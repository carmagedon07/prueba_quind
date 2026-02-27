# PRUEBA TÉCNICA - DEV SENIOR JAVA

## Sistema de Gestión de Órdenes E-commerce con Event-Driven Architecture


**Entrega: Repositorio Git + Documentación completa**

---

## Objetivo

Diseñar e implementar un sistema de gestión de órdenes para e-commerce utilizando arquitectura orientada a eventos, programación reactiva y clean architecture. Este ejercicio evaluará capacidades técnicas, diseño arquitectónico y criterio de liderazgo técnico.

---

## Contexto de Negocio

Una empresa de e-commerce necesita modernizar su sistema de procesamiento de órdenes. Los problemas actuales incluyen:

- Picos de tráfico durante campañas promocionales que causan degradación del servicio
- Múltiples canales de venta requieren integración
- Procesos síncronos generan timeouts frecuentes
- Imposibilidad de escalar componentes de forma independiente

---

## Requerimientos Funcionales

### Gestión de Órdenes (Módulo Core)

- Crear orden de compra de forma reactiva
- Consultar estado de orden individual
- Listar órdenes por cliente con paginación
- Cancelar orden (permitido solo en estados PENDING o CONFIRMED)

### Procesamiento Asíncrono mediante Eventos

Implementar el siguiente flujo event-driven:

```
OrderCreated → ValidateInventory → ProcessPayment →
ConfirmOrder → NotifyCustomer → UpdateInventory

```

### Máquina de Estados para Órdenes

- PENDING: Orden recién creada, esperando validación
- CONFIRMED: Inventario validado y reservado
- PAYMENT_PROCESSING: Procesando pago con gateway
- PAID: Pago confirmado exitosamente
- SHIPPED: Orden despachada al cliente
- DELIVERED: Orden entregada
- CANCELLED: Orden cancelada por cliente o sistema
- FAILED: Error irrecuperable en procesamiento

---

## Requerimientos Técnicos Obligatorios

### Arquitectura

**Clean Architecture / Hexagonal Architecture**

- Capa de dominio completamente independiente de frameworks
- Puertos y adaptadores claramente definidos y documentados
- Inversión de dependencias respetada en todos los módulos

**Event-Driven Architecture**

- Kafka como message broker principal
- Definición clara de eventos de dominio
- Mínimo 3 eventos diferentes en el sistema
- Implementación de idempotencia en todos los consumidores

**Programación Reactiva**

- Spring WebFlux con Project Reactor
- APIs completamente no bloqueantes
- Manejo correcto de backpressure
- Uso apropiado de Mono y Flux

### Stack Técnico Obligatorio

- Java 17 o superior
- Spring Boot 3.x
- Spring WebFlux (Mono/Flux)
- Apache Kafka
- PostgreSQL o MySQL con R2DBC para datos transaccionales
- MongoDB reactivo para event store y logs
- Docker y Docker Compose

### Patrones de Diseño a Implementar

- **CQRS**: Separación explícita entre comandos y queries
- **Event Sourcing**: Al menos en el módulo de auditoría
- **Saga Pattern**: Para gestión de transacciones distribuidas
- **Repository Pattern**: Abstracción de persistencia
- **Factory/Builder**: Para construcción de entidades complejas

---

## Arquitectura del Sistema

### Microservicios Requeridos

**Order Service (puerto 8080)**

- API REST reactiva para gestión de órdenes
- Lógica de negocio del dominio
- Publicación de eventos en Kafka

**Payment Service (puerto 8081)**

- Simulación de procesamiento de pagos
- Consumidor de eventos OrderConfirmed
- Publicador de eventos PaymentProcessed/PaymentFailed

**Notification Service (puerto 8082)**

- Consumidor de eventos de órdenes
- Registro de notificaciones enviadas
- Endpoint de consulta de historial

### Componentes de Infraestructura

- Kafka (Zookeeper + Broker)
- PostgreSQL (persistencia transaccional)
- MongoDB (event store y auditoría)
- Redis (opcional, para caché reactivo)

---

## API REST Endpoints

### Order Service

```
POST   /api/v1/orders                    - Crear nueva orden
GET    /api/v1/orders/{id}               - Obtener orden por ID
GET    /api/v1/orders?customerId=X       - Listar órdenes del cliente
PATCH  /api/v1/orders/{id}/cancel        - Cancelar orden
GET    /api/v1/orders/{id}/events        - Historial de eventos (Event Sourcing)

```

