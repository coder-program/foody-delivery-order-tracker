import { useEffect, useState } from "react";
import AppShell from "../components/AppShell";
import { pedidoService } from "../services/pedidoService";

function DashboardPage() {
  const [metrics, setMetrics] = useState({ total: 0, entregues: 0, emPreparo: 0 });

  useEffect(() => {
    async function load() {
      try {
        const pedidos = await pedidoService.listar();
        const entregues = pedidos.filter((p) => p.status === "ENTREGUE").length;
        const emPreparo = pedidos.filter((p) => p.status === "EM_PREPARO").length;
        setMetrics({ total: pedidos.length, entregues, emPreparo });
      } catch {
        setMetrics({ total: 0, entregues: 0, emPreparo: 0 });
      }
    }
    load();
  }, []);

  return (
    <AppShell title="Dashboard">
      <section className="cards-grid">
        <article className="metric-card">
          <h3>Total de pedidos</h3>
          <strong>{metrics.total}</strong>
        </article>
        <article className="metric-card">
          <h3>Pedidos entregues</h3>
          <strong>{metrics.entregues}</strong>
        </article>
        <article className="metric-card">
          <h3>Pedidos em preparo</h3>
          <strong>{metrics.emPreparo}</strong>
        </article>
      </section>
    </AppShell>
  );
}

export default DashboardPage;
