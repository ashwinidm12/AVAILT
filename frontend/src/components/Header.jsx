import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export function Header() {
  const { isLoggedIn, user, logout } = useAuth();
  const navigate = useNavigate();
  const [q, setQ] = useState("");
  const [open, setOpen] = useState(false);

  const goSearch = (e) => {
    e.preventDefault();
    const term = q.trim();
    navigate(term ? `/services?q=${encodeURIComponent(term)}` : "/services");
    setOpen(false);
  };

  return (
    <header className="site-header">
      <div className="header-inner">
        <Link to="/" className="logo" onClick={() => setOpen(false)}>
          AVAI<span>LT</span>
        </Link>
        <form className="header-search" onSubmit={goSearch}>
          <input value={q} onChange={(e) => setQ(e.target.value)} placeholder="Search services…" aria-label="Search" />
          <button type="submit" className="btn btn-primary">
            Search
          </button>
        </form>
        <button type="button" className="nav-toggle" aria-label="Menu" onClick={() => setOpen((o) => !o)}>
          <span />
          <span />
          <span />
        </button>
        <nav className={`nav-links ${open ? "is-open" : ""}`}>
          <Link to="/categories" onClick={() => setOpen(false)}>
            Categories
          </Link>
          <Link to="/services" onClick={() => setOpen(false)}>
            Services
          </Link>
          {isLoggedIn ? (
            <>
              <span className="nav-user">{user?.name?.split(" ")[0]}</span>
              <button type="button" className="btn btn-ghost" onClick={() => { logout(); setOpen(false); navigate("/"); }}>
                Logout
              </button>
            </>
          ) : (
            <>
              <Link to="/login" onClick={() => setOpen(false)}>
                Login
              </Link>
              <Link to="/signup" className="btn btn-primary" style={{ textDecoration: "none" }} onClick={() => setOpen(false)}>
                Sign up
              </Link>
            </>
          )}
        </nav>
      </div>
    </header>
  );
}

export function Footer() {
  return <footer className="footer-mini">© AVAILT — Local service booking</footer>;
}
