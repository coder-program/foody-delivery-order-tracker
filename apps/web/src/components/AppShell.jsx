import Sidebar from "./Sidebar";

function AppShell({ title, children }) {
  return (
    <div className="app-shell">
      <Sidebar />
      <main className="main-content">
        <header className="page-header">
          <h2>{title}</h2>
        </header>
        {children}
      </main>
    </div>
  );
}

export default AppShell;
