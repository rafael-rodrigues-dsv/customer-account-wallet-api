package dev.challenge.api.adapter.notification.dto.notification;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NotificationDtoTest {

  @Test
  void testBuilder() {
    // Arrange
    NotificationDto notificationDto = NotificationDto.builder()
        .messageSent(true)
        .build();

    // Assert
    assertThat(notificationDto.isMessageSent()).isTrue();
  }

  @Test
  void testGettersAndSetters() {
    // Arrange
    NotificationDto notificationDto = new NotificationDto();

    notificationDto.setMessageSent(true);

    // Assert
    assertThat(notificationDto.isMessageSent()).isTrue();
  }

  @Test
  void testNoArgsConstructor() {
    // Act
    NotificationDto notificationDto = new NotificationDto();

    // Assert
    assertThat(notificationDto).isNotNull();
    assertThat(notificationDto.isMessageSent()).isFalse(); // Default value for boolean
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    NotificationDto notificationDto = new NotificationDto(true);

    // Assert
    assertThat(notificationDto).isNotNull();
    assertThat(notificationDto.isMessageSent()).isTrue();
  }
}
