(function () {
  const STORAGE_SEARCH = "availt_header_search";

  function esc(s) {
    const d = document.createElement("div");
    d.textContent = s;
    return d.innerHTML;
  }

  function buildHeader(active) {
    const nav = [
      { id: "home", href: "index.html", label: "Home" },
      { id: "categories", href: "categories.html", label: "Categories" },
      { id: "login", href: "#", label: "Login" },
    ];

    const desktop = nav
      .map((item) => {
        const isActive = item.id === active;
        const cls = isActive ? ' class="is-active"' : "";
        const href = esc(item.href);
        const label = esc(item.label);
        if (item.id === "login") {
          return `<a href="${href}" data-login${cls}>${label}</a>`;
        }
        return `<a href="${href}"${cls}>${label}</a>`;
      })
      .join("");

    const mobile = nav
      .map((item) => {
        const href = esc(item.href);
        const label = esc(item.label);
        if (item.id === "login") {
          return `<a href="${href}" data-login>${label}</a>`;
        }
        return `<a href="${href}">${label}</a>`;
      })
      .join("");

    return `
<header class="site-header">
  <div class="header-inner">
    <a class="logo-link" href="index.html" aria-label="AVAILT Home">
      <span class="logo-text">AVAILT</span>
      <span class="logo-badge">Local</span>
    </a>
    <form class="header-search" id="header-search-form" action="services.html" method="get" role="search">
      <input type="search" name="q" id="header-search-input" placeholder="Search services, places…" autocomplete="off" aria-label="Search" />
      <button type="submit">Search</button>
    </form>
    <nav class="nav-desktop" aria-label="Primary">${desktop}</nav>
    <button type="button" class="nav-toggle" id="nav-toggle" aria-expanded="false" aria-controls="nav-mobile" aria-label="Open menu">
      <span></span><span></span><span></span>
    </button>
  </div>
  <nav class="nav-mobile" id="nav-mobile" aria-label="Mobile">${mobile}</nav>
</header>`;
  }

  function injectStyles() {
    const style = document.createElement("style");
    style.textContent = `
      .nav-desktop a.is-active { background: var(--bg); color: var(--primary); }
      .nav-desktop a[data-login] { color: var(--muted); }
    `;
    document.head.appendChild(style);
  }

  window.initLayout = function initLayout(options) {
    const active = (options && options.activeNav) || "home";
    const mount = document.getElementById("header-mount");
    if (!mount) return;

    injectStyles();
    mount.innerHTML = buildHeader(active);

    const toggle = document.getElementById("nav-toggle");
    const mobile = document.getElementById("nav-mobile");
    const input = document.getElementById("header-search-input");
    const saved = sessionStorage.getItem(STORAGE_SEARCH);
    if (input && saved) input.value = saved;

    if (toggle && mobile) {
      toggle.addEventListener("click", () => {
        const open = mobile.classList.toggle("is-open");
        toggle.setAttribute("aria-expanded", open ? "true" : "false");
      });
      mobile.querySelectorAll("a:not([data-login])").forEach((a) => {
        a.addEventListener("click", () => {
          mobile.classList.remove("is-open");
          toggle.setAttribute("aria-expanded", "false");
        });
      });
    }

    document.querySelectorAll("[data-login]").forEach((el) => {
      el.addEventListener("click", (e) => {
        e.preventDefault();
        alert("Login is coming soon. Browse categories to book a service.");
      });
    });

    const form = document.getElementById("header-search-form");
    if (form && input) {
      form.addEventListener("submit", () => {
        sessionStorage.setItem(STORAGE_SEARCH, input.value.trim());
      });
    }
  };
})();
