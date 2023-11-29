package backend.com.parcelsystem.Service.Implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.com.parcelsystem.Exception.BadResultException;
import backend.com.parcelsystem.Exception.EntityNotFoundException;
import backend.com.parcelsystem.Models.Cabinet;
import backend.com.parcelsystem.Models.Code;
import backend.com.parcelsystem.Models.Locker;
import backend.com.parcelsystem.Models.Parcel;
import backend.com.parcelsystem.Repository.CabinetRepos;
import backend.com.parcelsystem.Repository.CodeRepos;
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
    @Autowired
    CodeRepos codeRepository;
    
    

    @Override
    public List<Cabinet> getEmptyCabinetsByCity(String city) {
        List<Locker> lockers = lockerService.getByCity(city);
        List<Cabinet> cabinets = new ArrayList<>();

        for (Locker locker : lockers) {
            List<Cabinet> emptyCabinets = cabinetRepository.findByLockerAndEmptyIsTrue(locker);
            cabinets.addAll(emptyCabinets);
        } 
        return cabinets;
    }

    @Override
    public List<Cabinet> getEmptyCabinets() {
       return cabinetRepository.findByEmptyIsTrue();
    }

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
    public Cabinet checkAndUpdateCodeByLockerAndCode(Long lockerId, String code) {
        Locker locker = lockerService.getById(lockerId);
        // verify the code in the list of locker coders to find correct cabinet
        Cabinet cabinet = checkCodeToFindCabinet(locker, code);
        
        // fix the logic for wrong code
        if(cabinet == null) {
            return null;
        }


        System.out.println(cabinet);
        
        //update new code for the cabinet
        Code existingCode = codeService.getCodeByCabinet(cabinet.getId());
        String newCode = codeService.generateRandomCode(cabinet.getLocker().getId());
        existingCode.setCode(newCode);
        codeService.updateCode(existingCode);
        
        return cabinet;
    }
    @Override
    public Cabinet updateEmptyStatus(Cabinet cabinet) {
        if (cabinet != null) {
            cabinet.setEmpty(!cabinet.isEmpty());
            return cabinetRepository.save(cabinet);
        }
        throw new EntityNotFoundException("cabinet not found");
    }
    @Override
    public Cabinet updateFilledStatus(Cabinet cabinet) {
        if (cabinet != null) {
            cabinet.setFilled(cabinet.isFilled());
            return cabinetRepository.save(cabinet);
        }
        throw new EntityNotFoundException("cabinet not found");
    }

    @Override
    public Cabinet updateCabinetAfterBeingPickedupOrderDropOff(Cabinet cabinet, boolean isEmpty, boolean isFilled) {
         if (cabinet != null) {
            cabinet.setFilled(isFilled);
            cabinet.setEmpty(isEmpty);
            return cabinetRepository.save(cabinet);
        }
        throw new EntityNotFoundException("cabinet not found");
    }

    private Cabinet checkCodeToFindCabinet(Locker locker, String inputCode) {
        // get the list of codes
        List<Code> lockerCodes = codeRepository.findByLocker(locker);
        System.out.println(lockerCodes);
        Code correctCode = null;
        
        // check the code inside the list of codes by locker
        for(Code code : lockerCodes) {
            if(code.getCode().equals(inputCode) == true) {
               correctCode = code;
            }
        }

        if(correctCode == null) {
            // throw new BadResultException("the code is not correct");
            return null;
        } else {
            return correctCode.getCabinet();
        }
        
    }

    
}
