import { Navigate, Route, Routes } from "react-router-dom";
import ProtectedRoute from "../components/ProtectedRoute";
import DashboardPage from "../pages/DashboardPage";
import LoginPage from "../pages/LoginPage";
import NovoPedidoPage from "../pages/NovoPedidoPage";
import PedidosPage from "../pages/PedidosPage";
import RegisterPage from "../pages/RegisterPage";

function AppRoutes() {
  return (
    <Routes>
      <Route path="/login" element={<LoginPage />} />
      <Route path="/register" element={<RegisterPage />} />

      <Route
        path="/dashboard"
        element={
          <ProtectedRoute>
            <DashboardPage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/pedidos"
        element={
          <ProtectedRoute>
            <PedidosPage />
          </ProtectedRoute>
        }
      />
      <Route
        path="/pedidos/novo"
        element={
          <ProtectedRoute>
            <NovoPedidoPage />
          </ProtectedRoute>
        }
      />

      <Route path="/" element={<Navigate to="/dashboard" replace />} />
      <Route path="*" element={<Navigate to="/dashboard" replace />} />
    </Routes>
  );
}

export default AppRoutes;
