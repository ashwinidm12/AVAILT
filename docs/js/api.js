(function () {
  window.AvailtApi = {
    async getJson(path) {
      const res = await fetch(path, { headers: { Accept: "application/json" } });
      if (!res.ok) {
        const err = new Error(`Request failed: ${res.status}`);
        err.status = res.status;
        throw err;
      }
      return res.json();
    },

    getCategories() {
      return this.getJson("api/categories");
    },

    /** Spring: GET /api/services?category=&search= */
    getServices(category, search) {
      const p = new URLSearchParams();
      if (category && String(category).trim()) {
        p.set("category", String(category).trim());
      }
      if (search && String(search).trim()) {
        p.set("search", String(search).trim());
      }
      const qs = p.toString();
      return this.getJson(`api/services${qs ? `?${qs}` : ""}`);
    },

    getService(id) {
      return this.getJson(`api/services/${encodeURIComponent(id)}`);
    },

    /** Spring: GET /api/menus?serviceId= */
    getMenus(serviceId) {
      return this.getJson(
        `api/menus?serviceId=${encodeURIComponent(serviceId)}`
      );
    },

    /** Spring Booking JSON: name, peopleCount, serviceId, date (yyyy-MM-dd), … */
    postBooking(payload) {
      return fetch("api/bookings", {
        method: "POST",
        headers: { "Content-Type": "application/json", Accept: "application/json" },
        body: JSON.stringify(payload),
      }).then(async (res) => {
        const data = await res.json().catch(() => ({}));
        if (!res.ok) {
          const err = new Error(data.message || data.error || "Booking failed");
          err.status = res.status;
          throw err;
        }
        return data;
      });
    },
  };
})();
