package dev.challenge.api.adapter.notification.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.challenge.api.adapter.notification.dto.notification.SendNotificationDto;
import dev.challenge.api.configuration.NotificationConfig;
import dev.challenge.api.domain.enumeration.TransactionReasonEnum;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CamelNotificationServiceImplTest extends CamelTestSupport {

  @Mock
  private CamelContext camelContext;

  @Mock
  private ProducerTemplate producerTemplate;

  @Mock
  private NotificationConfig notificationConfig;

  @Mock
  private ObjectMapper objectMapper;

  @InjectMocks
  private CamelNotificationServiceImpl notificationService;

  @Captor
  private ArgumentCaptor<Map<String, Object>> headersCaptor;

  @Test
  void testSendNotification_Success() throws IOException {
    // Arrange
    String email = "test@example.com";
    BigDecimal amount = BigDecimal.TEN;
    Long transferId = 123L;
    TransactionReasonEnum transactionReason = TransactionReasonEnum.REFUND;

    ReflectionTestUtils.setField(notificationConfig, "notificationServiceUrl", "http://example.com");

    // Act
    notificationService.sendNotification(email, amount, transferId, transactionReason);

    // Assert
    verify(producerTemplate).sendBodyAndHeaders(Mockito.eq("direct:sendNotification"), Mockito.isNull(), headersCaptor.capture());

    Map<String, Object> headers = headersCaptor.getValue();
    assertEquals(email, headers.get("email"));
    assertEquals(amount, headers.get("amount"));
    assertEquals(transferId, headers.get("transferId"));
    assertEquals(transactionReason.name(), headers.get("transactionReason"));
  }

  @Test
  void testCreateRouteIfNotCreated_RouteNotCreated() throws Exception {
    // Act
    notificationService.sendNotification("test@example.com", BigDecimal.TEN, 123L, TransactionReasonEnum.REFUND);

    // Assert
    verify(camelContext).addRoutes(Mockito.any(RouteBuilder.class));
  }

  @Test
  void testCreateRouteIfNotCreated_RouteAlreadyCreated() throws Exception {
    // Arrange
    notificationService.sendNotification("test@example.com", BigDecimal.TEN, 123L, TransactionReasonEnum.REFUND);

    // Act
    notificationService.sendNotification("test@example.com", BigDecimal.TEN, 123L, TransactionReasonEnum.REFUND);

    // Assert
    verify(camelContext, Mockito.times(1)).addRoutes(Mockito.any(RouteBuilder.class));
  }

  @Test
  void testCreateNotificationRequestDto() {
    // Arrange
    String email = "test@example.com";
    BigDecimal amount = BigDecimal.TEN;
    Long transferId = 123L;
    TransactionReasonEnum transactionReason = TransactionReasonEnum.REFUND;

    String requestBody = "Conteúdo da mensagem, se necessário";
    org.apache.camel.Exchange exchange = createExchangeWithBody(requestBody);

    // Adicione headers ao exchange
    exchange.getIn().setHeader("email", email);
    exchange.getIn().setHeader("amount", amount);
    exchange.getIn().setHeader("transferId", transferId);
    exchange.getIn().setHeader("transactionReason", transactionReason.name());

    // Act
    SendNotificationDto sendNotificationDto = notificationService.createNotificationRequestDto(exchange);

    // Assert
    assertEquals(email, sendNotificationDto.getEmail());
    assertEquals("Cancelamento: Transferência cancelada.", sendNotificationDto.getMessage());
    assertEquals("ID da transferência: 123, Valor: 10", sendNotificationDto.getDetails());
  }
}
