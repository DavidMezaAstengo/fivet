package cl.ucn.disc.isof.fivet.domain.model;

import com.durrutia.ebean.BaseModel;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * Clase que representa a un control de la veterinaria.
 *
 * @author David Meza A
 * @version 20161102
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Control extends BaseModel{

    /**
     * Fecha
     */
    @Getter
    @Column
    private Date fecha;

    /**
     * Proximo Control
     */
    @Getter
    @Column
    private Date proxControl;

    /**
     * Temperatura
     */
    @Getter
    @Column
    private Double temperatura;

    /**
     * Peso
     */
    @Getter
    @Column
    private Double peso;

    /**
     * Altura
     */
    @Getter
    @Column
    private Double altura;

    /**
     * Diagnostico
     */
    @Getter
    @Column
    private String diagnostico;

    /**
     * Nota
     */
    @Getter
    @Column
    private String nota;

    /**
     * ID
     */
    @Getter
    @Column(nullable = false)
    private Integer numeroid;

    /**
     * veterinario
     */

    @Getter
    @Column(nullable = false)
    @ManyToOne
    private Persona veterinario;

}
