package co.edu.uniandes.dse.parcial1.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

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
