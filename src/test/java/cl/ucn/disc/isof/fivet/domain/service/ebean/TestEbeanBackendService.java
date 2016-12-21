package cl.ucn.disc.isof.fivet.domain.service.ebean;

import cl.ucn.disc.isof.fivet.domain.model.Control;
import cl.ucn.disc.isof.fivet.domain.model.Examen;
import cl.ucn.disc.isof.fivet.domain.model.Paciente;
import cl.ucn.disc.isof.fivet.domain.model.Persona;
import cl.ucn.disc.isof.fivet.domain.service.BackendService;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.rules.Timeout;
import org.junit.runners.MethodSorters;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase de testing del {@link BackendService}.
 */
@Slf4j
@FixMethodOrder(MethodSorters.DEFAULT)
public class TestEbeanBackendService {

    /**
     * Todos los test deben terminar antes de 60 segundos.
     */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(60);

    /**
     * Configuracion de la base de datos:  h2, hsql, sqlite
     * WARN: hsql no soporta ENCRYPT
     */
    private static final String DB = "h2";

    /**
     * Backend
     */
    private BackendService backendService;

    /**
     * Cronometro
     */
    private Stopwatch stopWatch;

    /**
     * Antes de cada test
     */
    @Before
    public void beforeTest() {

        stopWatch = Stopwatch.createStarted();
        log.debug("Initializing Test Suite with database: {}", DB);

        backendService = new EbeanBackendService(DB);
        backendService.initialize();
    }

    /**
     * Despues del test
     */
    @After
    public void afterTest() {

        log.debug("Test Suite done. Shutting down the database ..");
        backendService.shutdown();

        log.debug("Test finished in {}", stopWatch.toString());
    }




    /**
     * Test de la persona
     */
    @Test
    public void testPersona() {

        final String rut = "19.182.627-2";
        final String nombre = "David Meza";
        final String nombre2 = "nombre2";
        final Persona.Tipo tipo = Persona.Tipo.CLIENTE;
        final String password = "qwe1";

        final String direccion = "qwe 123";
        final String email = "qwe1@qwe.com";
        final String telMovil = "123 123";
        final String telFijo = "123 123 123";


        // Insert into backend
        {
            final Persona persona = Persona.builder()
                    .nombre(nombre)
                    .rut(rut)
                    .password(password)
                    .direccion(direccion)
                    .email(email)
                    .telMovil(telMovil)
                    .telFijo(telFijo)
                    .tipo(tipo)
                    .build();

            persona.insert();

            log.debug("Persona to insert: {}", persona);
            Assert.assertNotNull("Objeto sin id", persona.getId());
        }

        // Get from backend v1
        //buscando por rut
        {
            final Persona persona = backendService.getPersona(rut);
            log.debug("Persona found: {}", persona);
            Assert.assertNotNull("Can't find Persona", persona);
            Assert.assertNotNull("Objeto sin id", persona.getId());
            Assert.assertEquals("Nombre distintos!", nombre, persona.getNombre());
            Assert.assertNotNull("Pacientes null", persona.getPacientes());
            Assert.assertTrue("Pacientes != 0", persona.getPacientes().size() == 0);


        }

        // Get from backend v2
        //buscando por email
        {
            final Persona persona = backendService.getPersona(email);
            log.debug("Persona found: {}", persona);
            Assert.assertNotNull("Can't find Persona", persona);
            Assert.assertEquals("Nombres distintos!", rut, persona.getRut());

            // Update nombre
            persona.setNombre(nombre2);
            persona.update();
        }

        // Get from backend v3
        {
            final Persona persona = backendService.getPersona(rut);
            log.debug("Persona found: {}", persona);
            Assert.assertNotNull("Can't find Persona", persona);
            Assert.assertEquals("Nombres distintos!", nombre2, persona.getNombre());
        }

        //hacer veterinario
    }

