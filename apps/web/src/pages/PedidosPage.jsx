import { useEffect, useState } from "react";
import AppShell from "../components/AppShell";
import StatusBadge from "../components/StatusBadge";
import { pedidoService } from "../services/pedidoService";

const statusOptions = ["RECEBIDO", "EM_PREPARO", "SAIU_PARA_ENTREGA", "ENTREGUE", "CANCELADO"];

function PedidosPage() {
  const [pedidos, setPedidos] = useState([]);
  const [error, setError] = useState("");

  const loadPedidos = async () => {
    try {
      const data = await pedidoService.listar();
      setPedidos(data);
      setError("");
    } catch (err) {
      setError(err.response?.data?.message || "Falha ao carregar pedidos");
    }
  };

  useEffect(() => {
    loadPedidos();
  }, []);

  const handleStatusChange = async (pedidoId, status) => {
    try {
      await pedidoService.atualizarStatus(pedidoId, status);
      await loadPedidos();
    } catch (err) {
      setError(err.response?.data?.message || "Falha ao atualizar status");
    }
  };

  const handleVisualizar = async (pedidoId) => {
    try {
      const pedido = await pedidoService.buscarPorId(pedidoId);
      const itens = pedido.listaItens.map((item) => `${item.nome} (${item.quantidade})`).join(", ");
      window.alert(`Pedido #${pedido.id}\nCliente: ${pedido.cliente}\nEndereco: ${pedido.enderecoEntrega}\nStatus: ${pedido.status}\nItens: ${itens}`);
    } catch (err) {
      setError(err.response?.data?.message || "Falha ao buscar pedido");
    }
  };

  return (
    <AppShell title="Lista de Pedidos">
      <section className="card">
        {error && <p className="error-text">{error}</p>}
        <div className="table-wrap">
          <table className="clean-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Cliente</th>
                <th>Status</th>
                <th>Endereco</th>
                <th>Data</th>
                <th>Acoes</th>
              </tr>
            </thead>
            <tbody>
              {pedidos.map((pedido) => (
                <tr key={pedido.id}>
                  <td>{pedido.id}</td>
                  <td>{pedido.cliente}</td>
                  <td><StatusBadge status={pedido.status} /></td>
                  <td>{pedido.enderecoEntrega}</td>
                  <td>{new Date(pedido.dataCriacao).toLocaleString("pt-BR")}</td>
                  <td className="actions-cell">
                    <select
                      defaultValue={pedido.status}
                      onChange={(event) => handleStatusChange(pedido.id, event.target.value)}
                    >
                      {statusOptions.map((status) => (
                        <option key={status} value={status}>{status}</option>
                      ))}
                    </select>
                    <button className="btn btn-secondary" onClick={() => handleVisualizar(pedido.id)}>
                      Visualizar
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </section>
    </AppShell>
  );
}

export default PedidosPage;
