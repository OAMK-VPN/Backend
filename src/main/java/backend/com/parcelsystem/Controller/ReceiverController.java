package backend.com.parcelsystem.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.com.parcelsystem.Mapper.ReceiverMapper;
import backend.com.parcelsystem.Models.Response.ReceiverResponse;
import backend.com.parcelsystem.Service.ReceiverService;

@RestController
@RequestMapping("/api/receivers")
public class ReceiverController {
    @Autowired
    ReceiverService receiverService;
    @Autowired
    ReceiverMapper receiverMapper;


    // get receiver by id (add bearer token to request)
    @GetMapping("/receiver/id/{id}")
    public ResponseEntity<ReceiverResponse> getById(@PathVariable Long id) {
        ReceiverResponse res = receiverMapper.mapReceiverToResponse(receiverService.getById(id));
        return new ResponseEntity<ReceiverResponse>(res, HttpStatus.OK);
    }
     // get receiver by authenticated user or if the receiver is not existent, the method will automatically create new receiver account for the current authenticated user (add bearer token to request)
    @GetMapping("/receiver/authenticated")
    public ResponseEntity<ReceiverResponse> getByAuthUser() {
        ReceiverResponse res = receiverMapper.mapReceiverToResponse(receiverService.getByAuthenticatedUser());
        return new ResponseEntity<ReceiverResponse>(res, HttpStatus.OK);
    }
}
