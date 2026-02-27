# Decisión:
El sistema se implementará como arquitectura de microservicios independientes.

# Contexto:
Se requiere desacoplamiento, escalabilidad independiente y separación clara de dominios.

# Consecuencias:

- Cada servicio tiene su propia base de datos

- No hay foreign keys entre servicios

- Comunicación asincrónica vía eventos