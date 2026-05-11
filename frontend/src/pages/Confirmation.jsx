import { Link, useLocation } from "react-router-dom";
import { useMemo } from "react";

export function Confirmation() {
  const location = useLocation();
  const data = useMemo(() => {
    try {
      const raw = sessionStorage.getItem("availt_last_booking");
      return raw ? JSON.parse(raw) : null;
    } catch {
      return null;
    }
  }, [location.key]);

  const bookingId = location.state?.bookingId || data?.id;

  if (!data && !bookingId) {
    return (
      <section className="section container narrow" style={{ textAlign: "center" }}>
        <p>No confirmation to show.</p>
        <Link to="/">Home</Link>
      </section>
    );
  }

  return (
    <section className="section container narrow" style={{ textAlign: "center" }}>
      <div className="success-icon">✓</div>
      <h1 style={{ marginBottom: 8 }}>Booking confirmed</h1>
      <p style={{ color: "var(--muted)" }}>
        Thank you! Your request for <strong>{data?.serviceName}</strong> is received.
      </p>
      {data?.category && (
        <p style={{ color: "var(--muted)" }}>
          Category: <strong>{data.category}</strong>
        </p>
      )}
      <p>
        Reference: <strong>{bookingId || "—"}</strong>
      </p>
      {data?.totalPrice != null && (
        <p>
          Total: <strong>₹{Number(data.totalPrice).toLocaleString("en-IN")}</strong>
        </p>
      )}
      <Link to="/" className="btn btn-primary btn-lg" style={{ textDecoration: "none", marginTop: 20, display: "inline-flex" }}>
        Continue browsing
      </Link>
    </section>
  );
}
