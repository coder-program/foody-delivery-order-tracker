function StatusBadge({ status }) {
  const normalized = status || "RECEBIDO";
  return <span className={`status-badge status-${normalized.toLowerCase()}`}>{normalized}</span>;
}

export default StatusBadge;
