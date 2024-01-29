package dev.challenge.api.adapter.notification.dto.notification;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SendNotificationDtoTest {

  @Test
  void testBuilder() {
    // Arrange
    SendNotificationDto sendNotificationDto = SendNotificationDto.builder()
        .email("john.doe@example.com")
        .message("Test Message")
        .details("Details for testing")
        .build();

    // Assert
    assertThat(sendNotificationDto.getEmail()).isEqualTo("john.doe@example.com");
    assertThat(sendNotificationDto.getMessage()).isEqualTo("Test Message");
    assertThat(sendNotificationDto.getDetails()).isEqualTo("Details for testing");
  }

  @Test
  void testGettersAndSetters() {
    // Arrange
    SendNotificationDto sendNotificationDto = new SendNotificationDto();

    sendNotificationDto.setEmail("john.doe@example.com");
    sendNotificationDto.setMessage("Test Message");
    sendNotificationDto.setDetails("Details for testing");

    // Assert
    assertThat(sendNotificationDto.getEmail()).isEqualTo("john.doe@example.com");
    assertThat(sendNotificationDto.getMessage()).isEqualTo("Test Message");
    assertThat(sendNotificationDto.getDetails()).isEqualTo("Details for testing");
  }

  @Test
  void testNoArgsConstructor() {
    // Act
    SendNotificationDto sendNotificationDto = new SendNotificationDto();

    // Assert
    assertThat(sendNotificationDto).isNotNull();
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    SendNotificationDto sendNotificationDto = new SendNotificationDto("john.doe@example.com", "Test Message", "Details for testing");

    // Assert
    assertThat(sendNotificationDto).isNotNull();
    assertThat(sendNotificationDto.getEmail()).isEqualTo("john.doe@example.com");
    assertThat(sendNotificationDto.getMessage()).isEqualTo("Test Message");
    assertThat(sendNotificationDto.getDetails()).isEqualTo("Details for testing");
  }
}