### Payment Service

```
GET    /api/v1/payments/{orderId}        - Consultar estado de pago
POST   /api/v1/payments/{orderId}/retry  - Reintentar procesamiento

```

### Notification Service

```
GET    /api/v1/notifications?orderId=X   - Consultar notificaciones enviadas

```

---

## Entregables Obligatorios

### 1. Código Fuente

Repositorio Git público con:

- README exhaustivo con instrucciones de ejecución
- .gitignore apropiado para proyecto Java/Maven
- Commits atómicos con mensajes descriptivos siguiendo convención
- Estructura de proyecto clara y organizada

### 2. Documentación Técnica

**Architecture Decision Records (ADRs) - Mínimo 3**

Documentar decisiones arquitectónicas siguiendo este formato:

```markdown
# ADR-001: [Título de la Decisión]

## Estado
[Aceptado/Propuesto/Rechazado]

## Contexto
[Descripción del problema o situación que requiere una decisión]

## Decisión
[La solución elegida con justificación técnica]

## Consecuencias
Positivas:
- [Lista de ventajas]

Negativas:
- [Lista de desventajas o trade-offs]

## Alternativas Consideradas
1. [Alternativa 1]: Rechazada porque...
2. [Alternativa 2]: Rechazada porque...

```

Temas sugeridos para ADRs:

- Justificación de Clean Architecture vs alternativas
- Elección de Kafka como message broker
- Estrategia de manejo de errores y compensaciones en sagas

**Diagramas de Arquitectura**

- Diagrama de componentes (C4 Model nivel 2 o 3 preferiblemente)
- Diagrama de flujo de eventos entre servicios
- Modelo entidad-relación de base de datos
- Diagrama de estados de órdenes

Herramientas sugeridas: PlantUML, Mermaid, Draw.io, Excalidraw

**Diseño basado en Domain-Driven Design**

Documentar claramente:

- Bounded contexts identificados en el dominio
- Agregados y sus invariantes
- Entidades vs Value Objects
- Domain Events con sus payloads

**Documentación de APIs**

- Especificación OpenAPI/Swagger para cada servicio
- Collection de Postman o Insomnia con ejemplos de uso
- Ejemplos de requests y responses exitosos y con errores

### 3. Infraestructura como Código

- docker-compose.yml funcional que levante toda la infraestructura
- Scripts SQL de inicialización de bases de datos
- Variables de entorno documentadas en .env.example
- Comando único para ejecutar: docker-compose up

### 4. Suite de Testing

**Tests Unitarios**

- JUnit 5 con Mockito
- Cobertura mínima del 70% en capa de dominio
- Tests de validaciones de negocio

**Tests de Integración**

- Uso de Testcontainers para componentes externos
- Mínimo 5 tests end-to-end cubriendo flujos principales
- Tests de repositorios con bases de datos reales

**Tests Reactivos**

- StepVerifier para validar pipelines reactivos
- Tests de backpressure

**Reportes**

- Configuración de JaCoCo para reporte de cobertura
- Incluir reporte en el README

### 5. Observabilidad

- Logs estructurados en formato JSON
- Health checks implementados en todos los servicios
- Spring Boot Actuator configurado con métricas básicas
- Correlation ID propagado en headers HTTP y logs para trazabilidad

---

## Funcionalidades Adicionales Valoradas

### Implementaciones Técnicas Avanzadas

- Spring Cloud Gateway como API Gateway centralizado
- Kafka Streams para procesamiento de eventos complejos
- Pattern Outbox para garantizar consistencia eventual
- Circuit Breaker implementado con Resilience4j
- Cache reactivo con Redis
- Versionado de APIs (ejemplo: /v1, /v2)
- Migraciones de schema con Flyway o Liquibase
- Manifests de Kubernetes (Deployment, Service, ConfigMap)
- Pipeline de CI/CD con GitHub Actions o GitLab CI
- Autenticación y autorización con Spring Security (OAuth2/JWT)
- Rate limiting por cliente o endpoint

### Documentación Adicional

