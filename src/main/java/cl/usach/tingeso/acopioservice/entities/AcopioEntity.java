package cl.usach.tingeso.acopioservice.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "acopio")
@NoArgsConstructor
@AllArgsConstructor
public class AcopioEntity {
    @Id
    private String id;
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date fecha;
    private Integer turno;
    @Column(name="codigo_proveedor")
    private String codigoProveedor;
    private Integer kilos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getTurno() {
        return turno;
    }

    public void setTurno(Integer turno) {
        this.turno = turno;
    }

    public String getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(String codigo_proveedor) {
        this.codigoProveedor = codigo_proveedor;
    }

    public Integer getKilos() {
        return kilos;
    }

    public void setKilos(Integer kilos) {
        this.kilos = kilos;
    }
}
