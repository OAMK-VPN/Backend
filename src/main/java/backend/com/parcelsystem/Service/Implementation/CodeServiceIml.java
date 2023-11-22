package backend.com.parcelsystem.Service.Implementation;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.com.parcelsystem.Exception.BadResultException;
import backend.com.parcelsystem.Exception.EntityNotFoundException;
import backend.com.parcelsystem.Models.Cabinet;
import backend.com.parcelsystem.Models.Code;
import backend.com.parcelsystem.Models.Locker;
import backend.com.parcelsystem.Repository.CodeRepos;
import backend.com.parcelsystem.Service.CabinetService;
import backend.com.parcelsystem.Service.CodeService;
import backend.com.parcelsystem.Service.LockerService;

@Service
public class CodeServiceIml implements CodeService {

    @Autowired
    CodeRepos codeRepository;
    @Autowired
    LockerService lockerService;

    
    @Override
    public boolean checkCodeExist(String code) {
        return codeRepository.existsByCode(code);
    }

    @Override
    public Code getCodeById(Long id) {
        Code code = codeRepository.findById(id).orElse(null);
        if(code == null) {
            throw new EntityNotFoundException("code not found");
        }
        return code;
    }

    @Override
    public Code getCodeByCabinet(Long cabinetId) {
        return codeRepository.findByCabinetId(cabinetId).orElse(null);
    }

    @Override
    public Code updateCode(Code code) {
       return codeRepository.save(code);
    }

    @Override
    public String generateRandomCode(Long lockerId) {
        String characters = "0123456789";
        Random random = new Random();
        
        // Keep generating codes until a unique one is found
        // check code to avoid 0000, 1111, 1234, 2222, 3333, 4444, 5555, 6666, 7777, 8888, 9999
        while (true) {
            StringBuilder code = new StringBuilder();
            for (int i = 0; i < 4; i++) {
                code.append(characters.charAt(random.nextInt(characters.length())));
            }

            // Check if the generated code already exists
            if (!checkNewCodeByLocker(lockerId, code.toString())) {
                return code.toString();
            }
        }
    }

    private boolean checkNewCodeByLocker(Long lockerId, String newCode) {
        Locker locker = lockerService.getById(lockerId);

        // get the list of codes
        List<String> lockerCodes = codeRepository.findByLocker(locker).stream().map(code -> code.getCode()).collect(Collectors.toList());
        lockerCodes.addAll(List.of("0000", "1111", "1234", "2222", "3333", "4444", "5555", "6666", "7777", "8888", "9999"));

        // check the new code inside the list of codes by locker
        for(String code : lockerCodes) {
            boolean isCheck = code.equals(newCode);
            if(isCheck == true) {
                return true;
            }
        }
        return false;
    }

   
}
