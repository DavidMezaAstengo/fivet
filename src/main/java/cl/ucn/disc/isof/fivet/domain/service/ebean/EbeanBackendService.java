package cl.ucn.disc.isof.fivet.domain.service.ebean;

import cl.ucn.disc.isof.fivet.domain.model.Control;
import cl.ucn.disc.isof.fivet.domain.model.Examen;
import cl.ucn.disc.isof.fivet.domain.model.Paciente;
import cl.ucn.disc.isof.fivet.domain.model.Persona;
import cl.ucn.disc.isof.fivet.domain.service.BackendService;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.Expr;
import com.avaje.ebean.config.EncryptKey;
import com.avaje.ebean.config.EncryptKeyManager;
import com.avaje.ebean.config.ServerConfig;
import com.durrutia.ebean.BaseModel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class EbeanBackendService implements BackendService {

    /**
     * EBean server
     */
    private final EbeanServer ebeanServer;
    /*
    @Getter
    private Boolean Initialized = false;
    */

    /**
     *
     */
    public EbeanBackendService(final String database) {

        log.debug("Loading EbeanBackend in database: {}", database);

        /**
         * Configuration
         */
        ServerConfig config = new ServerConfig();
        config.setName(database);
        config.setDefaultServer(true);
        config.loadFromProperties();

        // Don't try this at home
        //config.setAutoCommitMode(false);

        //config.addPackage("package.de.la.clase.a.agregar.en.el.modelo");
        config.addClass(BaseModel.class);

        config.addClass(Examen.class);

        config.addClass(Control.class);

        config.addClass(Persona.class);
        config.addClass(Persona.Tipo.class);

        config.addClass(Paciente.class);
        config.addClass(Paciente.Sexo.class);


        // http://ebean-orm.github.io/docs/query/autotune
        config.getAutoTuneConfig().setProfiling(false);
        config.getAutoTuneConfig().setQueryTuning(false);

        config.setEncryptKeyManager(new EncryptKeyManager() {

            @Override
            public void initialise() {
                log.debug("Initializing EncryptKey ..");
            }

            @Override
            public EncryptKey getEncryptKey(final String tableName, final String columnName) {

                log.debug("gettingEncryptKey for {} in {}.", columnName, tableName);

                // Return the encrypt key
                return () -> tableName + columnName;
            }
        });

        this.ebeanServer = EbeanServerFactory.create(config);

        log.debug("EBeanServer ready to go.");

    }


    /**
     * Obtener una persona buscando en backend por rut.
     *
     * @param rut
     * @return the Persona
     */
    @Override
    public Persona getPersona(String rut) {
        return this.ebeanServer.find(Persona.class)
                .where()
                .or(Expr.eq("rut", rut),
                        Expr.eq("email", rut))
                .findUnique();
    }

    /**
     *Obtener una lista con todos los pacientes.
     *
     * @return lista de pacientes
     */
    @Override
    public List<Paciente> getPacientes() {
        return this.ebeanServer.find(Paciente.class).findList();

    }

    /**
     * Obtener un paciente buscando en backend por su numero de paciente.
     *
     * @param numeroPaciente
     * @return El Paciente
     */
    @Override
    public Paciente getPaciente(Integer numeroPaciente) {
        return this.ebeanServer.find(Paciente.class)
                .where()
                .eq("numero",numeroPaciente)
                .findUnique();
    }

    /**
     * Obtiener un control desde el backend dado su ID.
     *
     * @param id
     * @return El Paciente
     */
    @Override
    public Control getControl(Integer id) {
        return this.ebeanServer.find(Control.class)
                .where()
                .eq("ID",id)
                .findUnique();
    }
    /**
     * Obtener una lista de los controles de un veterinario buscando por su rut.
     *
     * @param rutVeterinario
     * @return lista de controles
     */
    @Override
    public List<Control> getControlesVeterinario(String rutVeterinario) {
        return this.ebeanServer.find(Control.class)
                .where()
                .eq("veterinario.rut",rutVeterinario)
                .findList();

    }

    /**
     * Obtener una lista de pacientes por buscando nombre.
     *
     * @param nombre
     * @return lista de pacientes
     */
    @Override
    public List<Paciente> getPacientesPorNombre(String nombre) {
        return this.ebeanServer.find(Paciente.class)
                .where()
                .contains("nombre",nombre)
                .findList();
    }

    /**
     * Agrega un control a un paciente.
     *
     * @param control
     * @param numeroPaciente
     */
    @Override
    public void agregarControl(Control control, Integer numeroPaciente) {
        Paciente paciente = this.getPaciente(numeroPaciente);
        paciente.add(control);
        paciente.update();
    }
     /**
     * Obtiener todos los examenes de un paciente.
     *
     * @param numero
     * @return lista de pacientes
     */
   @Override
   public List<Examen> getExamenesPaciente(Integer numero){
        return this.ebeanServer.find(Examen.class)
                .where()
                .eq("numero",numero)
                .findList();
    }

    /**
     * Inicializa la base de datos
     */
    @Override
    public void initialize() {
        log.info("Initializing Ebean ..");
       /*
        if (Initialized) {
            log.info("ya esta inicializada la base de datos");
        } else {
            final Persona persona = Persona.builder()
                    .nombre("admin")
                    .rut("admin")
                    .email("admin@admin.com")
                    .password("admin")
                    .tipo(Persona.Tipo.VETERINARIO)
                    .build();
            persona.insert();
            this.Initialized = true;
        }
        */
    }

    /**
     * Cierra la conexion a la BD
     */
    @Override
    public void shutdown() {
        log.debug("Shutting down Ebean ..");

        // TODO: Verificar si es necesario des-registrar el driver
        this.ebeanServer.shutdown(true, false);
    }
}
