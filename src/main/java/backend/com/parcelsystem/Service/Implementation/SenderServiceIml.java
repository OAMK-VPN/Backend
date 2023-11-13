package backend.com.parcelsystem.Service.Implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.com.parcelsystem.Exception.EntityNotFoundException;
import backend.com.parcelsystem.Models.Sender;
import backend.com.parcelsystem.Models.Users;
import backend.com.parcelsystem.Repository.DriverRepos;
import backend.com.parcelsystem.Repository.SenderRepos;
import backend.com.parcelsystem.Service.SenderService;
import backend.com.parcelsystem.Service.UserService;

@Service
public class SenderServiceIml implements SenderService {

    @Autowired
    SenderRepos senderRepos;
    @Autowired
    UserService userService;

    @Override
    public Sender getByAuthenticatedUser() {
       Users authUser = userService.getAuthUser();
        Optional<Sender> entity = senderRepos.findByUser(authUser);
        if(!entity.isPresent()) {
            Sender sender = new Sender(authUser);
            return senderRepos.save(sender);
        } else {
            Sender sender = entity.get();
            return sender;
        }
    }

    @Override
    public Sender getById(Long id) {
        Optional<Sender> entity = senderRepos.findById(id);
        if(!entity.isPresent()) {
            throw new EntityNotFoundException("the sender not found");
        } else {
            Sender sender = entity.get();
            return sender;
        }
    }
    
    
}
