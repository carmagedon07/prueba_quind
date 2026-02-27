# Decisión:
La creación y cancelación de órdenes se gestionará mediante Saga distribuida basada en eventos.

# Contexto:
Las transacciones abarcan múltiples servicios (Order, Payment, Inventory).

# Consecuencias:

- No se usan transacciones distribuidas

- Se modelan eventos compensatorios

- El sistema es eventualmente consistente