- Runbook para respuesta a incidentes
- Guía de troubleshooting de problemas comunes
- Definición de SLIs y SLOs para métricas de negocio
- Video demostración (5-10 minutos) explicando arquitectura

### Perspectiva de Liderazgo Técnico

- Propuesta de roadmap técnico a 3-6 meses
- Análisis de deuda técnica identificada y plan de remediación
- Estrategia de onboarding para nuevos desarrolladores
- Checklist de code review para el equipo
- Propuesta de estándares de código y convenciones

---

## Criterios de Evaluación

### Arquitectura y Diseño (30 puntos)

- Clean Architecture correctamente implementada con capas bien definidas
- Separación clara de responsabilidades entre módulos
- DDD: agregados, entidades y value objects apropiadamente modelados
- Event-driven architecture funcionando correctamente
- ADRs que demuestran análisis profundo de alternativas

### Calidad de Código (25 puntos)

- Aplicación de principios SOLID
- Clean Code: nombres descriptivos, funciones pequeñas, bajo acoplamiento
- Uso correcto y eficiente de programación reactiva
- Manejo robusto de errores y casos excepcionales
- Configuración externalizada correctamente
- Ausencia de código comentado, dead code o malas prácticas

### Funcionalidad Completa (20 puntos)

- Cumplimiento de todos los requerimientos funcionales
- Sistema funciona end-to-end sin errores
- Manejo correcto de casos edge y situaciones límite
- Idempotencia garantizada en operaciones críticas
- Validaciones de negocio apropiadas

### Testing (10 puntos)

- Cobertura de tests adecuada en capas críticas
- Tests bien estructurados, legibles y mantenibles
- Tests reactivos correctamente implementados
- Tests de integración con infraestructura real mediante Testcontainers
- Assertions claras y completas

### Documentación (10 puntos)

- ADRs completos y bien justificados
- README detallado que permite ejecutar el proyecto fácilmente
- Diagramas claros y comprensibles
- API documentation completa y precisa
- Decisiones técnicas documentadas

### Aspectos Operacionales (5 puntos)

- Docker compose funcional sin configuración manual adicional
- Scripts de inicialización que funcionan
- Logs estructurados y útiles para debugging
- Health checks correctamente implementados
- Variables de entorno manejadas apropiadamente

---

## Estructura del Repositorio

```
proyecto-root/
├── docs/
│   ├── ADRs/
│   │   ├── ADR-001-clean-architecture.md
│   │   ├── ADR-002-kafka-selection.md
│   │   └── ADR-003-error-handling.md
│   ├── architecture/
│   │   ├── component-diagram.png
│   │   ├── event-flow.png
│   │   └── database-schema.png
│   └── api/
│       ├── order-service-openapi.yaml
│       ├── payment-service-openapi.yaml
│       └── postman-collection.json
├── order-service/
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
├── payment-service/
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
├── notification-service/
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
├── docker-compose.yml
├── .env.example
├── README.md
├── EVALUATION.md
└── .gitignore

```

---

## Contenido del README.md

El README debe incluir obligatoriamente:

1. **Descripción general del proyecto**
    - Propósito y alcance
    - Arquitectura de alto nivel
2. **Decisiones técnicas principales**
    - Stack tecnológico elegido
    - Patrones implementados
    - Trade-offs realizados
3. **Prerequisitos**
    - Versiones de software requeridas
    - Herramientas necesarias
4. **Instrucciones de instalación**
    - Paso a paso desde clonar repositorio hasta sistema funcionando
    - Configuración de variables de entorno
5. **Ejecución de tests**
    - Comandos para ejecutar suite completa
    - Cómo ver reportes de cobertura
6. **Endpoints disponibles**
    - Listado de APIs con ejemplos de uso
    - URLs de Swagger UI
7. **Mejoras futuras**
    - Funcionalidades que agregarías con más tiempo
    - Optimizaciones pendientes

---

## Autoevaluación (EVALUATION.md)

Incluir un documento EVALUATION.md respondiendo:

1. **Funcionalidades completadas**
    - Lista detallada de lo implementado
    - Nivel de completitud de cada requerimiento
2. **Funcionalidades pendientes**
    - Qué no alcanzaste a implementar
    - Razones (tiempo, complejidad, priorización)
