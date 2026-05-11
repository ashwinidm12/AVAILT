import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { apiGet } from "../api/http";

export function Home() {
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [q, setQ] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    apiGet("/api/categories")
      .then(setCategories)
      .catch(() => setCategories([]))
      .finally(() => setLoading(false));
  }, []);

  return (
    <>
      <section className="hero">
        <div className="hero-inner">
          <h1>Book trusted local services</h1>
          <p>Browse categories, compare providers, and complete category-specific bookings — with catering menu selection when you choose Catering.</p>
          <form
            className="hero-search"
            onSubmit={(e) => {
              e.preventDefault();
              const t = q.trim();
              navigate(t ? `/services?q=${encodeURIComponent(t)}` : "/services");
            }}
          >
            <input value={q} onChange={(e) => setQ(e.target.value)} placeholder="What do you need?" />
            <button type="submit" className="btn btn-primary btn-lg">
              Search
            </button>
          </form>
        </div>
      </section>
      <section className="section container">
        <div className="section-head">
          <h2>Shop by category</h2>
          <p>Tap a category to see services and book.</p>
        </div>
        {loading ? (
          <p className="loading">Loading…</p>
        ) : (
          <div className="grid-categories">
            {categories.map((c) => (
              <Link key={c.name} className="card-category" to={`/services?category=${encodeURIComponent(c.name)}`}>
                <img className="thumb" src={c.image} alt="" loading="lazy" />
                <div className="body">
                  <h3>{c.name}</h3>
                </div>
              </Link>
            ))}
          </div>
        )}
        <p style={{ textAlign: "center", marginTop: 28 }}>
          <Link to="/categories" className="btn btn-ghost btn-lg" style={{ textDecoration: "none" }}>
            All categories
          </Link>
        </p>
      </section>
    </>
  );
}
