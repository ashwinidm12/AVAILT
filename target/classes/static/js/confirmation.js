(function () {
  var LAST_BOOKING_KEY = "availt_last_booking";

  function esc(s) {
    var d = document.createElement("div");
    d.textContent = s == null ? "" : String(s);
    return d.innerHTML;
  }

  document.addEventListener("DOMContentLoaded", function () {
    initLayout({ activeNav: "home" });
    var root = document.getElementById("confirmation-root");
    var raw = sessionStorage.getItem(LAST_BOOKING_KEY);

    if (!raw) {
      if (root)
        root.innerHTML =
          '<div class="confirm-wrap panel">' +
          '<p class="error-state" style="color:var(--muted);">No confirmation to show.</p>' +
          '<a class="btn btn-primary" href="index.html">Back to home</a></div>';
      return;
    }

    var data;
    try {
      data = JSON.parse(raw);
    } catch (e) {
      return;
    }

    var who =
      data.customerName ||
      (data.formData && data.formData.name) ||
      "guest";
    var svc = data.serviceName || "your service";
    var ref = data.bookingId || "—";
    var catLine = data.category
      ? '<p style="color:var(--muted);font-size:0.95rem;">Category: <strong>' +
        esc(data.category) +
        "</strong></p>"
      : "";

    if (root) {
      root.innerHTML =
        '<div class="confirm-wrap">' +
        '<div class="confirm-icon" aria-hidden="true">✓</div>' +
        "<h1>Booking confirmed</h1>" +
        "<p>Thank you, <strong>" +
        esc(who) +
        "</strong>. We’ve received your request for <strong>" +
        esc(svc) +
        "</strong>.</p>" +
        catLine +
        '<p style="font-size:0.95rem;">Reference: <strong>' +
        esc(ref) +
        "</strong></p>" +
        '<a class="btn btn-primary btn-lg" href="index.html">Continue browsing</a></div>';
    }
  });
})();
