package cl.ucn.disc.isof.fivet.domain.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Clase que representa a un examen.
 *
 * @author David Meza A
 * @version 20161102
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Examen {

    /**
     * Numero identificador del examen
     */
    @Getter
    @Column(nullable = false)
    private Integer numeroid;

    /**
     * Numero identificador de el paciente
     */

    @Getter
    @Column(nullable = false)
    private Integer numero;

    /**
     * Nombre del paciente
     */
    @Getter
    @Setter
    @Column
    private String nombre;

    /**
     * resultado del examen
     */
    @Getter
    @Setter
    @Column
    private String resultado;

    /**
     * medicamentos asociados al examen
     */
    @Getter
    @Setter
    @Column
    private String medicamentos;

    /**
     * Fecha
     */
    @Getter
    @Setter
    @Column
    private Date fecha;


}
