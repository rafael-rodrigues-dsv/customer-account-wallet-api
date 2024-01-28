package dev.challenge.api.adapter.entrypoint.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.challenge.api.adapter.entrypoint.command.ServiceCommand;
import dev.challenge.api.adapter.entrypoint.dto.transfer.CancelTransferDto;
import dev.challenge.api.adapter.entrypoint.dto.transfer.PerformTransferDto;
import dev.challenge.api.adapter.entrypoint.dto.transfer.TransferDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ComponentScan("dev.challenge.api.adapter.entrypoint.controller")
class TransferControllerTest {

  private MockMvc mockMvc;
  private ObjectMapper objectMapper;

  private TransferController transferController;

  @Mock
  private ServiceCommand<PerformTransferDto, TransferDto> createTransferCommand;

  @Mock
  private ServiceCommand<CancelTransferDto, TransferDto> cancelTransferCommand;

  @Mock
  private ServiceCommand<Long, TransferDto> findByIdCommand;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);

    transferController = new TransferController(
        createTransferCommand, cancelTransferCommand, findByIdCommand);

    mockMvc = MockMvcBuilders.standaloneSetup(transferController).build();
    objectMapper = new ObjectMapper();
  }

  @Test
  void testPerformTransfer() throws Exception {
    // Arrange
    PerformTransferDto performTransferDto = PerformTransferDto.builder()
        .debitAccountId(1L)
        .creditAccountId(2L)
        .amount(new BigDecimal("100.00"))
        .build();

    TransferDto createdTransfer = new TransferDto();
    createdTransfer.setId(1L);

    when(createTransferCommand.execute(any())).thenReturn(createdTransfer);

    // Act & Assert
    mockMvc.perform(post("/api/v1/transfers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(Objects.requireNonNull(objectMapper.writeValueAsString(performTransferDto))))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(createdTransfer.getId()));
  }

  @Test
  void testCancelTransfer() throws Exception {
    // Arrange
    Long transferId = 1L;

    CancelTransferDto cancelTransferDto = CancelTransferDto.builder()
        .id(transferId)
        .build();

    TransferDto cancelledTransfer = new TransferDto();
    cancelledTransfer.setId(transferId);

    when(cancelTransferCommand.execute(any())).thenReturn(cancelledTransfer);

    // Act & Assert
    mockMvc.perform(delete("/api/v1/transfers/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(cancelledTransfer.getId()));
  }

  @Test
  void testFindById() throws Exception {
    // Arrange
    Long transferId = 1L;

    TransferDto transferDto = TransferDto.builder()
        .id(transferId)
        .build();

    when(findByIdCommand.execute(any())).thenReturn(transferDto);

    // Act & Assert
    mockMvc.perform(get("/api/v1/transfers/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(transferDto.getId()));
  }
}
