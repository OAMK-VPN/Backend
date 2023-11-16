package backend.com.parcelsystem.Service.Implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.com.parcelsystem.Exception.BadResultException;
import backend.com.parcelsystem.Exception.EntityNotFoundException;
import backend.com.parcelsystem.Models.Cabinet;
import backend.com.parcelsystem.Models.Code;
import backend.com.parcelsystem.Models.Locker;
import backend.com.parcelsystem.Repository.CabinetRepos;
import backend.com.parcelsystem.Service.CabinetService;
import backend.com.parcelsystem.Service.CodeService;
import backend.com.parcelsystem.Service.LockerService;

@Service
public class CabinetServiceIml implements CabinetService {

    @Autowired
    CabinetRepos cabinetRepository;
    @Autowired
    CodeService codeService;
    @Autowired
    LockerService lockerService;

    @Override
    public List<Cabinet> getAllByLocker(Long lockerId) {
        Locker locker = lockerService.getById(lockerId);
        return cabinetRepository.findByLocker(locker);
    }

    @Override
    public Cabinet getByID(Long id) {
        return cabinetRepository.findById(id).orElse(null);
    }

    @Override
    public Cabinet checkAndUpdateCode(Long id, String code) {
        Cabinet cabinet = cabinetRepository.findById(id).orElse(null);
        System.out.println(cabinet);
        if (cabinet != null) {
            Code existingCode = codeService.getCodeByCabinet(id);

            // Check if the new code already exists or not
            if (codeService.checkCodeExist(code)) {
                // If not, update the code
                String newCode = codeService.generateRandomCode();
                existingCode.setCode(newCode);
                codeService.updateCode(existingCode);
                return cabinet;
            } else {
                throw new BadResultException("code is not correct");
            }
        }
        return null;
    }

    @Override
    public Cabinet updateEmptyStatus(Long id) {
        Cabinet cabinet = cabinetRepository.findById(id).orElse(null);

        if (cabinet != null) {
            cabinet.setEmpty(!cabinet.isEmpty());
            return cabinetRepository.save(cabinet);
        }
        return null;
    }

    
}
