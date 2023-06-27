package cl.usach.tingeso.acopioservice.REST;

import cl.usach.tingeso.acopioservice.services.AcopioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class AcopioREST {
    @Autowired
    private AcopioService acopioService;

    @PostMapping(value = "acopio/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> save(@ModelAttribute MultipartFile file) {
        return acopioService.saveExcel(file);
    }

    @GetMapping("acopio/days")
    public ResponseEntity<Map<String, Integer>> getAcopiosFromDateByCodigoProveedor(@RequestParam Date date, @RequestParam String codigo_proveedor) {
        List<Integer> dias = acopioService.getAcopiosFromDateByCodigoProveedor(date, codigo_proveedor);
        Map<String, Integer> response = new HashMap<>();
        response.put("MT", dias.get(0));
        response.put("M", dias.get(1));
        response.put("T", dias.get(2));
        return ResponseEntity.ok(response);
    }

    @GetMapping("acopio/proveedor/kilos")
    public ResponseEntity<Integer> getKilosFromDateByProveedor(@RequestParam Date date,
                                                               @RequestParam String codigo_proveedor){
        try{
            Integer kilos = acopioService.getAcopioTotal(date,codigo_proveedor);
            return ResponseEntity.ok(kilos);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().build();
        }
    }


}