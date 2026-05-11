import { Routes, Route } from "react-router-dom";
import { Header, Footer } from "./components/Header";
import { ProtectedRoute } from "./components/ProtectedRoute";
import { Home } from "./pages/Home";
import { Login } from "./pages/Login";
import { Signup } from "./pages/Signup";
import { Categories } from "./pages/Categories";
import { Services } from "./pages/Services";
import { ServiceDetail } from "./pages/ServiceDetail";
import { CateringMenu } from "./pages/CateringMenu";
import { Booking } from "./pages/Booking";
import { Summary } from "./pages/Summary";
import { Confirmation } from "./pages/Confirmation";

function Shell({ children }) {
  return (
    <div className="page-wrap">
      <Header />
      <main style={{ flex: 1 }}>{children}</main>
      <Footer />
    </div>
  );
}

export default function App() {
  return (
    <Routes>
      <Route path="/" element={<Shell><Home /></Shell>} />
      <Route path="/login" element={<Shell><Login /></Shell>} />
      <Route path="/signup" element={<Shell><Signup /></Shell>} />
      <Route path="/categories" element={<Shell><Categories /></Shell>} />
      <Route path="/services" element={<Shell><Services /></Shell>} />
      <Route path="/service/:id" element={<Shell><ServiceDetail /></Shell>} />
      <Route path="/menu/:serviceId" element={<Shell><CateringMenu /></Shell>} />
      <Route
        path="/book/:serviceId"
        element={
          <Shell>
            <ProtectedRoute>
              <Booking />
            </ProtectedRoute>
          </Shell>
        }
      />
      <Route
        path="/summary"
        element={
          <Shell>
            <ProtectedRoute>
              <Summary />
            </ProtectedRoute>
          </Shell>
        }
      />
      <Route path="/confirmation" element={<Shell><Confirmation /></Shell>} />
    </Routes>
  );
}
