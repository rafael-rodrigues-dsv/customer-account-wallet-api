package dev.challenge.api.adapter.notification.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.challenge.api.adapter.notification.NotificationService;
import dev.challenge.api.adapter.notification.dto.notification.NotificationDto;
import dev.challenge.api.adapter.notification.dto.notification.SendNotificationDto;
import dev.challenge.api.configuration.NotificationConfig;
import dev.challenge.api.domain.enumeration.TransactionReasonEnum;
import lombok.RequiredArgsConstructor;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CamelNotificationServiceImpl implements NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(CamelNotificationServiceImpl.class);
    private boolean routeCreated = false;
    private final CamelContext camelContext;
    private final ProducerTemplate producerTemplate;
    private final NotificationConfig notificationConfig;
    private final ObjectMapper objectMapper;


    @Override
    public void sendNotification(String email, BigDecimal amount, Long transferId, TransactionReasonEnum transactionReason) {
        try {
            createRouteIfNotCreated();

            String notificationServiceUrl = notificationConfig.notificationServiceUrl
                    + "?transactionReason=" + transactionReason.name()
                    + "&email=" + email
                    + "&amount=" + amount
                    + "&transferId=" + transferId;

            Map<String, Object> headers = new HashMap<>();
            headers.put("notificationServiceUrl", notificationServiceUrl);
            headers.put("email", email);
            headers.put("amount", amount);
            headers.put("transferId", transferId);
            headers.put("transactionReason", transactionReason.name());

            producerTemplate.sendBodyAndHeaders("direct:sendNotification", null, headers);
        } catch (Exception e) {
            logger.error("Error sending notification: {}", e.getMessage(), e);
        }
    }

    private void createRouteIfNotCreated() {
        if (!routeCreated) {
            try {
                camelContext.addRoutes(new RouteBuilder() {
                    @Override
                    public void configure() {
                        from("direct:sendNotification")
                                .setHeader("Content-Type", constant("application/json"))
                                .process(exchange -> {
                                    String jsonBody = objectMapper.writeValueAsString(createNotificationRequestDto(exchange));
                                    InputStream inputStream = new ByteArrayInputStream(jsonBody.getBytes(StandardCharsets.UTF_8));
                                    exchange.getIn().setBody(inputStream);
                                })
                                .toD("${header.notificationServiceUrl}")
                                .log("Response: ${body}")
                                .process(exchange -> {
                                    String responseBody = exchange.getIn().getBody(String.class);
                                    NotificationDto responseDto = objectMapper.readValue(responseBody, NotificationDto.class);
                                    if (responseDto.isMessageSent()) {
                                        logger.info("Message sent successfully!");
                                    } else {
                                        logger.warn("Failed to send the message");
                                    }
                                });
                    }
                });

                routeCreated = true;
            } catch (Exception e) {
                logger.error("Error executing the notification service: {}", e.getMessage(), e);
            }
        }
    }

    protected SendNotificationDto createNotificationRequestDto(org.apache.camel.Exchange exchange) {
        String email = exchange.getIn().getHeader("email", String.class);
        BigDecimal amount = exchange.getIn().getHeader("amount", BigDecimal.class);
        Long transferId = exchange.getIn().getHeader("transferId", Long.class);
        TransactionReasonEnum transactionReason = TransactionReasonEnum.valueOf(exchange.getIn().getHeader("transactionReason", String.class));

        String messageType = transactionReason.equals(TransactionReasonEnum.REFUND) ? "Cancelamento" : "Transferência";
        String message = String.format("%s: Transferência %s.", messageType, transactionReason.equals(TransactionReasonEnum.REFUND) ? "cancelada" : "concluída");
        String transferDetails = String.format("ID da transferência: %s, Valor: %s", transferId, amount);

        return SendNotificationDto.builder()
                .email(email)
                .message(message)
                .details(transferDetails)
                .build();
    }
}
