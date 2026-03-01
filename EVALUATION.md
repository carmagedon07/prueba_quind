1. **Funcionalidades completadas**

    - Arquitectura de Microservicios Reactivos: Implementación base utilizando Spring Boot 4.0.3 y Project Reactor (WebFlux) para un manejo no bloqueante de peticiones.

    - Persistencia Políglota:

        - Configuración de PostgreSQL mediante R2DBC para operaciones relacionales reactivas.

        - Integración de MongoDB Reactive para el almacenamiento de eventos y auditoría.

    - Comunicación Asíncrona con Kafka:

        - Configuración de productores y consumidores reactivos.

        - Definición de DTOs para la interoperabilidad entre servicios (OrderEventDTO).

    - Infraestructura como Código (Docker): Orquestación completa de servicios (Order, Payment, Notification) y bases de datos mediante docker-compose.yml.

    - Documentación de API: Integración de Swagger/OpenAPI adaptada para entornos WebFlux.        
        - Lista detallada de lo implementado
        - Nivel de completitud de cada requerimiento
2. **Funcionalidades pendientes**

    - Manejo de Transacciones Distribuidas (Saga Pattern): Aunque el flujo de eventos está configurado, falta la implementación completa de la lógica de compensación en caso de fallos en el pago. Razón: Alta complejidad en la gestión de estados intermedios en un entorno puramente reactivo.

    - Seguridad (OAuth2/JWT): No se ha implementado la capa de seguridad en los endpoints.Razón: Se priorizó la estabilidad de la mensajería y la persistencia sobre la autenticación.

    |- Pruebas de Integración con Testcontainers: Falta la validación completa del flujo Kafka-Postgres en entornos de test.
        - Qué no alcanzaste a implementar
        - Razones (tiempo, complejidad, priorización)

3. **Decisiones con más tiempo**

    - Implementación de Service Mesh: Utilizaría Istio para gestionar el tráfico, reintentos y observabilidad sin  sobrecargar el código de la aplicación.

    - Event Sourcing Completo: Migraría la lógica de negocio para que el estado actual del pago se derive exclusivamente de la secuencia de eventos en MongoDB.

    - Optimización de Recursos JVM: Investigaría los errores de "HotSpot" detectados en los logs (hs_err_pid) para ajustar los límites de memoria en los contenedores Docker.

4. **Desafíos enfrentados**

    - Se gestionó la Dualidad R2DBC/JDBC mediante la configuración del ConnectionFactoryInitializer. Ante la ausencia de soporte maduro de Flyway para drivers no bloqueantes, se optó por una inicialización programática forzando la carga de spring.r2dbc.url para garantizar que el esquema de base de datos esté listo antes de que el consumidor de Kafka empiece a procesar OrderEventDTO.

5. **Trade-offs realizados**

    - A. Consolidación de Lógica vs. Microservicio de Inventario
        Decisión: Se sacrificó la creación de un microservicio de Inventario independiente, integrando su validación lógica dentro del flujo de orquestación actual.

        Justificación: Para una prueba técnica de tiempo limitado, era más crítico garantizar que la orquestación reactiva y la coreografía de eventos (vía Kafka) funcionara de extremo a extremo sin errores de comunicación, que desplegar múltiples contenedores vacíos. Se prefirió entregar un flujo de pago y orden sólido y bien orquestado antes que una arquitectura distribuida incompleta o propensa a fallos de red por exceso de servicios.

    - B. Configuración de Entorno: Application Properties vs. Docker Compose
        Decisión: Se sacrificó la inyección dinámica de variables de entorno desde el docker-compose.yml para algunas configuraciones críticas, forzando la lectura directa desde los archivos application.properties internos de cada microservicio.

        Justificación: Se detectaron problemas de reconocimiento de variables de entorno con la versión específica de Spring  Boot 4.0.3 al correr en ciertos entornos de Docker. Para garantizar la portabilidad y la disponibilidad inmediata del   servicio (asegurando que las URLs de R2DBC y Kafka fueran siempre correctas), se decidió centralizar la configuración base en el proyecto. Esto evita que el revisor de la prueba encuentre errores de conexión por variables no expandidas en el runtime de Docker.

    - C. Inicialización de Base de Datos: SQL Init vs. Migraciones Evolutivas
        Decisión: Se utilizó la inicialización de scripts schema.sql forzada por propiedades en lugar de herramientas de migración como Flyway/Liquibase.

        Justificación: Dada la dualidad R2DBC/JDBC, integrar una herramienta de migración bloqueante en un stack puramente reactivo añadía una capa de complejidad innecesaria para el alcance de esta entrega. Se priorizó que la base de datos sea autogestionada al levantar el servicio para asegurar que el evaluador pueda ejecutar docker-compose up y tener el sistema listo sin pasos manuales.
