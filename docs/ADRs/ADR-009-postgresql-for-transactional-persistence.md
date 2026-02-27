# Decisión:
Se utilizará PostgreSQL para almacenar el estado actual de los agregados (orders, payments, inventory).

# Contexto:

- Se requieren transacciones ACID

- Se necesita integridad fuerte

- Se requiere soporte para Outbox Pattern

- Modelo relacional claro

# Alternativas consideradas:

- MongoDB

- MySQL

- Base compartida entre servicios (descartada)

# Consecuencias:

- Consistencia fuerte en estado actual

- Soporte natural para transacciones

- Fácil implementación del Outbox

- Mayor rigidez en esquema