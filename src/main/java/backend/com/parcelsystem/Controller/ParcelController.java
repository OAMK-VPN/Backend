package backend.com.parcelsystem.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.com.parcelsystem.Mapper.ParcelMapper;
import backend.com.parcelsystem.Models.Parcel;
import backend.com.parcelsystem.Models.Enums.ParcelStatus;
import backend.com.parcelsystem.Models.Request.ParcelRequest;
import backend.com.parcelsystem.Models.Request.SendLockerCodeRequest;
import backend.com.parcelsystem.Models.Response.Locker.SendLockerCodeResponse;
import backend.com.parcelsystem.Models.Response.Parcel.ParcelResponse;
import backend.com.parcelsystem.Service.ParcelService;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/parcels")
public class ParcelController {
    @Autowired
    ParcelService parcelService;

    @Autowired
    ParcelMapper parcelMapper;

    // authenticated
    @GetMapping("/receiver")
    public ResponseEntity<List<ParcelResponse>> getAllByAuthReceiver() {
        List<Parcel> parcels = parcelService.getAllByAuthReceiver();
        List<ParcelResponse> res = parcels.stream()
        .map(parcel -> parcelMapper.mapParcelToResponseForReceiver(parcel))
        .collect(Collectors.toList());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // authenticated
    @GetMapping("/sender")
    public ResponseEntity<List<ParcelResponse>> getAllByAuthSender() {
        List<Parcel> parcels = parcelService.getAllByAuthSender();
        List<ParcelResponse> res = parcels.stream()
        .map(parcel -> parcelMapper.mapParcelToResponseForSender(parcel))
        .collect(Collectors.toList());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // authenticated
    @GetMapping("/driver")
    public ResponseEntity<List<ParcelResponse>> getALLByAuthDriver() {
        List<Parcel> parcels = parcelService.getALLByAuthDriverAndStatus();
        List<ParcelResponse> res = parcels.stream()
        .map(parcel -> parcelMapper.mapParcelToResponseForDriverApp(parcel))
        .collect(Collectors.toList());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // authenticated (/parcel/2/role/DRIVER) ->ROLE: DRIVER, RECEIVER, SENDER
    @GetMapping("/parcel/{id}/role/{role}")
    public ResponseEntity<ParcelResponse> getParcelById(@PathVariable Long id, @PathVariable String role) {
        Parcel parcel = parcelService.getParcelById(id);
        ParcelResponse res = parcelMapper.mapParcelToResponseForParcelID(parcel, role);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/public/tracking/{trackingNumber}")
    public ResponseEntity<ParcelResponse> trackingParcel(@PathVariable String trackingNumber) {
        Parcel parcel = parcelService.trackingParcel(trackingNumber);
        ParcelResponse res = parcelMapper.mapParcelToResponseForTrackingNumber(parcel);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/buy")
    public ResponseEntity<ParcelResponse> buyParcel(@RequestBody ParcelRequest req) {
        Parcel parcel = parcelService.buyParcel(req);
        ParcelResponse res = parcelMapper.mapParcelToResponseForSender(parcel);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PutMapping("/public/drop-off/locker/{lockerId}/code/")
    public ResponseEntity<SendLockerCodeResponse> dropOffParcelIntoCabinet(@PathVariable Long lockerId,
            @RequestBody SendLockerCodeRequest request) {
        SendLockerCodeResponse response =  parcelService.dropOffParcelIntoCabinet(lockerId, request.getCode());

        if(response == null) {
            return new ResponseEntity<>(new SendLockerCodeResponse(lockerId, -1, false), HttpStatus.OK);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/public/pick-up/locker/{lockerId}/code/")
    public ResponseEntity<SendLockerCodeResponse> pickedUpParcelByReceiver(@PathVariable Long lockerId, @RequestBody SendLockerCodeRequest request) {
        System.out.println("pick-up parcel");
        SendLockerCodeResponse response = parcelService.pickedUpParcelByReceiver(lockerId, request.getCode());
       if(response == null) {
        return new ResponseEntity<>(new SendLockerCodeResponse(lockerId, -1, false), HttpStatus.OK);
       }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // testing for cronJob
    @PutMapping("/assign-all-to-drivers")
    public ResponseEntity<List<Parcel>> assignAllParcelsToDrivers() {
        List<Parcel> parcels = parcelService.assignAllParcelsToDrivers();
        return new ResponseEntity<>(parcels, HttpStatus.OK);
    }

     // testing for cronJob
    @PutMapping("/check-pickup-expired")
    public ResponseEntity<List<Parcel>> checkAllPickupExpiredParcels() {
        List<Parcel> parcels = parcelService.CheckAllPickupExpiredParcels();
        return new ResponseEntity<>(parcels, HttpStatus.OK);
    }

     // testing for cronJob
    @PutMapping("/check-send-expired")
    public ResponseEntity<List<Parcel>> checkAllSendExpiredParcels() {
        List<Parcel> parcels = parcelService.CheckAllSendExpiredParcels();
        return new ResponseEntity<>(parcels, HttpStatus.OK);
    }

     // testing for cronJob
    @GetMapping("/robot-generating")
    public ResponseEntity<List<ParcelResponse>> getAllParcelsGeneratedByRobot() {
        List<Parcel> parcels = parcelService.generateParcelsAndSendToDriversByRobot();
        List<ParcelResponse> res = parcels.stream()
        .map(parcel -> parcelMapper.mapParcelToResponseForDriverApp(parcel))
        .collect(Collectors.toList());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
