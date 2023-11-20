package backend.com.parcelsystem.Service;

import backend.com.parcelsystem.Models.Code;

public interface CodeService {
    boolean checkCodeExist(String code);
    Code getCodeById(Long id);
    Code getCodeByCabinet(Long cabinetID);
    Code updateCode(Code code);
    String generateRandomCode(Long lockerId);
} 
