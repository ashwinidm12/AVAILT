import { useEffect, useState } from "react";
import { Link, useSearchParams } from "react-router-dom";
import { apiGet } from "../api/http";

export function Services() {
  const [params] = useSearchParams();
  const category = params.get("category") || "";
  const q = params.get("q") || "";
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    let cancelled = false;
    setLoading(true);
    const qs = new URLSearchParams();
    if (category) qs.set("category", category);
    if (q) qs.set("search", q);
    const suffix = qs.toString() ? `?${qs.toString()}` : "";
    apiGet(`/api/services${suffix}`)
      .then((data) => {
        if (!cancelled) setItems(Array.isArray(data) ? data : []);
      })
      .catch(() => {
        if (!cancelled) setItems([]);
      })
      .finally(() => {
        if (!cancelled) setLoading(false);
      });
    return () => {
      cancelled = true;
    };
  }, [category, q]);

  const title = q ? `Search: “${q}”` : category || "All services";

  return (
    <section className="section container">
      <div className="breadcrumb">
        <Link to="/">Home</Link> · <span>Services</span>
      </div>
      <div className="section-head">
        <h2>{title}</h2>
        <p>Open a service for details, then book.</p>
      </div>
      {loading ? (
        <p className="loading">Loading services…</p>
      ) : items.length === 0 ? (
        <p className="error-banner">No services found.</p>
      ) : (
        <div className="grid-services">
          {items.map((s) => (
            <article key={s.id} className="card-service">
              <Link to={`/service/${s.id}`} style={{ textDecoration: "none", color: "inherit" }}>
                <div className="thumb-wrap">
                  <img src={s.imageUrl} alt="" loading="lazy" />
                </div>
                <div className="body">
                  <h3>{s.name}</h3>
                  <div className="meta">{s.category}</div>
                  <div className="rating">⭐ {Number(s.rating || 0).toFixed(1)}</div>
                  <div className="price">₹{Number(s.price || 0).toLocaleString("en-IN")}+</div>
                </div>
              </Link>
            </article>
          ))}
        </div>
      )}
    </section>
  );
}
