package dev.challenge.api.adapter.entrypoint.dto.transfer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CancelTransferDto {
  @Getter(onMethod = @__(@JsonIgnore))
  @Setter(onMethod = @__(@JsonIgnore))
  private Long id;
}
