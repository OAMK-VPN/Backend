package backend.com.parcelsystem.Service.Implementation;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.com.parcelsystem.Models.Code;
import backend.com.parcelsystem.Repository.CodeRepos;
import backend.com.parcelsystem.Service.CodeService;

@Service
public class CodeServiceIml implements CodeService {

    @Autowired
    CodeRepos codeRepository;

    
    @Override
    public boolean checkCodeExist(String code) {
        return codeRepository.existsByCode(code);
    }

    @Override
    public Code getCodeById(Long id) {
        return codeRepository.findById(id).orElse(null);
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
    public String generateRandomCode() {
       String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        
        // Keep generating codes until a unique one is found
        while (true) {
            StringBuilder code = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                code.append(characters.charAt(random.nextInt(characters.length())));
            }

            // Check if the generated code already exists
            if (!codeRepository.existsByCode(code.toString())) {
                return code.toString();
            }
        }
    }
}
