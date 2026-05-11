import { useLocation, useNavigate } from "react-router-dom";
import { useState } from "react";
import { estimateTotal, getCategoryConfig } from "../categoryConfig";
import { apiPost } from "../api/http";
import { useAuth } from "../context/AuthContext";

export function Summary() {
  const location = useLocation();
  const navigate = useNavigate();
  const { user } = useAuth();
  const [err, setErr] = useState("");
  const [busy, setBusy] = useState(false);

  const state = location.state;
  if (!state?.service || !state.formData) {
    return (
      <section className="section container narrow">
        <p className="error-banner">No booking data. Start from a service.</p>
      </section>
    );
  }

  const { service, category, formData, selectedMenu } = state;
  const cfg = getCategoryConfig(category);
  const total = estimateTotal(category, formData, selectedMenu, service);

  const labelFor = (name) => {
    const f = cfg.fields.find((x) => x.name === name);
    return f ? f.label : name;
  };

  const confirm = async () => {
    setErr("");
    setBusy(true);
    const body = {
      serviceId: service.id,
      category,
      bookingData: formData,
      selectedMenu: category === "Catering" ? selectedMenu : undefined,
      totalPrice: total,
    };
    try {
      const res = await apiPost("/api/bookings", body, { auth: true });
      sessionStorage.setItem(
        "availt_last_booking",
        JSON.stringify({
          id: res.id,
          serviceName: service.name,
          category,
          totalPrice: total,
          userId: user?.id,
        })
      );
      navigate("/confirmation", { replace: true, state: { bookingId: res.id } });
    } catch (e) {
      setErr(e.message || "Booking failed");
    } finally {
      setBusy(false);
    }
  };

  return (
    <section className="section container narrow">
      <div className="breadcrumb">
        <span>Summary</span>
      </div>
      <div className="panel">
        <p className="meta">
          {cfg.icon} <strong>{category}</strong>
        </p>
        <h2 style={{ marginTop: 0 }}>Review your booking</h2>
        <p style={{ color: "var(--muted)" }}>{service.name}</p>
        <h3 className="summary-sub">Your details</h3>
        <ul className="summary-list">
          {Object.entries(formData).map(([k, v]) =>
            v ? (
              <li key={k}>
                <span>{labelFor(k)}</span>
                <strong>{v}</strong>
              </li>
            ) : null
          )}
        </ul>
        {selectedMenu && category === "Catering" && (
          <>
            <h3 className="summary-sub">Selected menu</h3>
            <p>
              <strong>{selectedMenu.menuName}</strong> — {selectedMenu.items}
            </p>
            <p className="meta">
              ₹{Number(selectedMenu.price).toLocaleString("en-IN")} × {formData.people || 1} guests
            </p>
          </>
        )}
        <div className="summary-total">
          <span>Total</span>
          <span>₹{Number(total).toLocaleString("en-IN")}</span>
        </div>
        {err ? <p className="error-banner">{err}</p> : null}
        <button type="button" className="btn btn-primary btn-lg btn-block" style={{ marginTop: 20 }} disabled={busy} onClick={confirm}>
          {busy ? "Confirming…" : "Confirm booking"}
        </button>
      </div>
    </section>
  );
}
