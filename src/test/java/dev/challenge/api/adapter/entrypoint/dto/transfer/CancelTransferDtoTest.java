package dev.challenge.api.adapter.entrypoint.dto.transfer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CancelTransferDtoTest {

  @Test
  void testValidCancelTransferDto() {
    // Arrange
    CancelTransferDto cancelTransferDto = CancelTransferDto.builder()
        .id(1L)
        .build();

    // Assert
    assertThat(cancelTransferDto).isNotNull();
  }

  @Test
  void testGettersAndSetters() {
    // Arrange
    CancelTransferDto cancelTransferDto = new CancelTransferDto();

    // Act
    cancelTransferDto.setId(1L);

    // Assert
    assertThat(cancelTransferDto.getId()).isEqualTo(1L);
  }

  @Test
  void testNoArgsConstructor() {
    // Act
    CancelTransferDto cancelTransferDto = new CancelTransferDto();

    // Assert
    assertThat(cancelTransferDto).isNotNull();
  }

  @Test
  void testAllArgsConstructor() {
    // Arrange
    CancelTransferDto cancelTransferDto = new CancelTransferDto(1L);

    // Assert
    assertThat(cancelTransferDto).isNotNull();
    assertThat(cancelTransferDto.getId()).isEqualTo(1L);
  }
}
