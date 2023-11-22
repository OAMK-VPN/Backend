package backend.com.parcelsystem.Service;

import backend.com.parcelsystem.Models.Receiver;
import backend.com.parcelsystem.Models.Sender;

public interface ReceiverService {
    Receiver getById(Long id);
    Receiver getByAuthenticatedUser();
    Receiver getByReceiverEmail(String email);
}
