export async function apiGet(path, { auth = false } = {}) {
  const headers = { Accept: "application/json" };
  const t = localStorage.getItem("availt_auth_token");
  if (auth && t) headers.Authorization = `Bearer ${t}`;
  const res = await fetch(path, { headers });
  const data = await res.json().catch(() => ({}));
  if (!res.ok) {
    const err = new Error(data.message || `Request failed (${res.status})`);
    err.status = res.status;
    throw err;
  }
  return data;
}

export async function apiPost(path, body, { auth = false } = {}) {
  const headers = { "Content-Type": "application/json", Accept: "application/json" };
  const t = localStorage.getItem("availt_auth_token");
  if (auth && t) headers.Authorization = `Bearer ${t}`;
  const res = await fetch(path, {
    method: "POST",
    headers,
    body: JSON.stringify(body),
  });
  const data = await res.json().catch(() => ({}));
  if (!res.ok) {
    const err = new Error(data.message || `Request failed (${res.status})`);
    err.status = res.status;
    throw err;
  }
  return data;
}
