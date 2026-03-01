package com.notification_service.infrastructure.rest;

import com.notification_service.application.usecase.GetNotificationsUseCase;
import com.notification_service.domain.model.Notification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@Tag(name = "Notifications", description = "Endpoints para la gestión de notificaciones de pedidos")
public class NotificationController {

    private final GetNotificationsUseCase getNotificationsUseCase;

    @GetMapping
    @Operation(
            summary = "Obtiene notificaciones por ID de orden",
            description = """
                    Retorna la lista de notificaciones asociadas al orderId especificado.
                    
                                                                                             La respuesta es un arreglo JSON que contiene, para cada notificación, los siguientes campos:
                    
                                                                                             id: Identificador único de la notificación.
                    
                                                                                             orderId: Identificador de la orden asociada.
                    
                                                                                             type: Tipo de notificación.
                    
                                                                                             message: Contenido del mensaje de la notificación.
                    
                                                                                             status: Estado actual de la notificación.
                    
                                                                                             createdAt: Fecha y hora de creación de la notificación.
                    
                                                                                             Si no existen notificaciones para el orderId proporcionado, el servicio retornará un arreglo vacío ([]).
            """
    )
    public Flux<Notification> getByOrderId(@RequestParam Long orderId) {
        return getNotificationsUseCase.execute(orderId);
    }
}
