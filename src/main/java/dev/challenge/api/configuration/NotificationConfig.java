package dev.challenge.api.configuration;

import lombok.Generated;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Generated
@Configuration
public class NotificationConfig {

  @Value("${notification.service.url}")
  public String notificationServiceUrl;
}
