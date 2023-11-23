package backend.com.parcelsystem.Service.Implementation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.com.parcelsystem.Exception.BadResultException;
import backend.com.parcelsystem.Exception.EntityNotFoundException;
import backend.com.parcelsystem.Models.Cabinet;
import backend.com.parcelsystem.Models.City;
import backend.com.parcelsystem.Models.Driver;
import backend.com.parcelsystem.Models.Parcel;
import backend.com.parcelsystem.Models.Receiver;
import backend.com.parcelsystem.Models.Sender;
import backend.com.parcelsystem.Models.Enums.ParcelStatus;
import backend.com.parcelsystem.Models.Request.ParcelRequest;
import backend.com.parcelsystem.Repository.ParcelRepos;
import backend.com.parcelsystem.Service.CabinetService;
import backend.com.parcelsystem.Service.CityService;
import backend.com.parcelsystem.Service.DriverService;
import backend.com.parcelsystem.Service.ParcelService;
import backend.com.parcelsystem.Service.ReceiverService;
import backend.com.parcelsystem.Service.SenderService;

@Service
public class ParcelServiceIml implements ParcelService {
    @Autowired
    private ParcelRepos parcelRepository;

    @Autowired
    private ReceiverService receiverService;

    @Autowired
    private SenderService senderService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private CabinetService cabinetService;
    
    @Autowired
    private CityService cityService;

    @Override
    public Parcel buyParcel(ParcelRequest req) {
        Sender sender = senderService.getByAuthenticatedUser();
        Receiver receiver = receiverService.getByReceiverEmail(req.getEmailReceiver());

        // define the final destination
        String cityDestination = receiver.getUser().getCity();
        String zipcodeDestination  = receiver.getUser().getZipcode();
        String addressDestination  = receiver.getUser().getAddress();

        // get list of empty cabinets from the sender's city
        List<Cabinet> emptyCabinets = cabinetService.getEmptyCabinetsByCity(sender.getUser().getCity());

        // create the new parcel object
        Parcel parcel = new Parcel(generateTrackingNumber(), ParcelStatus.WAITING_FOR_SENDER, cityDestination, addressDestination, zipcodeDestination, receiver, sender, req.getWeigh(), req.getHeigh(), req.getWidth(), req.getLength());
        parcel.setSendExpiry(LocalDateTime.now().plusDays(5));
        System.out.println(parcel);

        // get the fittest cabinet for the the parcel
        Cabinet cabinet = checkSize(emptyCabinets, parcel);
        System.out.println("get fittest cabinet");
        System.out.println(cabinet);

        // update empty status for the cabinet
        cabinet = cabinetService.updateEmptyStatus(cabinet);
        
        // add the matching cabinet for the parcel
        parcel.setCabinet(cabinet);
        parcelRepository.save(parcel);

        //send the notification here
        // TODOS -> send notification to the receiver (excludes the code from the notification). 
        // TODOS -> Send notification to sender contains the parcel, the cabinet, the locker, the code to open cabinet in order that the sender can drop the parcel into the cabinet (include the code in notification for sender)

        return parcel;
    }

    @Override
    public List<Parcel> getByParcelStatus(ParcelStatus status) {
        List<Parcel> parcels = parcelRepository.findByStatus(status);
        return parcels;
    }

    @Override
    public List<Parcel> getALLByAuthDriverAndStatus() {
        Driver driver = driverService.getByAuthenticatedUser();
        LocalDateTime currentTime = LocalDateTime.now();
        ParcelStatus status = ParcelStatus.IN_DELIVERY;
        
        return parcelRepository.findByStatusAndDriver(status, driver);
    }

    @Override
    public List<Parcel> getAllByAuthReceiver() {
        Receiver receiver = receiverService.getByAuthenticatedUser();
        return parcelRepository.findByReceiver(receiver);
    }

    @Override
    public List<Parcel> getAllByAuthSender() {
        Sender sender = senderService.getByAuthenticatedUser();
        return parcelRepository.findBySender(sender);
    }

