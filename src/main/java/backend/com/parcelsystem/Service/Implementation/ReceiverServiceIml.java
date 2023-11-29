package backend.com.parcelsystem.Service.Implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.com.parcelsystem.Exception.EntityNotFoundException;
import backend.com.parcelsystem.Models.Receiver;
import backend.com.parcelsystem.Models.Users;
import backend.com.parcelsystem.Repository.ReceiverRepos;
import backend.com.parcelsystem.Repository.SenderRepos;
import backend.com.parcelsystem.Service.ReceiverService;
import backend.com.parcelsystem.Service.UserService;

@Service
public class ReceiverServiceIml implements ReceiverService {
    
    @Autowired
    ReceiverRepos receiverRepos;
    @Autowired
    UserService userService;

    @Override
    public Receiver getByAuthenticatedUser() {
       Users authUser = userService.getAuthUser();
        Optional<Receiver> entity = receiverRepos.findByUser(authUser);
        if(!entity.isPresent()) {
            Receiver receiver = new Receiver(authUser);
            return receiverRepos.save(receiver);
        } else {
            Receiver receiver = entity.get();
            return receiver;
        }
    }

    @Override
    public Receiver getById(Long id) {
        Optional<Receiver> entity = receiverRepos.findById(id);
        if(!entity.isPresent()) {
            throw new EntityNotFoundException("the Receiver not found");
        } else {
            Receiver receiver = entity.get();
            return receiver;
        }
    }

    @Override
    public Receiver getByReceiverEmail(String email) {
        Users user = userService.getUserByEmail(email);
        Optional<Receiver> entity = receiverRepos.findByUser(user);
        if (!entity.isPresent()) {
            Receiver receiver = new Receiver(user);
            return receiverRepos.save(receiver);
        } else {
            Receiver receiver = entity.get();
            return receiver;
        }
    }

    @Override
    public List<Receiver> getByCity(String city) {
        return receiverRepos.findReceiversByCity(city);
    }
}
