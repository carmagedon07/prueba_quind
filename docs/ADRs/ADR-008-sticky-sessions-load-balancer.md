# Decisión:
Se habilitan Sticky Sessions en el Load Balancer para ciertas operaciones críticas.

# Contexto:
Reducir problemas de afinidad de sesión en flujos síncronos iniciales.

# Consecuencias:

- Mejor coherencia temporal

- Menor distribución completamente stateless