     @Override
    public Parcel dropOffParcelIntoCabinet(Long lockerId, String code) {
        // find the cabinet of locker and code. then update the cabinet code and filled status
        Cabinet cabinet = cabinetService.checkAndUpdateCodeByLockerAndCode(lockerId, code);

        // update the cabinet, set empty status by false, set isFilled by true
        cabinetService.updateCabinetAfterBeingPickedupOrderDropOff(cabinet, false, true);


        // find the parcel by current cabinet
        List<Parcel> parcels = parcelRepository.findByCabinet(cabinet);
        if (parcels.size() != 1) {
            throw new BadResultException("the list of parcel using same cabinet must be equals to 1 because the cabinet must be removed from the parcel whenever the parcel is get out the cabinet");
        } 
        Parcel parcel = parcels.get(0);

        // check who drops off the parcel into the assigned cabinet. Then update status and the sentDate  for the parcel
        if (parcel.getStatus().equals(ParcelStatus.WAITING_FOR_SENDER)) {

            // this case means that the sender drops off the parcel
            parcel.setStatus(ParcelStatus.WAITING_FOR_DRIVER);
            parcel.setSendDateSender(LocalDateTime.now());

        } else  if (parcel.getStatus().equals(ParcelStatus.IN_DELIVERY)) {

            // this case means that the driver drops off the parcel
            parcel.setStatus(ParcelStatus.WAITING_FOR_RECEIVER);
            parcel.setSendDateDriver(LocalDateTime.now());
            parcel.setPickupAvailability(true);
            parcel.setPickupExpiry(LocalDateTime.now().plusDays(10));
        }

        parcel = parcelRepository.save(parcel);
        
        // TODOS -> if sender drops off -> send to notification to the receiver (note -> exclude the code from the notification for receiver) 
        // TODOS -> if driver drops off -> send to notification to the receiver (note -> include the code in the notification for receiver) 
        // TODOS send to notification  to the sender (note -> exclude the code from the notification for sender)

        return parcel;
    }



    @Override
    public Parcel pickedUpParcelByReceiver(Long lockerId, String code) {
        // find the cabinet of locker and code. then update the cabinet code and filled status
        Cabinet cabinet = cabinetService.checkAndUpdateCodeByLockerAndCode(lockerId, code);

        // find the parcel by current cabinet
        List<Parcel> parcels = parcelRepository.findByCabinet(cabinet);
        if (parcels.size() != 1) {
            throw new BadResultException("the list of parcel using same cabinet must be equals to 1 because the cabinet must be removed from the parcel whenever the parcel is get out the cabinet");
        } 
        Parcel parcel = parcels.get(0);


        // update parcel after being picked up by the receiver
        parcel.setStatus(ParcelStatus.PICKED_UP_BY_RECIEVER);
        parcel.setPickupDate(LocalDateTime.now());
        parcel.setCabinet(null);
        parcelRepository.save(parcel);

        // update the cabinet, set empty status by true, set isFilled by false
        cabinetService.updateCabinetAfterBeingPickedupOrderDropOff(cabinet, true, false);

        // TODOS ->  send to notification to the receiver (note -> exclude the code from the notification for receiver) 
        // TODOS ->  send to notification to the sender (note -> exclude the code from the notification for receiver) 

        return parcel;
    }

   
   @Override
   public Parcel assignParcelToNewCabinet(Parcel parcel) {

        // remove the old cabinet from the current parcel and update statuses for the old cabinet
        Cabinet oldCabinet = parcel.getCabinet();
        cabinetService.updateCabinetAfterBeingPickedupOrderDropOff(oldCabinet, true, false);

        // get list of empty cabinets from the receiver's city
        List<Cabinet> emptyCabinets = cabinetService.getEmptyCabinetsByCity(parcel.getCity());

        // get the fittest cabinet for the the parcel
        Cabinet newcabinet = checkSize(emptyCabinets, parcel);

        // update empty status for the new cabinet
        cabinetService.updateCabinetAfterBeingPickedupOrderDropOff(newcabinet, false, false);
        
        // add the matching cabinet for the parcel && update the status for parcel
        parcel.setCabinet(newcabinet);
        parcel.setStatus(ParcelStatus.ASSIGNING_NEW_CABINET_FOR_PICKUP);
        parcelRepository.save(parcel);

        return parcel;
   }

   
   

