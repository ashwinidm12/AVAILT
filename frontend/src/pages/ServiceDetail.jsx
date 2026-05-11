import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { apiGet } from "../api/http";

export function ServiceDetail() {
  const { id } = useParams();
  const [s, setS] = useState(null);
  const [err, setErr] = useState("");

  useEffect(() => {
    apiGet(`/api/services/${id}`)
      .then(setS)
      .catch(() => setErr("Not found"));
  }, [id]);

  if (err || (!s && !err)) {
    if (!s && !err) return <p className="loading container section">Loading…</p>;
    return (
      <section className="section container">
        <p className="error-banner">{err}</p>
        <Link to="/services">Back</Link>
      </section>
    );
  }

  const isCatering = s.category === "Catering";
  const bookHref = isCatering ? `/menu/${s.id}` : `/book/${s.id}`;

  return (
    <section className="section container">
      <div className="breadcrumb">
        <Link to="/">Home</Link> · <Link to="/services">Services</Link> · <span>{s.name}</span>
      </div>
      <div className="detail-grid">
        <div className="panel">
          <img src={s.imageUrl} alt="" className="detail-hero-img" />
          <h1 style={{ marginTop: 16 }}>{s.name}</h1>
          <p className="meta">{s.type}</p>
          <p>{s.description || "Trusted local provider on AVAILT."}</p>
          <p>
            <strong>Address:</strong> {s.address}
          </p>
          <p>
            <strong>Contact:</strong> {s.contact}
          </p>
          <p className="rating">
            ⭐ {Number(s.rating || 0).toFixed(1)} · <span className="price">₹{Number(s.price || 0).toLocaleString("en-IN")}</span>
          </p>
        </div>
        <div className="panel sticky-side">
          <h2 style={{ marginTop: 0 }}>Book this service</h2>
          <p style={{ color: "var(--muted)" }}>
            {isCatering ? "Choose your catering menu package first, then enter event details." : "Fill the category-specific booking form."}
          </p>
          <Link className="btn btn-primary btn-lg btn-block" to={bookHref} style={{ textDecoration: "none", marginTop: 16 }}>
            {isCatering ? "Choose menu" : "Book now"}
          </Link>
          <Link className="btn btn-ghost btn-block" to="/services" style={{ textDecoration: "none", marginTop: 10 }}>
            Back to list
          </Link>
        </div>
      </div>
    </section>
  );
}
