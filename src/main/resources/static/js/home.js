(function () {
  function goSearch() {
    const el = document.getElementById("hero-search-input");
    const q = (el && el.value.trim()) || "";
    sessionStorage.setItem("availt_header_search", q);
    window.location.href = q
      ? `services.html?q=${encodeURIComponent(q)}`
      : "services.html";
  }

  document.addEventListener("DOMContentLoaded", async () => {
    initLayout({ activeNav: "home" });

    const heroIn = document.getElementById("hero-search-input");
    const saved = sessionStorage.getItem("availt_header_search");
    if (heroIn && saved) heroIn.value = saved;

    const form = document.getElementById("hero-search-form");
    if (form) {
      form.addEventListener("submit", (e) => {
        e.preventDefault();
        goSearch();
      });
    }

    const btn = document.getElementById("hero-search-btn");
    if (btn) btn.addEventListener("click", goSearch);

    const grid = document.getElementById("home-categories");
    if (grid) {
      grid.innerHTML =
        '<div class="skeleton" style="grid-column:1/-1;min-height:200px;"></div>';
      try {
        const list = await AvailtApi.getCategories();
        grid.innerHTML = list
          .slice(0, 8)
          .map(
            (c) => `
<a class="card-category" href="services.html?category=${encodeURIComponent(c.name)}">
  <img class="thumb" src="${c.image}" alt="" loading="lazy" width="600" height="375" />
  <div class="body"><h3>${c.name}</h3></div>
</a>`
          )
          .join("");
      } catch (e) {
        grid.innerHTML =
          '<p class="error-state">Could not load categories. <a href="categories.html">Open categories page</a>.</p>';
      }
    }
  });
})();
