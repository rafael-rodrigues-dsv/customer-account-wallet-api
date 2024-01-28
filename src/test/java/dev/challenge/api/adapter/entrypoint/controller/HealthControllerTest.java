package dev.challenge.api.adapter.entrypoint.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;

class HealthControllerTest {

  private MockMvc mockMvc;
  private HealthController healthController;

  @BeforeEach
  void setUp() {
    healthController = new HealthController();
    mockMvc = MockMvcBuilders.standaloneSetup(healthController).build();
  }

  @Test
  void testCheckHealth() throws Exception {
    // Arrange
    String expectedResponse = "Health Check: Application is running successfully!";

    // Act & Assert
    mockMvc.perform(MockMvcRequestBuilders.get("/api/health"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(equalTo(expectedResponse)));
  }
}