    /**
     * Test del Paciente
     */
    @Test
    public void testPaciente(){
        final Integer numero = 1;
        final String nombre = "nombre";
        final String nombre2 = "nombre2";
        final String nombre3 = "nombre3";
        final Date fechaNacimiento =  new java.util.Date();
        final String color = "color";
        final String especie = "especie";
        Paciente.Sexo sexo = Paciente.Sexo.MACHO;
        final List <Control> controles = new ArrayList<Control>();

        // Insert into backend
        {
            final Paciente paciente = Paciente.builder()
                    .numero(numero)
                    .nombre(nombre)
                    .fechaNacimiento(fechaNacimiento)
                    .color(color)
                    .especie(especie)
                    .sexo(sexo)
                    .controles(controles)
                    .build();

            paciente.insert();


            log.debug("{Paciente to insert: {}", paciente);
            Assert.assertNotNull("Objeto sin id", paciente.getId());
        }

        // Get from backend v1
        {
            final  Paciente paciente = backendService.getPaciente(numero);
            log.debug("Paciente found: {}", paciente);
            Assert.assertNotNull("Can't find Paciente", paciente);
            Assert.assertNotNull("Objeto sin id", paciente.getId());
            Assert.assertEquals("Numero distintos!", numero, paciente.getNumero());
            Assert.assertEquals("Nombre distintos!", nombre, paciente.getNombre());
            Assert.assertEquals("Fecha de nacimiento distintas!", fechaNacimiento,
                    paciente.getFechaNacimiento());
            Assert.assertEquals("Color distintos!", color, paciente.getColor());
            Assert.assertEquals("Especie distintos!", especie, paciente.getEspecie());
            Assert.assertEquals("Sexo distintos!", sexo, paciente.getSexo());
            Assert.assertNotNull("Controles null", paciente.getControles());
            Assert.assertTrue("Controles != 0", paciente.getControles().size() == 0);

            // Update nombre
            paciente.setNombre(nombre2);
            paciente.update();
        }

        // Get from backend v2
        //test de buscar y update
        {
            final  Paciente paciente = backendService.getPaciente(numero);
            log.debug("Paciente found: {}", paciente);
            Assert.assertNotNull("Can't find Paciente", paciente);
            Assert.assertEquals("Nombres distintos!", nombre2, paciente.getNombre());
        }

        //nuevos pacientes
        {
            final Paciente paciente = Paciente.builder()
                    .numero(2)
                    .nombre(nombre3)
                    .build();

            paciente.insert();

            log.debug("{Paciente to insert: {}", paciente);
            Assert.assertNotNull("Objeto sin id", paciente.getId());

            final Paciente paciente2 = Paciente.builder()
                    .numero(3)
                    .nombre(nombre3)
                    .build();

            paciente2.insert();

            log.debug("{Paciente to insert: {}", paciente);
            Assert.assertNotNull("Objeto sin id", paciente.getId());
        }

        // Get from backend v3
        //test de listas todos los pacientes
        {
            List<Paciente> pacientes = backendService.getPacientes();
            log.debug("Lista Pacientes found: {}", pacientes);
            Assert.assertTrue(pacientes != null);
            Assert.assertTrue(pacientes.size() == 3);
        }

        // Get from backend v4
        // test de listas todos los pacientes con nombre3
        {
            List<Paciente> pacientes = backendService.getPacientesPorNombre(nombre3);
            log.debug("Lista Pacientes found: {}", pacientes);
            Assert.assertTrue(pacientes != null);
            Assert.assertTrue(pacientes.size() == 2);
        }
    }

