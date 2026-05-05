/**
 * Menu selection for Catering is integrated into booking.html.
 * Legacy route: send users forward or back to categories.
 */
(function () {
  document.addEventListener("DOMContentLoaded", function () {
    if (localStorage.getItem("availt_booking_payload")) {
      window.location.replace("summary.html");
      return;
    }
    window.location.replace("categories.html");
  });
})();
