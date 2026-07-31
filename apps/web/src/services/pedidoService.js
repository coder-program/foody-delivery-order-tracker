import api from "./api";

export const pedidoService = {
  async listar() {
    const { data } = await api.get("/pedidos");
    return data;
  },

  async buscarPorId(id) {
    const { data } = await api.get(`/pedidos/${id}`);
    return data;
  },

  async criar(payload) {
    const { data } = await api.post("/pedidos", payload);
    return data;
  },

  async atualizarStatus(id, status) {
    const { data } = await api.put(`/pedidos/${id}/status`, { status });
    return data;
  }
};
