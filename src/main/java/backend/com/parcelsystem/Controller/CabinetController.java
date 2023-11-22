package backend.com.parcelsystem.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.com.parcelsystem.Models.Cabinet;
import backend.com.parcelsystem.Service.CabinetService;

@RestController
@RequestMapping("/api/cabinets")
public class CabinetController {

    @Autowired
    private CabinetService cabinetService;

    @GetMapping("/locker/{lockerId}")
    public ResponseEntity<List<Cabinet>> getAllByLocker(@PathVariable Long lockerId) {
        List<Cabinet> cabinets = cabinetService.getAllByLocker(lockerId);
        System.out.println(cabinets);
        // return new ResponseEntity<>(cabinets, HttpStatus.OK);
        return new ResponseEntity<>(cabinets, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cabinet> getById(@PathVariable Long id) {
        Cabinet cabinet = cabinetService.getByID(id);
        if (cabinet != null) {
            return new ResponseEntity<>(cabinet, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // @PutMapping("/{id}/updateEmptyStatus")
    // public ResponseEntity<Cabinet> updateEmptyStatus(@PathVariable Long id) {
    //     Cabinet updatedCabinet = cabinetService.updateEmptyStatus(id);

    //     if (updatedCabinet != null) {
    //         return new ResponseEntity<>(updatedCabinet, HttpStatus.OK);
    //     } else {
    //         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //     }
    // }

    // @PutMapping("/{id}/updateCode")
    // public ResponseEntity<Cabinet> updateCode(@PathVariable Long id, @RequestParam String code) {
    //     Cabinet updatedCabinet = cabinetService.checkAndUpdateCode(id, code);
    //     return new ResponseEntity<>(updatedCabinet, HttpStatus.OK);
        
    // }
}
