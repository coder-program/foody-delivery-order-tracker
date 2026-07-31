import { useState } from "react";
import { useNavigate } from "react-router-dom";
import AppShell from "../components/AppShell";
import { pedidoService } from "../services/pedidoService";

function NovoPedidoPage() {
  const navigate = useNavigate();
  const [cliente, setCliente] = useState("");
  const [enderecoEntrega, setEnderecoEntrega] = useState("");
  const [itemNome, setItemNome] = useState("");
  const [itemQuantidade, setItemQuantidade] = useState(1);
  const [listaItens, setListaItens] = useState([]);
  const [error, setError] = useState("");

  const handleAdicionarItem = () => {
    if (!itemNome.trim() || Number(itemQuantidade) <= 0) {
      setError("Informe nome do item e quantidade maior que zero");
      return;
    }

    setListaItens((prev) => [...prev, { nome: itemNome.trim(), quantidade: Number(itemQuantidade) }]);
    setItemNome("");
    setItemQuantidade(1);
    setError("");
  };

  const handleSalvar = async (event) => {
    event.preventDefault();

    if (!cliente.trim() || !enderecoEntrega.trim() || listaItens.length === 0) {
      setError("Preencha cliente, endereço e ao menos um item");
      return;
    }

    try {
      await pedidoService.criar({
        cliente: cliente.trim(),
        enderecoEntrega: enderecoEntrega.trim(),
        listaItens
      });
      navigate("/pedidos");
    } catch (err) {
      setError(err.response?.data?.message || "Falha ao criar pedido");
    }
  };

  return (
    <AppShell title="Novo Pedido">
      <section className="card">
        <form className="form-grid" onSubmit={handleSalvar}>
          <label>
            Cliente
            <input value={cliente} onChange={(event) => setCliente(event.target.value)} required />
          </label>

          <label>
            Endereco de entrega
            <input value={enderecoEntrega} onChange={(event) => setEnderecoEntrega(event.target.value)} required />
          </label>

          <div className="inline-form">
            <label>
              Nome do item
              <input value={itemNome} onChange={(event) => setItemNome(event.target.value)} />
            </label>
            <label>
              Quantidade
              <input
                type="number"
                min="1"
                value={itemQuantidade}
                onChange={(event) => setItemQuantidade(event.target.value)}
              />
            </label>
            <button className="btn btn-secondary" type="button" onClick={handleAdicionarItem}>
              Adicionar
            </button>
          </div>

          {listaItens.length > 0 && (
            <ul className="item-list">
              {listaItens.map((item, index) => (
                <li key={`${item.nome}-${index}`}>{item.nome} - qtd {item.quantidade}</li>
              ))}
            </ul>
          )}

          {error && <p className="error-text">{error}</p>}

          <button className="btn btn-primary" type="submit">Salvar Pedido</button>
        </form>
      </section>
    </AppShell>
  );
}

export default NovoPedidoPage;
