(function () {
  function params() {
    const u = new URL(window.location.href);
    const rawQ = (u.searchParams.get("q") || "").trim();
    return {
      category: (u.searchParams.get("category") || "").trim(),
      qDisplay: rawQ,
    };
  }

  function starRating(r) {
    const n = Number(r) || 0;
    return `⭐ ${n.toFixed(1)}`;
  }

  function cardHtml(s) {
    const cat = s.category ? `&category=${encodeURIComponent(s.category)}` : "";
    const href = `booking.html?id=${encodeURIComponent(s.id)}${cat}`;
    return `
<article class="card-service">
  <div class="thumb-wrap">
    <img src="${s.imageUrl}" alt="" loading="lazy" width="900" height="560" />
  </div>
  <div class="body">
    <h3>${s.name}</h3>
    <div class="type">${s.type}</div>
    <div class="address">${s.address}</div>
    <div class="rating">${starRating(s.rating)}</div>
    <div class="btn-wrap">
      <a class="btn btn-primary btn-block" href="${href}">Book Now</a>
    </div>
  </div>
</article>`;
  }

  document.addEventListener("DOMContentLoaded", async () => {
    initLayout({ activeNav: "home" });
    const grid = document.getElementById("services-grid");
    const title = document.getElementById("services-title");
    const sub = document.getElementById("services-sub");
    const crumb = document.getElementById("services-crumb");
    const loading = document.getElementById("services-loading");
    if (!grid) return;

    const { category, qDisplay } = params();
    if (qDisplay) {
      const hi = document.getElementById("header-search-input");
      if (hi) hi.value = qDisplay;
      sessionStorage.setItem("availt_header_search", qDisplay);
    }

    if (title) {
      if (qDisplay) title.textContent = "Search results";
      else if (category) title.textContent = category;
      else title.textContent = "All services";
    }
    if (sub) {
      if (qDisplay) sub.textContent = `Matching “${qDisplay}”`;
      else if (category) sub.textContent = "Book trusted providers near you.";
      else sub.textContent = "Explore every category in one place.";
    }
    if (crumb) {
      crumb.innerHTML = `<a href="index.html">Home</a> · <a href="categories.html">Categories</a>`;
      if (category) crumb.innerHTML += ` · <span>${category}</span>`;
      else if (qDisplay) crumb.innerHTML += ` · <span>Search</span>`;
    }

    try {
      const catParam = category || undefined;
      const searchParam = qDisplay || undefined;
      const list = await AvailtApi.getServices(catParam, searchParam);

      if (!list.length) {
        grid.innerHTML =
          '<p class="error-state">No services found. Try another category or search.</p>';
      } else {
        grid.innerHTML = list.map(cardHtml).join("");
      }
    } catch (e) {
      grid.innerHTML =
        '<p class="error-state">Could not load services. Is the Java server running?</p>';
    } finally {
      if (loading) loading.style.display = "none";
    }
  });
})();
