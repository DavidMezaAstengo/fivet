package cl.ucn.disc.isof.fivet.domain.model;

import com.avaje.ebean.annotation.EnumValue;
import com.durrutia.ebean.BaseModel;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Clase que representa a un Paciente de la veterinaria.
 *
 * @author Diego P. Urrutia Astorga
 * @version 20161102
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Paciente extends BaseModel {

    /**
     * Numero de la ficha
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
     * Fecha de nacimiento
     */
    @Getter
    @Setter
    @Column
    private Date fechaNacimiento;

    /**
     * Raza
     */
    @Getter
    @Setter
    @Column
    private String raza;

    /**
     * Sexo
     */
    @Column
    private Sexo sexo;

    /**
     * Color
     */
    @Getter
    @Setter
    @Column
    private String color;

    /**
     * Especie
     */
    @Getter
    @Column
    private String especie;

    /**
     * controles
     */
    @Getter
    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("ID")
    private List<Control> controles;
    public enum Sexo {
        @EnumValue("Macho")
        MACHO,

        @EnumValue("Hembra")
        HEMBRA,

        @EnumValue("Indeterminado")
        INDETERMINADO,
    }

}
