package cl.usach.tingeso.acopioservice.repositories;

import cl.usach.tingeso.acopioservice.entities.AcopioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AcopioRepository extends JpaRepository<AcopioEntity,String> {
    Optional<AcopioEntity> findById(String id);
    List<AcopioEntity> findByFecha(Date fecha);
    List<AcopioEntity> findByCodigoProveedor(String codigo_proveedor);
    List<AcopioEntity> findAcopioEntitiesByCodigoProveedorAndFechaGreaterThanEqual(String codigo_proveedor, Date fecha);

}
