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

  // @Override
  // public String toString() {
  //   return "SendLockerCodeResponse [lockerId=" + lockerId + ", num=" + num + ", isOpen=" + isOpen + "]";
  // }

  // public SendLockerCodeResponse(Long lockerId, boolean isOpen) {
  //   this.lockerId = lockerId;
  //   this.isOpen = isOpen;
  // }


  
  
}