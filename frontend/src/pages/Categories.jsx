import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { apiGet } from "../api/http";

export function Categories() {
  const [list, setList] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    apiGet("/api/categories")
      .then(setList)
      .catch(() => setList([]))
      .finally(() => setLoading(false));
  }, []);

  return (
    <section className="section container">
      <div className="breadcrumb">
        <Link to="/">Home</Link> · <span>Categories</span>
      </div>
      <div className="section-head">
        <h2>All categories</h2>
      </div>
      {loading ? (
        <p className="loading">Loading…</p>
      ) : (
        <div className="grid-categories">
          {list.map((c) => (
            <Link key={c.name} className="card-category" to={`/services?category=${encodeURIComponent(c.name)}`}>
              <img className="thumb" src={c.image} alt="" loading="lazy" />
              <div className="body">
                <h3>{c.name}</h3>
              </div>
            </Link>
          ))}
        </div>
      )}
    </section>
  );
}
