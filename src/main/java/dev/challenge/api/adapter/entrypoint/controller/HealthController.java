package dev.challenge.api.adapter.entrypoint.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/health")
@Tag(name = "Health", description = "Operations related to Health")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HealthController {

  @ApiResponse(responseCode = "200", description = "OK", content = @Content)
  @GetMapping
  public String checkHealth() {
    return "Health Check: Application is running successfully!";
  }
}
