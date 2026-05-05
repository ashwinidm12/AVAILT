(function () {
  var STORAGE_KEY = "availt_booking_payload";

  function escapeHtml(s) {
    var d = document.createElement("div");
    d.textContent = s == null ? "" : String(s);
    return d.innerHTML;
  }

  function getQuery() {
    var u = new URL(window.location.href);
    return {
      id: u.searchParams.get("id"),
      category: (u.searchParams.get("category") || "").trim(),
    };
  }

  function fieldHtml(f) {
    var id = "bf-" + f.name.replace(/[^a-zA-Z0-9_-]/g, "_");
    var req = f.required !== false;
    var ph = f.placeholder ? ' placeholder="' + escapeHtml(f.placeholder) + '"' : "";
    var attrs = "";
    if (f.attrs) {
      for (var k in f.attrs) {
        if (Object.prototype.hasOwnProperty.call(f.attrs, k)) {
          attrs += " " + k + '="' + escapeHtml(f.attrs[k]) + '"';
        }
      }
    }
    if (f.type === "select") {
      var opts = (f.options || []).map(function (o) {
        return (
          '<option value="' +
          escapeHtml(o) +
          '">' +
          escapeHtml(o) +
          "</option>"
        );
      });
      return (
        '<div class="form-group">' +
        '<label for="' +
        id +
        '">' +
        escapeHtml(f.label) +
        (req ? " *" : "") +
        "</label>" +
        '<select id="' +
        id +
        '" name="' +
        escapeHtml(f.name) +
        '"' +
        (req ? " required" : "") +
        '><option value="">Choose…</option>' +
        opts.join("") +
        "</select></div>"
      );
    }
    return (
      '<div class="form-group">' +
      '<label for="' +
      id +
      '">' +
      escapeHtml(f.label) +
      (req ? " *" : "") +
      "</label>" +
      '<input id="' +
      id +
      '" name="' +
      escapeHtml(f.name) +
      '" type="' +
      escapeHtml(f.type) +
      '"' +
      (req ? " required" : "") +
      ph +
      attrs +
      " /></div>"
    );
  }

  function renderForm(cfg) {
    var html = "";
    var lastSection = null;
    for (var i = 0; i < cfg.fields.length; i++) {
      var f = cfg.fields[i];
      var sec = f.section || "Details";
      if (sec !== lastSection) {
        html +=
          '<h3 class="form-section-title"><span class="form-section-icon" aria-hidden="true">▸</span>' +
          escapeHtml(sec) +
          "</h3>";
        lastSection = sec;
      }
      html += fieldHtml(f);
    }
    return html;
  }

  function renderMenuCards(menus) {
    if (!menus.length) {
      return '<p class="error-state">No menu packages available.</p>';
    }
    return menus
      .map(function (m, idx) {
        return (
          '<div class="card-food booking-menu-card" role="button" tabindex="0" data-idx="' +
          idx +
          '" data-name="' +
          encodeURIComponent(m.menuName) +
          '" data-items="' +
          encodeURIComponent(m.items || "") +
          '" data-price="' +
          Number(m.price) +
          '">' +
          "<h3>" +
          escapeHtml(m.menuName) +
          "</h3>" +
          '<p class="items">' +
          escapeHtml(m.items || "") +
          "</p>" +
          '<div class="price">₹' +
          Number(m.price).toLocaleString("en-IN") +
          "</div></div>"
        );
      })
      .join("");
  }

  document.addEventListener("DOMContentLoaded", async function () {
    initLayout({ activeNav: "home" });
    var q = getQuery();
    var panel = document.getElementById("booking-detail");
    var formMount = document.getElementById("booking-form-mount");
    var loading = document.getElementById("booking-loading");

    if (!q.id) {
      if (loading) loading.style.display = "none";
      if (panel)
        panel.innerHTML =
          '<p class="error-state">Missing service. <a href="categories.html">Pick a category</a>.</p>';
      return;
    }

    try {
      var s = await AvailtApi.getService(q.id);
      var category = q.category || s.category || "";
      var cfg = AvailtCategoryConfig.get(category);
      var isCatering = category === "Catering" && cfg.extra === "menu";

      if (loading) loading.style.display = "none";

      if (panel) {
        panel.innerHTML =
          '<div class="service-preview">' +
          '<div class="category-booking-badge">' +
          escapeHtml(cfg.icon) +
          " <span>" +
          escapeHtml(cfg.title) +
          "</span></div>" +
          '<img src="' +
          escapeHtml(s.imageUrl) +
          '" alt="" loading="lazy" />' +
          "<h2>" +
          escapeHtml(s.name) +
          "</h2>" +
          '<p class="type" style="color:var(--muted);margin:0 0 8px;">' +
          escapeHtml(s.type) +
          "</p>" +
          "<p style=\"margin:0;\">" +
          escapeHtml(s.address) +
          "</p>" +
          '<p style="margin:8px 0 0;font-weight:600;">⭐ ' +
          Number(s.rating).toFixed(1) +
          "</p></div>";
      }

      if (!formMount) return;

      var menuHtml = "";
      if (isCatering) {
        menuHtml =
          '<div class="menu-section panel menu-section-embed" id="menu-section">' +
          '<h3 class="form-section-title"><span class="form-section-icon" aria-hidden="true">🍽️</span>Choose a package</h3>' +
          '<p class="menu-section-hint">Select one menu package for your event.</p>' +
          '<div class="grid-food booking-menu-grid" id="booking-menu-grid"></div>' +
          '<p class="menu-validation-msg" id="menu-validation-msg" hidden>Please select a menu package.</p>' +
          "</div>";
      }

      formMount.innerHTML =
        '<div class="panel booking-dynamic-panel">' +
        '<h2 class="booking-form-main-title">' +
        escapeHtml(cfg.icon) +
        " " +
        escapeHtml(cfg.title) +
        "</h2>" +
        '<form id="booking-form" novalidate>' +
        renderForm(cfg) +
        "</form>" +
        menuHtml +
        '<button type="button" class="btn btn-primary btn-lg btn-block booking-submit-btn" id="booking-continue">Continue to summary</button>' +
        "</div>";

      var menuGrid = document.getElementById("booking-menu-grid");
      var selectedMenu = null;

      if (isCatering && menuGrid) {
        try {
          var menus = await AvailtApi.getMenus(q.id);
          menuGrid.innerHTML = renderMenuCards(menus);
          menuGrid.querySelectorAll(".booking-menu-card").forEach(function (card) {
            card.addEventListener("click", function () {
              menuGrid.querySelectorAll(".booking-menu-card").forEach(function (el) {
                el.classList.toggle("is-selected", el === card);
              });
              selectedMenu = card;
              var mv = document.getElementById("menu-validation-msg");
              if (mv) mv.hidden = true;
            });
            card.addEventListener("keydown", function (e) {
              if (e.key === "Enter" || e.key === " ") {
                e.preventDefault();
                card.click();
              }
            });
          });
        } catch (e) {
          menuGrid.innerHTML =
            '<p class="error-state">Could not load menus.</p>';
        }
      }

      document.getElementById("booking-continue").addEventListener("click", function () {
        var form = document.getElementById("booking-form");
        if (!form.checkValidity()) {
          form.reportValidity();
          return;
        }

        if (isCatering && !selectedMenu) {
          var mv = document.getElementById("menu-validation-msg");
          if (mv) mv.hidden = false;
          return;
        }

        var formData = {};
        cfg.fields.forEach(function (f) {
          var el = form.elements[f.name];
          if (el) formData[f.name] = String(el.value || "").trim();
        });

        var selectedPackage = null;
        if (selectedMenu) {
          selectedPackage = {
            name: decodeURIComponent(selectedMenu.getAttribute("data-name")),
            items: decodeURIComponent(selectedMenu.getAttribute("data-items")),
            price: Number(selectedMenu.getAttribute("data-price")),
          };
        }

        var payload = {
          version: 2,
          category: category || "General",
          serviceId: String(q.id),
          serviceName: s.name,
          servicePrice: Number(s.price) || 0,
          formData: formData,
          selectedPackage: selectedPackage,
        };

        try {
          localStorage.setItem(STORAGE_KEY, JSON.stringify(payload));
        } catch (e) {
          alert("Could not save booking data. Check storage permissions.");
          return;
        }

        window.location.href = "summary.html";
      });
    } catch (e) {
      if (loading) loading.style.display = "none";
      if (panel)
        panel.innerHTML =
          '<p class="error-state">Service not found. <a href="categories.html">Back to categories</a>.</p>';
    }
  });
})();
