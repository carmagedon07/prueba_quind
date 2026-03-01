package com.PaymentService.PaymentServiceAPI.infrastructure.rest;

import com.PaymentService.PaymentServiceAPI.application.usecase.GetPaymentStatusUseCase;
import com.PaymentService.PaymentServiceAPI.application.usecase.RetryPaymentUseCase;
import com.PaymentService.PaymentServiceAPI.domain.model.PaymentAudit;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Tag(
        name = "Payments",
        description = "API para gestión de pagos. Permite consultar el estado de un pago y reintentar pagos fallidos."
)
public class PaymentController {

    private final GetPaymentStatusUseCase getStatusUseCase;
    private final RetryPaymentUseCase retryUseCase;

    @GetMapping("/{orderId}")
    @Operation(
            summary = "Obtiene el estado de un pago por ID de orden",
            description = """
            Consulta el estado actual y la información de auditoría de un pago específico.
            
            - El pago se identifica mediante el orderId proporcionado en la URL.
            - Retorna un objeto PaymentAudit con detalles completos del pago.
            - Si no existe un pago para el orderId especificado, retorna 404 Not Found.
            
            Los datos de auditoría incluyen información como:
            * Estado del pago
            * Fecha y hora de creación
            * Fecha y hora de última actualización
            * Detalles de la transacción
            """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Pago encontrado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PaymentAudit.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró un pago para el orderId proporcionado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    public Mono<ResponseEntity<PaymentAudit>> getStatus(
            @Parameter(
                    description = "ID de la orden asociada al pago a consultar",
                    required = true,
                    example = "12345"
            )
            @PathVariable Long orderId
    ) {
        return getStatusUseCase.execute(orderId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/{orderId}/retry")
    @Operation(
            summary = "Reintenta un pago fallido",
            description = """
            Ejecuta un reintento para un pago que previamente falló.
            
            - Requiere el ID de la orden asociada al pago a reintentar.
            - El proceso es asíncrono: retorna 202 Accepted inmediatamente.
            - El resultado del reintento puede ser consultado posteriormente mediante el endpoint GET.
            - Solo aplica para pagos en estado 'FAILED' o 'ERROR'.
            
            El sistema maneja automáticamente:
            * Validación de que el pago existe y es reintentable
            * Registro del reintento en auditoría
            * Límites de reintentos (si están configurados)
            """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "202",
                    description = "Reintento aceptado y en proceso",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Solicitud inválida (ej: el pago no es reintentable)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró un pago para el orderId proporcionado",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflicto - Ya hay un reintento en proceso",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content
            )
    })
    public Mono<ResponseEntity<Void>> retry(
            @Parameter(
                    description = "ID de la orden asociada al pago a reintentar",
                    required = true,
                    example = "12345"
            )
            @PathVariable Long orderId
    ) {
        return retryUseCase.execute(orderId)
                .then(Mono.just(ResponseEntity.accepted().build()));
    }
}