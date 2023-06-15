package cl.usach.tingeso.acopioservice.Repositories;

import cl.usach.tingeso.acopioservice.Entities.AcopioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AcopioRepository extends JpaRepository<AcopioEntity,String> {
    Optional<AcopioEntity> findById(String id);
    List<AcopioEntity> findByFecha(Date fecha);
}
