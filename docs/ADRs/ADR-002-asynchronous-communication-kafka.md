# Decisión:
Se utilizará Apache Kafka como broker de eventos.

# Contexto:
Se requiere desacoplamiento fuerte entre servicios y soporte para Saga distribuida.

# Consecuencias:

- Eventual consistency

- Necesidad de idempotencia

- Necesidad de Outbox Pattern