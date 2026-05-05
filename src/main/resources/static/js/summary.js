(function () {
  var STORAGE_KEY = "availt_booking_payload";
  var LAST_BOOKING_KEY = "availt_last_booking";

  function escapeHtml(s) {
    var d = document.createElement("div");
    d.textContent = s == null ? "" : String(s);
    return d.innerHTML;
  }

  function humanizeKey(key) {
    return String(key || "")
      .replace(/_/g, " ")
      .replace(/\b\w/g, function (c) {
        return c.toUpperCase();
      });
  }

  function labelForField(cfg, name) {
    for (var i = 0; i < cfg.fields.length; i++) {
      if (cfg.fields[i].name === name) return cfg.fields[i].label;
    }
    return humanizeKey(name);
  }

  function derivePeopleCount(fd) {
    var n =
      fd.people ||
      fd.guests ||
      fd.attendees ||
      fd.sessions ||
      fd.duration ||
      fd.child_age;
    var x = Number(n);
    return isNaN(x) || x < 1 ? 1 : Math.floor(x);
  }

  function deriveDate(fd) {
    return fd.date || fd.start_date || "";
  }

  function deriveEventName(fd, category) {
    return (
      fd.event ||
      fd.event_type ||
      fd.service_type ||
      fd.job_type ||
      category ||
      "Booking"
    );
  }

  function buildDetailRows(category, formData, cfg) {
    var rows = [];
    var keys = Object.keys(formData || {});
    keys.forEach(function (k) {
      var v = formData[k];
      if (v === "" || v == null) return;
      rows.push({
        label: labelForField(cfg, k),
        value: v,
      });
    });
    return rows;
  }

  function loadPayload() {
    var raw = localStorage.getItem(STORAGE_KEY);
    if (raw) {
      try {
        return JSON.parse(raw);
      } catch (e) {}
    }
    var oldB = sessionStorage.getItem("availt_booking");
    var oldF = sessionStorage.getItem("availt_food");
    if (oldB && oldF) {
      try {
        var b = JSON.parse(oldB);
        var f = JSON.parse(oldF);
        return {
          version: 2,
          category: "Catering",
          serviceId: String(b.serviceId),
          serviceName: b.serviceName,
          servicePrice: 0,
          formData: {
            name: b.customerName,
            phone: b.phone,
            people: String(b.people),
            event: b.eventName,
            date: b.date,
          },
          selectedPackage: {
            name: f.name,
            items: f.items,
            price: f.price,
          },
        };
      } catch (e) {}
    }
    return null;
  }

  document.addEventListener("DOMContentLoaded", function () {
    initLayout({ activeNav: "home" });
    var root = document.getElementById("summary-root");
    var loading = document.getElementById("summary-loading");

    var payload = loadPayload();
    if (!payload || !payload.formData) {
      if (loading) loading.style.display = "none";
      if (root)
        root.innerHTML =
          '<p class="error-state">No booking data. <a href="index.html">Start from home</a>.</p>';
      return;
    }

    var category = payload.category || "General";
    var cfg = AvailtCategoryConfig.get(category);
    var formData = payload.formData;
    var rows = buildDetailRows(category, formData, cfg);

    var total = payload.selectedPackage
      ? Number(payload.selectedPackage.price) || 0
      : Number(payload.servicePrice) || 0;

    var pkgBlock = "";
    if (payload.selectedPackage) {
      var p = payload.selectedPackage;
      pkgBlock =
        '<h3 class="summary-subtitle">Selected package</h3>' +
        '<ul class="summary-list summary-package">' +
        "<li><span>" +
        escapeHtml(p.name) +
        '</span><span class="summary-muted">' +
        escapeHtml(p.items) +
        "</span></li>" +
        "</ul>";
    } else if (category === "Catering") {
      pkgBlock =
        '<p class="error-state">No package selected. <a href="booking.html?id=' +
        encodeURIComponent(payload.serviceId) +
        "&category=" +
        encodeURIComponent(category) +
        '">Go back</a></p>';
    }

    if (loading) loading.style.display = "none";

    if (root) {
      var listHtml = rows
        .map(function (r) {
          return (
            "<li><span>" +
            escapeHtml(r.label) +
            "</span><strong>" +
            escapeHtml(r.value) +
            "</strong></li>"
          );
        })
        .join("");

      root.innerHTML =
        '<div class="panel summary-panel">' +
        '<div class="summary-category-chip">' +
        escapeHtml(cfg.icon) +
        " " +
        escapeHtml(category) +
        "</div>" +
        '<h2 style="margin-top:12px;">Review your booking</h2>' +
        '<p class="summary-service-name">' +
        escapeHtml(payload.serviceName || "") +
        "</p>" +
        '<h3 class="summary-subtitle">Your details</h3>' +
        '<ul class="summary-list">' +
        listHtml +
        "</ul>" +
        pkgBlock +
        '<div class="summary-total">' +
        "<span>Total</span>" +
        "<span>₹" +
        total.toLocaleString("en-IN") +
        "</span></div>" +
        '<button type="button" class="btn btn-primary btn-lg btn-block" id="confirm-booking" style="margin-top:24px;">Confirm booking</button>' +
        "</div>";

      if (category === "Catering" && !payload.selectedPackage) {
        return;
      }

      document.getElementById("confirm-booking").addEventListener("click", async function () {
        var btn = document.getElementById("confirm-booking");
        btn.disabled = true;
        btn.textContent = "Confirming…";

        var detailsObj = {
          category: category,
          formData: formData,
          selectedPackage: payload.selectedPackage,
        };
        var bookingDetails = JSON.stringify(detailsObj);

        var apiPayload = {
          serviceId: Number(payload.serviceId),
          name: formData.name,
          phone: formData.phone,
          peopleCount: derivePeopleCount(formData),
          eventName: deriveEventName(formData, category),
          date: deriveDate(formData),
          totalPrice: total,
          venue:
            (payload.selectedPackage
              ? payload.selectedPackage.name + " — " + payload.selectedPackage.items
              : deriveEventName(formData, category)) || "",
          status: "CONFIRMED",
          bookingDetails: bookingDetails,
        };

        try {
          var res = await AvailtApi.postBooking(apiPayload);
          sessionStorage.setItem(
            LAST_BOOKING_KEY,
            JSON.stringify({
              bookingId: res.id != null ? String(res.id) : "",
              customerName: formData.name,
              serviceName: payload.serviceName,
              category: category,
              formData: formData,
              selectedPackage: payload.selectedPackage,
              totalPrice: total,
            })
          );
          localStorage.removeItem(STORAGE_KEY);
          sessionStorage.removeItem("availt_booking");
          sessionStorage.removeItem("availt_food");
          window.location.href = "confirmation.html";
        } catch (e) {
          btn.disabled = false;
          btn.textContent = "Confirm booking";
          alert("Could not confirm. Please try again.");
        }
      });
    }
  });
})();
