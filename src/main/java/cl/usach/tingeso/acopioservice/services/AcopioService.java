package cl.usach.tingeso.acopioservice.services;

import cl.usach.tingeso.acopioservice.AcopioServiceApplication;
import cl.usach.tingeso.acopioservice.entities.AcopioEntity;
import cl.usach.tingeso.acopioservice.repositories.AcopioRepository;
import lombok.Generated;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class AcopioService {
    private final AcopioRepository acopioRepository;
    private final Logger logger = Logger.getLogger(AcopioServiceApplication.class.getName());

    @Autowired
    public AcopioService(AcopioRepository acopioRepository) {
        this.acopioRepository = acopioRepository;
    }

    @Autowired
    private RestTemplate restTemplate;

    @Value("${proveedor.service.base.url}")
    private String proveedorServiceBaseUrl;

    public List<AcopioEntity> findAll() {
        return acopioRepository.findAll();
    }

    @Generated
    public ResponseEntity<Void> saveExcel(MultipartFile file){
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            List<AcopioEntity> acopios = excelToList(sheet);
            acopioRepository.saveAll(acopios);
            return ResponseEntity.created(URI.create("/acopio")).build();
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, e.toString());
            return ResponseEntity.status(400).build();
        }
    }
    @Generated
    public List<AcopioEntity> excelToList(Sheet sheet){
        List<AcopioEntity> acopios = new ArrayList<>();
        int counter = 0;
        while (counter <= sheet.getLastRowNum()) {
            Row row = sheet.getRow(counter);
            if (row != null) { // verifica si la fila no está vacía
                if (counter > 0) { // omite la primera fila (encabezados de columna)
                    Cell fechaCell = row.getCell(0);
                    Cell turnoCell = row.getCell(1);
                    Cell proveedorCell = row.getCell(2);
                    Cell kilosCell = row.getCell(3);
                    if (fechaCell == null || turnoCell == null || proveedorCell == null || kilosCell == null) {
                        break;
                    }
                    Date fecha = fechaCell.getDateCellValue();
                    String turno = turnoCell.getStringCellValue();
                    int turnout;
                    if (turno.equals("M")) {
                        turnout = 1;
                    } else {
                        turnout = 2;
                    }
                    String proveedorCodigo = proveedorCell.getStringCellValue();
                    Integer kilos = (int) kilosCell.getNumericCellValue();
                    //Aqui se valida que existe un proveedor con el codigo ingresado
                    String url = proveedorServiceBaseUrl+"exist/" + proveedorCodigo;
                    ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
                    boolean exists = Boolean.TRUE.equals(response.getBody());
                    if (exists) {
                        String id = UUID.randomUUID().toString();
                        if (acopioRepository.findById(id).isPresent()) {
                            id = UUID.randomUUID().toString();
                        }
                        AcopioEntity acopio = new AcopioEntity(id, fecha, turnout, proveedorCodigo, kilos);
                        acopios.add(acopio);
                    }
                }
            }
            counter++;
        }
        return acopios;
    }

}
