package co.edu.uniandes.dse.parcial1.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class ConciertoEntity extends BaseEntity {

    private String nombre;
    private Long presupuesto;
    private String nombreartista;
    private Date fechaconcierto;
    private Integer capacidad;

    @PodamExclude
    @ManyToOne
    private EstadioEntity estadio;
}
