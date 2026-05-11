import { Fragment, useEffect, useMemo, useState } from "react";
import { Link, useLocation, useNavigate, useParams } from "react-router-dom";
import { estimateTotal, getCategoryConfig } from "../categoryConfig";
import { apiGet } from "../api/http";

function Field({ f, value, onChange }) {
  const id = `f-${f.name}`;
  if (f.type === "select") {
    return (
      <div className="form-group">
        <label htmlFor={id}>
          {f.label}
          {f.required ? " *" : ""}
        </label>
        <select id={id} required={f.required} value={value || ""} onChange={(e) => onChange(f.name, e.target.value)}>
          <option value="">Choose…</option>
          {(f.options || []).map((o) => (
            <option key={o} value={o}>
              {o}
            </option>
          ))}
        </select>
      </div>
    );
  }
  if (f.type === "textarea") {
    return (
      <div className="form-group">
        <label htmlFor={id}>
          {f.label}
          {f.required ? " *" : ""}
        </label>
        <textarea id={id} required={f.required} rows={f.rows || 3} value={value || ""} onChange={(e) => onChange(f.name, e.target.value)} />
      </div>
    );
  }
  return (
    <div className="form-group">
      <label htmlFor={id}>
        {f.label}
        {f.required ? " *" : ""}
      </label>
      <input
        id={id}
        type={f.type || "text"}
        required={f.required}
        value={value || ""}
        placeholder={f.placeholder}
        min={f.attrs?.min}
        step={f.attrs?.step}
        onChange={(e) => onChange(f.name, e.target.value)}
      />
    </div>
  );
}

export function Booking() {
  const { serviceId } = useParams();
  const location = useLocation();
  const navigate = useNavigate();
  const [service, setService] = useState(null);
  const [form, setForm] = useState({});
  const [error, setError] = useState("");

  useEffect(() => {
    setError("");
    apiGet(`/api/services/${serviceId}`)
      .then(setService)
      .catch(() => setError("Service not found"));
  }, [serviceId]);

  const category = service?.category || "";
  const cfg = useMemo(() => getCategoryConfig(category), [category]);
  const isCatering = category === "Catering";
  const selectedMenu = location.state?.selectedMenu;

  useEffect(() => {
    if (!service) return;
    if (isCatering && !selectedMenu) {
      navigate(`/menu/${serviceId}`, { replace: true });
    }
  }, [service, isCatering, selectedMenu, navigate, serviceId]);

  const onChange = (name, v) => setForm((p) => ({ ...p, [name]: v }));

  const submit = (e) => {
    e.preventDefault();
    if (!service) return;
    if (isCatering && !selectedMenu) return;
    const total = estimateTotal(category, form, selectedMenu, service);
    navigate("/summary", {
      state: {
        service,
        category,
        formData: { ...form },
        selectedMenu: isCatering ? selectedMenu : null,
        totalPrice: total,
      },
    });
  };

  if (error) {
    return (
      <section className="section container">
        <p className="error-banner">{error}</p>
        <Link to="/services">Back</Link>
      </section>
    );
  }

  if (!service) return <p className="loading container section">Loading…</p>;
  if (isCatering && !selectedMenu) return null;

  const totalPreview = estimateTotal(category, form, selectedMenu, service);

  return (
    <section className="section container">
      <div className="breadcrumb">
        <Link to="/">Home</Link> · <Link to={`/service/${service.id}`}>{service.name}</Link> · <span>Book</span>
      </div>
      <div className="booking-grid">
        <div className="panel">
          <img src={service.imageUrl} alt="" className="detail-hero-img" />
          <h2>{service.name}</h2>
          <p className="meta">{service.category}</p>
          {isCatering && selectedMenu && (
            <div className="menu-recap">
              <strong>Selected:</strong> {selectedMenu.menuName} — ₹{Number(selectedMenu.price).toLocaleString("en-IN")}/person
            </div>
          )}
          <div className="estimate-pill">
            Est. total: <strong>₹{Number(totalPreview).toLocaleString("en-IN")}</strong>
          </div>
        </div>
        <form className="panel" onSubmit={submit}>
          <h2 style={{ marginTop: 0 }}>
            {cfg.icon} {cfg.title}
          </h2>
          {cfg.fields.map((f, i) => (
            <Fragment key={f.name}>
              {f.section && (i === 0 || cfg.fields[i - 1].section !== f.section) ? <h3 className="form-section-title">{f.section}</h3> : null}
              <Field f={f} value={form[f.name]} onChange={onChange} />
            </Fragment>
          ))}
          <button type="submit" className="btn btn-primary btn-lg btn-block" style={{ marginTop: 16 }}>
            Review summary
          </button>
        </form>
      </div>
    </section>
  );
}
