package backend.com.parcelsystem.Service;

import java.util.List;

import backend.com.parcelsystem.Models.Cabinet;

public interface CabinetService {
    List<Cabinet> getAllByLocker(Long lockerID);
    Cabinet getByID(Long id);
    Cabinet updateEmptyStatus(Long id);
    Cabinet checkAndUpdateCode(Long id, String code);
} 
