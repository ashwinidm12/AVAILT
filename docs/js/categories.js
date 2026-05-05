(function () {
  function cardHtml(c) {
    const name = c.name;
    const img = c.image;
    const href = `services.html?category=${encodeURIComponent(name)}`;
    return `
<a class="card-category" href="${href}">
  <img class="thumb" src="${img}" alt="" loading="lazy" width="600" height="375" />
  <div class="body"><h3>${name}</h3></div>
</a>`;
  }

  document.addEventListener("DOMContentLoaded", async () => {
    initLayout({ activeNav: "categories" });
    const grid = document.getElementById("categories-grid");
    const loading = document.getElementById("categories-loading");
    if (!grid) return;

    try {
      const list = await AvailtApi.getCategories();
      grid.innerHTML = list.map(cardHtml).join("");
    } catch (e) {
      grid.innerHTML =
        '<p class="error-state">Could not load categories. Please refresh.</p>';
    } finally {
      if (loading) loading.style.display = "none";
    }
  });
})();
