package cl.usach.tingeso.acopioservice.REST;

import cl.usach.tingeso.acopioservice.services.AcopioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping
public class AcopioREST {
    @Autowired
    private AcopioService acopioService;

    @PostMapping(value = "acopio/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> save(@ModelAttribute MultipartFile file) {
        return acopioService.saveExcel(file);
    }
}