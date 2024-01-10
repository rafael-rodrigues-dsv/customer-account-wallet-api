package dev.challenge.api.adapter.entrypoint.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/health")
@Tag(name = "Health", description = "Operations related to Health")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HealthController {

  @GetMapping
  public String checkHealth() {
    return "Health Check: Application is running successfully!";
  }
}
