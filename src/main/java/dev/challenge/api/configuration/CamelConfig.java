package dev.challenge.api.configuration;

import lombok.Generated;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.engine.DefaultProducerTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Generated
@Configuration
public class CamelConfig {

  private CamelContext camelContext;

  @Bean
  public CamelContext camelContext() throws Exception {
    if (camelContext == null) {
      camelContext = new DefaultCamelContext();
      camelContext.start();
      producerTemplate(camelContext).start();
    }

    return camelContext;
  }

  @Bean
  public ProducerTemplate producerTemplate(CamelContext camelContext) {
    return new DefaultProducerTemplate(camelContext);
  }
}
