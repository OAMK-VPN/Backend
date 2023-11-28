package backend.com.parcelsystem.Models.Response.Locker;

import lombok.Data;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Data
public class SendLockerCodeResponse {
  private Long lockerId;
  private boolean isOpen;
}