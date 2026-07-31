import { NavLink, useNavigate } from "react-router-dom";
import { useAuth } from "../hooks/useAuth";

function Sidebar() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <aside className="sidebar">
      <div>
        <h1 className="brand-title">Foody Tracker</h1>
        <p className="brand-subtitle">Mini Rastreador de Pedidos</p>
      </div>

      <nav className="side-nav">
        <NavLink to="/dashboard" className="side-link">Dashboard</NavLink>
        <NavLink to="/pedidos" className="side-link">Lista de Pedidos</NavLink>
        <NavLink to="/pedidos/novo" className="side-link">Novo Pedido</NavLink>
      </nav>

      <div className="sidebar-footer">
        <p className="user-name">{user?.nome}</p>
        <p className="user-email">{user?.email}</p>
        <button className="btn btn-ghost" onClick={handleLogout}>Sair</button>
      </div>
    </aside>
  );
}

export default Sidebar;
