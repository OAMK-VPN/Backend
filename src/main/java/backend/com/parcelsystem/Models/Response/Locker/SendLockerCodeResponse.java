package backend.com.parcelsystem.Models.Response.Locker;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Data
public class SendLockerCodeResponse {
  private Long lockerId;

  private int num;
  
  @JsonProperty("isOpen")
  private boolean isOpen;
}