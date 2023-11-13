package backend.com.parcelsystem.Service;

import backend.com.parcelsystem.Models.Sender;

public interface SenderService {
    Sender getById(Long id);
    Sender getByAuthenticatedUser();
}
