import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { apiPost } from "../api/http";
import { useAuth } from "../context/AuthContext";

export function Signup() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");
  const [password, setPassword] = useState("");
  const [confirm, setConfirm] = useState("");
  const [err, setErr] = useState("");
  const { saveAuth } = useAuth();
  const navigate = useNavigate();

  const submit = async (e) => {
    e.preventDefault();
    setErr("");
    if (password.length < 6) {
      setErr("Password must be at least 6 characters.");
      return;
    }
    if (password !== confirm) {
      setErr("Passwords do not match.");
      return;
    }
    try {
      const res = await apiPost("/api/auth/signup", {
        name: name.trim(),
        email: email.trim(),
        phone: phone.trim(),
        password,
        confirmPassword: confirm,
      });
      saveAuth(res.token, res.user);
      navigate("/", { replace: true });
    } catch (ex) {
      setErr(ex.message || "Signup failed");
    }
  };

  return (
    <section className="section container narrow">
      <div className="breadcrumb">
        <Link to="/">Home</Link> · <span>Sign up</span>
      </div>
      <div className="panel">
        <h1 style={{ marginTop: 0 }}>Create your account</h1>
        <form onSubmit={submit} className="stack-form">
          <div className="form-group">
            <label htmlFor="name">Full name *</label>
            <input id="name" required value={name} onChange={(e) => setName(e.target.value)} />
          </div>
          <div className="form-group">
            <label htmlFor="email">Email *</label>
            <input id="email" type="email" required value={email} onChange={(e) => setEmail(e.target.value)} />
          </div>
          <div className="form-group">
            <label htmlFor="phone">Phone *</label>
            <input id="phone" type="tel" required value={phone} onChange={(e) => setPhone(e.target.value)} />
          </div>
          <div className="form-group">
            <label htmlFor="password">Password *</label>
            <input id="password" type="password" required value={password} onChange={(e) => setPassword(e.target.value)} />
          </div>
          <div className="form-group">
            <label htmlFor="confirm">Confirm password *</label>
            <input id="confirm" type="password" required value={confirm} onChange={(e) => setConfirm(e.target.value)} />
          </div>
          {err ? <p className="error-banner">{err}</p> : null}
          <button type="submit" className="btn btn-primary btn-lg btn-block">
            Sign up
          </button>
        </form>
        <p style={{ marginTop: 16, textAlign: "center" }}>
          Already have an account? <Link to="/login">Login</Link>
        </p>
      </div>
    </section>
  );
}
