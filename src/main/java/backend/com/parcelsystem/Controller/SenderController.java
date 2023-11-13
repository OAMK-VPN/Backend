package backend.com.parcelsystem.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.com.parcelsystem.Mapper.DriverMapper;
import backend.com.parcelsystem.Mapper.SenderMapper;
import backend.com.parcelsystem.Models.Response.SenderResponse;
import backend.com.parcelsystem.Models.Response.SenderResponse;
import backend.com.parcelsystem.Service.DriverService;
import backend.com.parcelsystem.Service.SenderService;

@RestController
@RequestMapping("/api/senders")
public class SenderController {
    @Autowired
    SenderMapper senderMapper;
    @Autowired
    SenderService senderService;


    // get sender by id (add bearer token to request)
    @GetMapping("/sender/id/{id}")
    public ResponseEntity<SenderResponse> getById(@PathVariable Long id) {
        SenderResponse res = senderMapper.mapSenderToResponse(senderService.getById(id));
        return new ResponseEntity<SenderResponse>(res, HttpStatus.OK);
    }

    // get sender by authenticated user or if the sender is not existent, the method will automatically create new sender account for the current authenticated user (add bearer token to request)
    @GetMapping("/sender/authenticated")
    public ResponseEntity<SenderResponse> getByAuthUser() {
        SenderResponse res = senderMapper.mapSenderToResponse(senderService.getByAuthenticatedUser());
        return new ResponseEntity<SenderResponse>(res, HttpStatus.OK);
    }
}
