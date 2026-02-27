# Decisión:
Se implementará Event Sourcing híbrido únicamente en Order Service.

# Contexto:
Order es el Aggregate principal y requiere trazabilidad completa de estado.

# Implementación:

- Estado actual en PostgreSQL

- ADR-005 — Uso de Outbox PatternEventos en MongoDB

# Consecuencias:

- Mayor complejidad en Order

- Auditoría completa

- Replay posible