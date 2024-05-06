package co.edu.javeriana.as.personapp.terminal.menu;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Profesion;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.domain.StudyId;
import co.edu.javeriana.as.personapp.terminal.adapter.EstudioInputAdapterCli;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EstudioMenu {

    private static final int OPCION_REGRESAR_MODULOS = 0;
    private static final int PERSISTENCIA_MARIADB = 1;
    private static final int PERSISTENCIA_MONGODB = 2;

    private static final int OPCION_REGRESAR_MOTOR_PERSISTENCIA = 0;
    private static final int OPCION_VER_TODO = 1;
    private static final int OPCION_CREAR_ESTUDIO = 2;
    private static final int OPCION_ELIMINAR_ESTUDIO = 3;
    private static final int OPCION_EDITAR_ESTUDIO = 4;

    public void iniciarMenu(EstudioInputAdapterCli estudioInputAdapterCli, Scanner keyboard) throws NoExistException {
        boolean isValid = false;
        do {
            try {
                mostrarMenuMotorPersistencia();
                int opcion = leerOpcion(keyboard);
                switch (opcion) {
                    case OPCION_REGRESAR_MODULOS:
                        isValid = true;
                        break;
                    case PERSISTENCIA_MARIADB:
                        estudioInputAdapterCli.setStudyOutputPortInjection("MARIA");
                        menuOpciones(estudioInputAdapterCli, keyboard);
                        break;
                    case PERSISTENCIA_MONGODB:
                        estudioInputAdapterCli.setStudyOutputPortInjection("MONGO");
                        menuOpciones(estudioInputAdapterCli, keyboard);
                        break;
                    default:
                        log.warn("La opción elegida no es válida.");
                }
            } catch (InvalidOptionException e) {
                log.warn(e.getMessage());
            }
        } while (!isValid);
    }

    private void menuOpciones(EstudioInputAdapterCli estudioInputAdapterCli, Scanner keyboard) throws NoExistException {
        boolean isValid = false;
        do {
            try {
                mostrarMenuOpciones();
                int opcion = leerOpcion(keyboard);
                switch (opcion) {
                    case OPCION_REGRESAR_MODULOS:
                        isValid = true;
                        break;
                    case OPCION_VER_TODO:
                        estudioInputAdapterCli.historial();
                        break;
                    case OPCION_CREAR_ESTUDIO:
                        crearEstudio(estudioInputAdapterCli, keyboard);
                        break;
                    case OPCION_ELIMINAR_ESTUDIO:
                        eliminarEstudio(estudioInputAdapterCli, keyboard);
                        break;
                    case OPCION_EDITAR_ESTUDIO:
                        editarEstudio(estudioInputAdapterCli, keyboard);
                        break;
                    default:
                        log.warn("La opción elegida no es válida.");
                }
            } catch (InputMismatchException e) {
                log.warn("Error: " + e.getMessage());
                keyboard.nextLine(); // Clear buffer
            }
        } while (!isValid);
    }

    private void mostrarMenuOpciones() {
        System.out.println("----------------------");
        System.out.println(OPCION_VER_TODO + " para ver todos los estudios");
        System.out.println(OPCION_CREAR_ESTUDIO + " para crear un nuevo estudio");
        System.out.println(OPCION_ELIMINAR_ESTUDIO + " para eliminar un estudio");
        System.out.println(OPCION_EDITAR_ESTUDIO + " para editar un estudio");
        System.out.println(OPCION_REGRESAR_MOTOR_PERSISTENCIA + " para regresar");
    }

    private void mostrarMenuMotorPersistencia() {
        System.out.println("----------------------");
        System.out.println(PERSISTENCIA_MARIADB + " para MariaDB");
        System.out.println(PERSISTENCIA_MONGODB + " para MongoDB");
        System.out.println(OPCION_REGRESAR_MODULOS + " para regresar");
    }

    private int leerOpcion(Scanner keyboard) {
        System.out.print("Ingrese una opción: ");
        try {
            return keyboard.nextInt();
        } catch (InputMismatchException e) {
            log.warn("Solo se permiten números.");
            keyboard.next(); // Clear buffer
            return -1;
        }
    }

    private void crearEstudio(EstudioInputAdapterCli estudioInputAdapterCli, Scanner keyboard) {
        log.info("Creación de un nuevo estudio");
        keyboard.nextLine(); // Limpiar buffer
        System.out.print("Ingrese el ID de la persona: ");
        int idPersona = keyboard.nextInt();
        System.out.print("Ingrese el ID de la profesión: ");
        int idProfesion = keyboard.nextInt();
        keyboard.nextLine(); // Limpiar buffer
        System.out.print("Ingrese el nombre de la universidad: ");
        String univer = keyboard.nextLine();
        System.out.print("Ingrese la fecha de graduación (YYYY-MM-DD): ");
        String fecha = keyboard.nextLine();

        // Crear objetos de dominio basados en los ID proporcionados
        Person person = new Person();
        person.setIdentification(idPersona);
        Profesion profession = new Profesion();
        profession.setId(idProfesion);

        Study study = new Study();
        study.setPerson(person);
        study.setProfession(profession);
        study.setUniversityName(univer);
        study.setGraduationDate(LocalDate.parse(fecha));

        if (estudioInputAdapterCli.create(study)) {
            System.out.println("¡Estudio creado exitosamente!");
        } else {
            System.out.println("Error al crear el estudio.");
        }
    }

    private void eliminarEstudio(EstudioInputAdapterCli estudioInputAdapterCli, Scanner keyboard) {
        log.info("Eliminación de un estudio");
        System.out.print("Ingrese el ID de la persona: ");
        int idPersona = keyboard.nextInt();
        System.out.print("Ingrese el ID de la profesión: ");
        int idProfesion = keyboard.nextInt();

        StudyId studyId = new StudyId(idPersona, idProfesion);

        try {
            if (estudioInputAdapterCli.drop(studyId)) {
                System.out.println("¡Estudio eliminado exitosamente!");
            } else {
                System.out.println("Error al eliminar el estudio.");
            }
        } catch (NoExistException e) {
            System.out.println(e.getMessage());
        }
    }

    private void editarEstudio(EstudioInputAdapterCli estudioInputAdapterCli, Scanner keyboard)
            throws NoExistException {
        log.info("Edición de un estudio");
        System.out.print("Ingrese el ID de la persona para el estudio a editar: ");
        int idPersona = keyboard.nextInt();
        System.out.print("Ingrese el ID de la profesión para el estudio a editar: ");
        int idProfesion = keyboard.nextInt();
        keyboard.nextLine(); // Limpiar buffer
        System.out.print("Ingrese el nuevo nombre de la universidad: ");
        String univer = keyboard.nextLine();
        System.out.print("Ingrese la nueva fecha de graduación (YYYY-MM-DD): ");
        String fecha = keyboard.nextLine();

        StudyId studyId = new StudyId(idPersona, idProfesion);
        Study study = new Study();
        study.setPerson(new Person(idPersona, fecha, fecha, null));
        study.setProfession(new Profesion(idProfesion, fecha));
        study.setUniversityName(univer);
        study.setGraduationDate(LocalDate.parse(fecha));

        if (estudioInputAdapterCli.edit(studyId, study)) {
            System.out.println("¡Estudio editado exitosamente!");
        } else {
            System.out.println("Error al editar el estudio.");
        }
    }
}
