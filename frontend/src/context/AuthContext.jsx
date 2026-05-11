import { createContext, useCallback, useContext, useMemo, useState } from "react";

const AuthContext = createContext(null);
const TOKEN_KEY = "availt_auth_token";
const USER_KEY = "availt_auth_user";

function readUser() {
  try {
    const raw = localStorage.getItem(USER_KEY);
    return raw ? JSON.parse(raw) : null;
  } catch {
    return null;
  }
}

export function AuthProvider({ children }) {
  const [token, setToken] = useState(() => localStorage.getItem(TOKEN_KEY) || "");
  const [user, setUser] = useState(readUser);

  const saveAuth = useCallback((t, u) => {
    localStorage.setItem(TOKEN_KEY, t || "");
    localStorage.setItem(USER_KEY, JSON.stringify(u || null));
    setToken(t || "");
    setUser(u || null);
  }, []);

  const logout = useCallback(() => {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USER_KEY);
    setToken("");
    setUser(null);
  }, []);

  const value = useMemo(
    () => ({
      token,
      user,
      isLoggedIn: Boolean(token && user),
      saveAuth,
      logout,
    }),
    [token, user, saveAuth, logout]
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error("useAuth outside AuthProvider");
  return ctx;
}