    @Override
    public Parcel assignParcelToDriver(Parcel parcel, Driver driver) {
        if(!driver.getUser().getCity().equals(parcel.getCity())) {
            throw new BadResultException("the city not the same");
        }
        parcel.setDriver(driver);
        parcel.setStatus(ParcelStatus.IN_DELIVERY);
        parcel.setReceiveDateDriver(LocalDateTime.now());
        return parcelRepository.save(parcel);

        // TODOS ->  send to notification to the receiver (note -> exclude the code from the notification for receiver) 
        // TODOS ->  send to notification to the sender (note -> exclude the code from the notification for receiver) 
        // TODOS ->  in the driver app, all parcel shows the code, do not need the notification
    }

    // @Override
    // public List<Parcel> assignAllParcelsToDrivers() {
    //     assignAllParcelsToNewCabinets();

    //     // get the list of cities
    //     List<City> cities = cityService.getAll();
        
    //     // create empty list to store parcels after being assigned to new cabinets and drivers
    //     List<Parcel> assignedParcels = new ArrayList<>();

    //     // loop through each city -> get list of drivers and parcels in the same city -> group parcels and drivers by the same city -> divides the number of parcels equally to each driver in the same city 
    //     for (City city : cities) {
    //         List<Parcel> parcels = getAllByCityAndStatus(city.getName(), ParcelStatus.ASSIGNING_NEW_CABINET_FOR_PICKUP);
    //         List<Driver> drivers = driverService.getDriversByCity(city.getName());
    //         int equalNumber = (int) Math.floor(parcels.size() / drivers.size());
    //         int leftNumber = parcels.size() % drivers.size();
    //         int indexNumber = 0;

    //         for(Driver driver: drivers) {
    //             int numberOfAssignedParcels = equalNumber + (leftNumber > 0 ? 1 : 0);
    //             leftNumber = leftNumber > 0 ? leftNumber-- : 0;

    //             for(int i = 0; i < numberOfAssignedParcels; i++) {
    //                 Parcel parcel = parcels.get(indexNumber);
    //                 indexNumber++;
                    
    //                 // assigned the parcel to the driver
    //                 parcel = assignParcelToDriver(parcel, driver);
    //                 assignedParcels.add(parcel);
    //             }
    //         }
    //     }
    //     return assignedParcels;
    // }

    @Override
public List<Parcel> assignAllParcelsToDrivers() {
    assignAllParcelsToNewCabinets();

    // get the list of cities
    List<City> cities = cityService.getAll();
    
    // create empty list to store parcels after being assigned to new cabinets and drivers
    List<Parcel> assignedParcels = new ArrayList<>();

    // loop through each city -> get list of drivers and parcels in the same city -> group parcels and drivers by the same city -> divides the number of parcels equally to each driver in the same city 
    for (City city : cities) {
        List<Parcel> parcels = getAllByCityAndStatus(city.getName(), ParcelStatus.ASSIGNING_NEW_CABINET_FOR_PICKUP);
        List<Driver> drivers = driverService.getDriversByCity(city.getName());

        // check if there are drivers available
        if (drivers.isEmpty()) {
            System.out.println("No drivers available in " + city.getName());
            continue; // Move on to the next city
        }

        int equalNumber = (int) Math.floor(parcels.size() / drivers.size());
        int leftNumber = parcels.size() % drivers.size();
        int indexNumber = 0;

        for (Driver driver : drivers) {
            int numberOfAssignedParcels = equalNumber + (leftNumber > 0 ? 1 : 0);
            leftNumber = leftNumber > 0 ? leftNumber-- : 0;

            for (int i = 0; i < numberOfAssignedParcels; i++) {
                Parcel parcel = parcels.get(indexNumber);
                indexNumber++;
                
                // assigned the parcel to the driver
                parcel = assignParcelToDriver(parcel, driver);
                assignedParcels.add(parcel);
            }
        }
    }
    return assignedParcels;
}

