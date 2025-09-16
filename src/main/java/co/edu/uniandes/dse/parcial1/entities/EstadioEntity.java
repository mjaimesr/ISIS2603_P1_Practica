package co.edu.uniandes.dse.parcial1.entities;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class EstadioEntity extends BaseEntity {

    private String nombre;
    private Long precioAlquiler;
    private String nombreciudad;
    private Integer aforo;

    @PodamExclude
    @OneToMany(mappedBy = "concierto", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ConciertoEntity> conciertos = new ArrayList<>();
}
