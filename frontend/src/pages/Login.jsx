import { useState } from "react";
import { Link, useNavigate, useLocation } from "react-router-dom";
import { apiPost } from "../api/http";
import { useAuth } from "../context/AuthContext";

export function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [err, setErr] = useState("");
  const { saveAuth } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const from = location.state?.from || "/";

  const submit = async (e) => {
    e.preventDefault();
    setErr("");
    try {
      const res = await apiPost("/api/auth/login", { email: email.trim(), password });
      saveAuth(res.token, res.user);
      navigate(from, { replace: true });
    } catch (ex) {
      setErr(ex.message || "Login failed");
    }
  };

  return (
    <section className="section container narrow">
      <div className="breadcrumb">
        <Link to="/">Home</Link> · <span>Login</span>
      </div>
      <div className="panel">
        <h1 style={{ marginTop: 0 }}>Welcome back</h1>
        <p style={{ color: "var(--muted)" }}>Sign in with your email and password.</p>
        <form onSubmit={submit} className="stack-form">
          <div className="form-group">
            <label htmlFor="email">Email *</label>
            <input id="email" type="email" autoComplete="email" required value={email} onChange={(e) => setEmail(e.target.value)} />
          </div>
          <div className="form-group">
            <label htmlFor="password">Password *</label>
            <input id="password" type="password" autoComplete="current-password" required value={password} onChange={(e) => setPassword(e.target.value)} />
          </div>
          {err ? <p className="error-banner">{err}</p> : null}
          <button type="submit" className="btn btn-primary btn-lg btn-block">
            Login
          </button>
        </form>
        <p style={{ marginTop: 16, textAlign: "center" }}>
          New here? <Link to="/signup">Create an account</Link>
        </p>
      </div>
    </section>
  );
}