    /**
     * Test del control
     */
    @Test
    public void testControl(){

        final String rut = "19.182.627-2";
        final String nombre = "David Meza";
        final Persona.Tipo tipo = Persona.Tipo.VETERINARIO;
        final String password = "qwe1";

        final Integer id = 1;
        final java.util.Date fecha = new java.util.Date();
        final double altura = 1.0;
        final double temperatura = 17.0;
        final double peso = 6.0;
        final String diagnostico = "diagnostico";
        final String nota = "nota";


        // Insert into backend
        {
        final Persona persona = Persona.builder()
                .rut(rut)
                .nombre(nombre)
                .password(password)
                .tipo(tipo)
                .build();

        persona.insert();

        final Control control = Control.builder()
                    .numeroid(id)
                    .veterinario(persona)
                    .fecha(fecha)
                    .altura(altura)
                    .temperatura(temperatura)
                    .peso(peso)
                    .diagnostico(diagnostico)
                    .nota(nota)
                    .build();

        control.insert();
        log.debug("Control to insert: {}", control);
        Assert.assertNotNull("Objeto sin id", control.getId());
        }

        // Get from backend v1
        {
            final Control control = this.backendService.getControl(id);
            log.debug("Control found: {}", control);

            Assert.assertNotNull("Can't find Control", control);
            Assert.assertNotNull("Objeto sin id", control.getId());
            Assert.assertEquals("ID distintos!", id, control.getNumeroid());
            Assert.assertEquals("Fecha distintas!", fecha, control.getFecha());
            Assert.assertEquals("Altura distintas!", String.valueOf(altura), String.valueOf(control.getAltura()));
            Assert.assertEquals("Diagnostico distintos!", diagnostico, control.getDiagnostico());
            Assert.assertEquals("Nota distintos!", nota, control.getNota());
            Assert.assertEquals("Peso distintos!", String.valueOf(peso), String.valueOf((control.getPeso())));
            Assert.assertEquals("Temperatura distintos!", String.valueOf(temperatura), String.valueOf(control.getTemperatura()));
            Assert.assertEquals("Tipo Persona Distintos!", control.getVeterinario().getTipo(),tipo);

        }
    }
    /**
     * Test de agregar control a paciente
     */
    @Test
    public void testAgregarControl(){

        final String rut = "19.182.627-3";
        final String nombre = "David Meza";
        final Persona.Tipo tipo = Persona.Tipo.VETERINARIO;
        final String password = "qwe1";

        final Integer numero = 2;
        final List <Control> controles = new ArrayList<Control>();

        final Integer id = 2;

        // Insert into backend
        {
            final Persona persona = Persona.builder()
                    .rut(rut)
                    .nombre(nombre)
                    .password(password)
                    .tipo(tipo)
                    .build();

            persona.insert();
            log.debug("Persona to insert: {}", persona);
            Assert.assertNotNull("Objeto sin id", persona.getId());

            final Paciente paciente = Paciente.builder()
                    .numero(numero)
                    .controles(controles)
                    .build();

            paciente.insert();
            log.debug("Paciente to insert: {}", paciente);
            Assert.assertNotNull("Objeto sin id", paciente.getId());

            final Control control = Control.builder()
                    .numeroid(id)
                    .veterinario(persona)
                    .build();

            control.insert();
            log.debug("Control to insert: {}", control);
            Assert.assertNotNull("Objeto sin id", control.getId());

            this.backendService.agregarControl(control,numero);

        }

        // Get from backend v1
        {
            List<Control> controles1 = backendService.getPaciente(numero).getControles();
            Assert.assertNotNull("Can't find controles", controles1);
            Assert.assertTrue("Cantidad Controles Distintas!",controles1.size() == 1);
            Assert.assertEquals("ID distintos!", id, controles1.get(0).getNumeroid());

        }

        {

            List<Control> controles1 = backendService.getControlesVeterinario(rut);
            Assert.assertNotNull("Can't find controles", controles1);
            Assert.assertTrue("Cantidad Controles Distintas!",controles1.size() == 1);

        }
    }

    @Test
    public void testExamen() {

        final Integer numero = 4;
        final java.util.Date fecha = new java.util.Date();
        final String nombre = "examen";
        final String medicamento = "medicamentos";
        final String resultado = "resultado";
        // Insert into backend
        {
            final Paciente paciente = Paciente.builder()
                    .numero(numero)
                    .build();

            paciente.insert();
            log.debug("{Paciente to insert: {}", paciente);
            Assert.assertNotNull("Objeto sin id", paciente.getId());

            for (Integer i =0; i<10;i++ ) {
                final Examen examen = Examen.builder()
                        .fecha(fecha)
                        .nombre(nombre)
                        .numero(numero)
                        .numeroid(i)
                        .medicamento(medicamento)
                        .resultado(resultado)
                        .build();

                examen.insert();
                log.debug("{Examen to insert: {}", examen);
                Assert.assertNotNull("Objeto sin id", examen.getId());
            }
        }

        // Get from backend v1
        {
            final List <Examen> examenes = this.backendService.getExamenesPaciente(numero);
            log.debug("Examenes found: {}", examenes);
            Assert.assertNotNull("Can't find Examenes", examenes);
            Assert.assertTrue("Pacientes != 10", examenes.size() == 10);
        }
    }
    /*
    @Test
    public void testinitialize(){
        AssertFalse(this.backendService.)
        this.backendService.initialize();
    }
    */
}
