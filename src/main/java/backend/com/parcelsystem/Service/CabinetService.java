package backend.com.parcelsystem.Service;

import java.util.List;

import backend.com.parcelsystem.Models.Cabinet;

public interface CabinetService {
    List<Cabinet> getAllByLocker(Long lockerID);
    List<Cabinet> getEmptyCabinetsByCity(String city);
    List<Cabinet> getEmptyCabinets();
    Cabinet getByID(Long id);
    Cabinet checkAndUpdateCodeByLockerAndCode(Long lockerId, String code);
    Cabinet updateEmptyStatus(Cabinet cabinet);
    Cabinet updateFilledStatus(Cabinet cabinet);
    Cabinet updateCabinetAfterBeingPickedupOrderDropOff(Cabinet cabinet, boolean isEmpty, boolean isFilled);
} 