3. **Decisiones con más tiempo**
    - Qué harías diferente si tuvieras 2 semanas
    - Qué aspectos mejorarías
4. **Desafíos enfrentados**
    - Problemas técnicos más complejos
    - Cómo los resolviste
5. **Trade-offs realizados**
    - Decisiones donde sacrificaste algo
    - Justificación de cada trade-off

---

## Instrucciones de Envío

**Formato de entrega:**

- Repositorio Git público (GitHub, GitLab o Bitbucket)
- Asegurar que todo compile y ejecute con: docker-compose up
- Verificar que los tests pasen con: mvn test
- Video demostración opcional (Loom, YouTube unlisted, máximo 10 minutos)

**Antes de enviar, verificar:**

- README está completo y es comprensible
- docker-compose.yml levanta todos los servicios correctamente
- Tests pasan sin errores
- No hay credenciales hardcodeadas en el código
- Documentación está actualizada

---

## Gestión Sugerida del Tiempo

Esta es una recomendación, no una restricción:

**Días 1-2: Fundamentos**

- Diseño arquitectónico detallado
- Redacción de ADRs iniciales
- Setup de proyecto base
- Configuración de docker-compose

**Días 3-4: Implementación Core**

- Order Service completo
- Integración con Kafka
- Event sourcing básico

**Día 5: Servicios Complementarios**

- Payment Service
- Notification Service
- Integración entre servicios

**Día 6: Calidad y Pruebas**

- Suite de tests completa
- Refactoring de código
- Documentación de APIs

**Día 7: Cierre**

- Documentación final
- README exhaustivo
- Video demostración
- Autoevaluación

---

## Preguntas Frecuentes

**¿Se puede usar otro lenguaje además de Java?**

El core del sistema debe estar en Java. Python puede usarse para scripts auxiliares si se justifica en los ADRs.

**¿Es necesario implementar un frontend?**

No. El foco es backend. Swagger UI y colecciones de Postman son suficientes para demostración.

**¿Puedo agregar librerías adicionales?**

Sí, siempre que estén justificadas en documentación y no reemplacen la lógica central que se busca evaluar.

**¿Qué pasa si no completo todos los requerimientos?**

Se prioriza calidad sobre cantidad. Es preferible tener pocos módulos excelentes que muchos mediocres. Documenta lo faltante en EVALUATION.md con justificación.

**¿Se permite consultar documentación durante el desarrollo?**

Absolutamente. Se espera que uses documentación oficial, Stack Overflow y herramientas de IA como asistentes. Es parte del trabajo real.

**¿Es válido reutilizar código de proyectos anteriores?**

Puedes usar patrones y estructuras que conoces, pero la implementación debe ser específica para este problema. El código debe ser original para esta prueba.

---

## Aspectos Clave que se Evalúan

Como candidato a líder técnico, la evaluación se centra en:

**Pensamiento Arquitectónico**

- Capacidad de justificar decisiones técnicas con argumentos sólidos
- Consideración de trade-offs y alternativas
- Visión de escalabilidad y mantenibilidad a largo plazo

**Pragmatismo Técnico**

- Balance entre perfección técnica y entrega de valor
- Priorización efectiva de funcionalidades
- Gestión realista del tiempo

**Claridad en Comunicación**

- Documentación comprensible para diferentes audiencias
- Capacidad de explicar decisiones complejas de forma simple
- Diagramas y ejemplos efectivos

**Visión de Equipo**

- Código que otros desarrolladores puedan mantener
- Consideración de onboarding de nuevos miembros
- Estándares y convenciones consistentes

**Mentalidad de Mejora Continua**

- Identificación honesta de áreas de mejora
- Propuestas concretas de evolución
- Autocrítica constructiva

No se busca perfección absoluta. Se busca criterio técnico sólido, pragmatismo y capacidad real de liderazgo técnico en un contexto empresarial.

---

## Soporte Durante la Prueba

Si encuentras requerimientos ambiguos o situaciones poco claras:

1. Documenta tus asunciones en el README o en un ADR
2. Toma una decisión técnica justificada
3. Explica el razonamiento detrás de tu elección
4. Considera alternativas que evaluaste

Esta capacidad de tomar decisiones con información incompleta es parte de la evaluación.

---


**Enviar a: [santiago.garcia@quind.io,andres.zapata@quind.io]**