    @Override
    public List<Parcel> CheckAllPickupExpiredParcels() {
        
        return checkAndRemoveAllExpiredParcels(ParcelStatus.WAITING_FOR_RECEIVER, ParcelStatus.RETURN_STORAGE_DUE_TO_EXPIRY);
    }

    @Override
    public List<Parcel> CheckAllSendExpiredParcels() {

        return checkAndRemoveAllExpiredParcels(ParcelStatus.WAITING_FOR_SENDER, ParcelStatus.PARCEL_CANCELLED_DUE_TO_SENDING_LATE_BY_SENDER);
    }

    @Override
    public List<Parcel> assignAllParcelsToNewCabinets() {
        List<Parcel> parcels = getByParcelStatus(ParcelStatus.WAITING_FOR_DRIVER);
        return parcels.stream().map(parcel -> assignParcelToNewCabinet(parcel)).collect(Collectors.toList());
    }

    @Override
    public List<Parcel> getAllByCityAndStatus(String city, ParcelStatus status) {
        return parcelRepository.findByStatusAndCity(status, city);
    }

    @Override
    public Parcel trackingParcel(String trackingNumber) {
        Optional<Parcel> entity = parcelRepository.findByTrackingNumber(trackingNumber);
        if(!entity.isPresent()) {
            throw new EntityNotFoundException("the parcel not found");
        }
        return entity.get();
    }
    @Override
    public Parcel getParcelById(Long id) {
        Optional<Parcel> entity = parcelRepository.findById(id);
        if(!entity.isPresent()) {
            throw new EntityNotFoundException("the parcel not found");
        }
        return entity.get();
    }

    private String generateTrackingNumber() {
        return UUID.randomUUID().toString();
    }

    private Cabinet checkSize(List<Cabinet> emptyCabinets, Parcel parcel) {
        double maxFitScore = -1.0; // Initialize with a non-positive value
        Cabinet fittestCabinet = null;
    
        for (Cabinet cabinet : emptyCabinets) {
            double fitScore = calculateFitScore(parcel, cabinet);
    
            if (fitScore > maxFitScore) {
                maxFitScore = fitScore;
                fittestCabinet = cabinet;
            }
        }
        if(fittestCabinet == null) {
            System.out.println("fitness cabinet cannot check");
        }
        return fittestCabinet;
    };


    private double calculateFitScore(Parcel parcel, Cabinet cabinet) {
        // Check if the cabinet is large enough in each dimension
        boolean isHeightFit = cabinet.getHeigh() >= parcel.getHeigh();
        boolean isWidthFit = cabinet.getWidth() >= parcel.getWidth();
        boolean isWeightFit = cabinet.getWeigh() >= parcel.getWeigh();
        boolean isLengthFit = cabinet.getLength() >= parcel.getLength();
    
        // If all dimensions are large enough, the cabinet is a fit
        if (isHeightFit && isWidthFit && isWeightFit && isLengthFit) {
            return 1.0; 
        } else {
            return 0.0; 
        }
    }

    private Parcel updateParcelStatus(Parcel parcel, ParcelStatus status) {
        parcel.setStatus(status);
        return parcelRepository.save(parcel);
    }

    private Parcel disableParcel(Parcel parcel, ParcelStatus newStatus) {
        parcel.setStatus(newStatus);
        parcel.setCabinet(null);
        return parcelRepository.save(parcel);
    }

    private List<Parcel> checkAndRemoveAllExpiredParcels(ParcelStatus expiredStatus, ParcelStatus newStatus) {
        List<Parcel> parcels = parcelRepository.findExpiredParcelsByStatus(LocalDateTime.now(), expiredStatus);


        // update parcel status by "return storage due to expiry"
        parcels = parcels.stream().map(parcel -> disableParcel(parcel, newStatus)).collect(Collectors.toList());
        return parcels;
    }

    @Override
    public List<Parcel> generateParcelsAndSendToDrivers() {
        List<Cabinet> emptyCabinets = cabinetService.getEmptyCabinets();
        int halfOfEmptyCabinets = emptyCabinets.size() / 2;

        for(int i = 0; i < halfOfEmptyCabinets; i++) {

        }

        return null;
    }

    private Parcel createParcelByRobot(Cabinet cabinet) {

        
        return null;
    }
}
