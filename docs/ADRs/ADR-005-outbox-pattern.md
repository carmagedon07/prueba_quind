# Decisión:
Todos los servicios utilizarán Outbox Pattern para publicación confiable de eventos.

# Contexto:
Evitar inconsistencias entre persistencia local y publicación en Kafka.

# Consecuencias:

- Worker adicional

- Gestión de reintentos

- Mayor confiabilidad