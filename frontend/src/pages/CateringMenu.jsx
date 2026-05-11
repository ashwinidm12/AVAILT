import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { apiGet } from "../api/http";

export function CateringMenu() {
  const { serviceId } = useParams();
  const navigate = useNavigate();
  const [service, setService] = useState(null);
  const [menus, setMenus] = useState([]);
  const [selected, setSelected] = useState(null);
  const [err, setErr] = useState("");

  useEffect(() => {
    let c = false;
    apiGet(`/api/services/${serviceId}`)
      .then((s) => {
        if (!c) setService(s);
        if (s && s.category !== "Catering") setErr("Menus are only for Catering services.");
      })
      .catch(() => {
        if (!c) setErr("Service not found.");
      });
    apiGet(`/api/menus?serviceId=${encodeURIComponent(serviceId)}`)
      .then((m) => {
        if (!c) setMenus(Array.isArray(m) ? m : []);
      })
      .catch(() => {});
    return () => {
      c = true;
    };
  }, [serviceId]);

  if (err) {
    return (
      <section className="section container">
        <p className="error-banner">{err}</p>
        <Link to="/services">Back</Link>
      </section>
    );
  }

  if (!service) return <p className="loading container section">Loading…</p>;

  return (
    <section className="section container">
      <div className="breadcrumb">
        <Link to="/">Home</Link> · <Link to="/services">Services</Link> · <Link to={`/service/${service.id}`}>{service.name}</Link> · <span>Menu</span>
      </div>
      <div className="section-head">
        <h2>Choose a catering package</h2>
        <p>Select one menu — it appears on your booking summary and total (per person × guests).</p>
      </div>
      <div className="grid-menus">
        {menus.map((m) => (
          <div
            key={m.id}
            role="button"
            tabIndex={0}
            className={`menu-card ${selected?.id === m.id ? "selected" : ""}`}
            onClick={() => setSelected(m)}
            onKeyDown={(e) => {
              if (e.key === "Enter" || e.key === " ") setSelected(m);
            }}
          >
            <img src={m.imageUrl} alt="" />
            <h3>{m.menuName}</h3>
            <p className="items">{m.items}</p>
            <div className="price">₹{Number(m.price).toLocaleString("en-IN")} / person</div>
            <button type="button" className="btn btn-ghost btn-block" onClick={() => setSelected(m)}>
              Select
            </button>
          </div>
        ))}
      </div>
      <div style={{ marginTop: 24, maxWidth: 420 }}>
        <button
          type="button"
          className="btn btn-primary btn-lg btn-block"
          disabled={!selected}
          onClick={() => {
            if (!selected) return;
            navigate(`/book/${serviceId}`, {
              state: {
                selectedMenu: {
                  id: selected.id,
                  menuName: selected.menuName,
                  items: selected.items,
                  price: selected.price,
                  imageUrl: selected.imageUrl,
                },
              },
            });
          }}
        >
          Continue to booking
        </button>
      </div>
    </section>
  );
}
