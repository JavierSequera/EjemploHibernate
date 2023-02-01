package Ejercicio3_1;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class principal {

    private static Scanner s = new Scanner(System.in);
    private static SessionFactory sessionFactory;

    public static void main(String[] args) throws Exception {
        LogManager.getLogManager().reset();
        Logger globalLogger = Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
        globalLogger.setLevel(java.util.logging.Level.OFF);
        setUp();
        int opcion = menu();
        switch(opcion){
            case 1:
                menuInsertar();
                break;
            case 2:
                MenuListar();
                break;
            case 3:
                menuActualizar();
                break;
            case 4:
                menuBorrar();
                break;
            default:
                System.out.println("Opción no válida");
                break;
        }
        sessionFactory.close();
    }

    //Método que inicia la conexión con la base de datos
    protected static void setUp() throws Exception {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // por defecto: hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    private static int menu(){
        System.out.println("Elija la opción que desee:");
        System.out.println("1. Insertar");
        System.out.println("2. Mostrar lista completa");
        System.out.println("3. Actualizar");
        System.out.println("4. Borrar");
        int opcion = s.nextInt();
        return opcion;
    }

    private static void menuInsertar() {
        System.out.println("Escriba el nombre de la tabla en la que se desea insertar");
        System.out.println("1.Alumnado, 2.Profesorado, 3.Matrícula");
        int opcion = s.nextInt();
        switch (opcion){
            case 1:
                insertarAlumnado();
                break;
            case 2:
                insertarProfesorado();
                break;
            case 3:
                insertarMatrícula();
                break;
            default:
                System.out.println("Opción no válida");
                break;
        }
    }

    //Método que pide al usuario los datos del alumno a insertar y llama al método que inserta objetos en la BBDD pasándole el objeto alumno
    //con los datos introducidos por el usuario.
    private static void insertarAlumnado(){
        String nombre, apellido;
        int antiguedad;
        Date fechaNac;
        String fechaCadena;
        String[] fechaSeparada;

        System.out.println("Escriba el nombre del alumno");
        nombre = s.next();
        System.out.println("Escriba el apellido del alumno");
        apellido = s.next();
        System.out.println("Escriba la antiguedad del alumno en el centro. (años)");
        antiguedad = s.nextInt();
        System.out.println("Escriba la fecha de nacimiento del alumno con el siguiente formato: AAAA/MM/DD");
        fechaCadena = s.next();

        fechaSeparada = fechaCadena.split("/");
        fechaNac = new Date(Integer.parseInt(fechaSeparada[0]) - 1900, Integer.parseInt(fechaSeparada[1]) - 1, Integer.parseInt(fechaSeparada[2]));

        AlumnadoEntidad alumno = new AlumnadoEntidad(nombre, apellido, fechaNac, antiguedad);
        insertarObjeto(alumno);
    }

    private static void insertarProfesorado(){
        String nombre, apellido;
        int antiguedad;
        Date fechaNac;
        String fechaCadena;
        String[] fechaSeparada;

        System.out.println("Escriba el nombre del profesor");
        nombre = s.next();
        System.out.println("Escriba el apellido del profesor");
        apellido = s.next();
        System.out.println("Escriba la antiguedad del profesor en el centro. (años)");
        antiguedad = s.nextInt();
        System.out.println("Escriba la fecha de nacimiento del profesor con el siguiente formato: AAAA/MM/DD");
        fechaCadena = s.next();

        fechaSeparada = fechaCadena.split("/");
        fechaNac = new Date(Integer.parseInt(fechaSeparada[0]) - 1900, Integer.parseInt(fechaSeparada[1]) - 1, Integer.parseInt(fechaSeparada[2]));

        ProfesorEntidad profesor = new ProfesorEntidad(nombre, apellido, fechaNac, antiguedad);
        insertarObjeto(profesor);
    }

    private static void insertarMatrícula(){
        int idAlumno, idProfesor, curso;
        String asignatura;

        System.out.println("Introduzca el id del alumno de la matrícula");
        idAlumno = s.nextInt();
        System.out.println("Introduzca el id del profesor de la matrícula");
        idProfesor = s.nextInt();
        System.out.println("Introduzca la asignatura de la matrícula");
        asignatura = s.next();
        System.out.println("Introduzca el curso de la matrícula (año)");
        curso = s.nextInt();

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        AlumnadoEntidad alumno = session.load(AlumnadoEntidad.class, idAlumno);
        ProfesorEntidad profesor = session.load(ProfesorEntidad.class, idProfesor);
        transaction.commit();

        MatriculaEntidad matricula = new MatriculaEntidad(alumno, profesor, asignatura, curso);
        insertarObjeto(matricula);
    }

    //Método genérico que inserta un objeto en la BBDD
    private static void insertarObjeto(Object objeto){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(objeto);
        transaction.commit();
        session.close();
    }

    private static void MenuListar(){
        System.out.println("Escriba el nombre de la tabla a la que desea listar");
        System.out.println("1.Alumnado, 2.Profesorado, 3.Matrícula");
        int opcion = s.nextInt();
        switch (opcion){
            case 1:
                ListarAlumnos();
                break;
            case 2:
                ListarProfesores();
                break;
            case 3:
                ListarMatriculas();
                break;
            default:
                System.out.println("Opción no válida");
                break;
        }
    }

    private static void ListarAlumnos(){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<AlumnadoEntidad> personas = (List<AlumnadoEntidad>) session.createSQLQuery("SELECT * FROM Alumnos").addEntity(AlumnadoEntidad.class).list();
        for (int i = 0; i < personas.size(); i++) {
            System.out.println(personas.get(i).getIdAlumnado()+", "+personas.get(i).getNombre()+", "+personas.get(i).getApellidos()+", "+personas.get(i).getFechaNacimiento()+", "+personas.get(i).getAntiguedad());
        }
        transaction.commit();
        session.close();
    }

    private static void ListarProfesores(){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<ProfesorEntidad> personas = (List<ProfesorEntidad>) session.createSQLQuery("SELECT * FROM Profesores").addEntity(ProfesorEntidad.class).list();
        for (int i = 0; i < personas.size(); i++) {
            System.out.println(personas.get(i).getIdProfesorado()+", "+personas.get(i).getNombre()+", "+personas.get(i).getApellidos()+", "+personas.get(i).getFechaNacimiento()+", "+personas.get(i).getAntiguedad());
        }
        transaction.commit();
        session.close();
    }

    private static void ListarMatriculas(){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<MatriculaEntidad> personas = (List<MatriculaEntidad>) session.createSQLQuery("SELECT * FROM Matrícula").addEntity(MatriculaEntidad.class).list();
        for (int i = 0; i < personas.size(); i++) {
            System.out.println(personas.get(i).getIdMatricula()+", "+personas.get(i).getAlumnado().getNombre()+", "+personas.get(i).getProfesorado().getNombre()+", "+personas.get(i).getCurso()+", "+personas.get(i).getAsignatura());
        }
        transaction.commit();
        session.close();
    }

    private static void menuActualizar(){
        System.out.println("Escriba el nombre de la tabla a la que desea actualizar");
        System.out.println("1.Alumnado, 2.Profesorado, 3.Matrícula");
        int opcion = s.nextInt();
        switch (opcion){
            case 1:
                actualizarAlumno();
                break;
            case 2:
                actualizarProfesor();
                break;
            case 3:
                actualizarMatrícula();
                break;
            default:
                System.out.println("Opción no válida");
                break;
        }
    }

    private static void actualizarAlumno() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Date fechaNac;
        String fechaCadena;
        String[] fechaSeparada;
        try{
            System.out.println("Escriba el id del alumno a actualizar");
            int id = s.nextInt();
            AlumnadoEntidad alumno = session.load(AlumnadoEntidad.class, id);
            System.out.println("Escriba el nombre del alumno a actualizar. Si no quiere cambiar el campo escriba: *");
            String nombre = s.next();
            if(!nombre.equals("*")) {
                alumno.setNombre(nombre);
            }
            System.out.println("Escriba los apellidos del alumno a actualizar. Si no quiere cambiar el campo escriba: *");
            String apellido = s.next();
            if(!apellido.equals("*")){
                alumno.setApellidos(apellido);
            }
            System.out.println("Escriba la antiguedad del alumno a actualizar. Si no quiere cambiar el campo escriba: -1");
            int antiguedad = s.nextInt();
            if(antiguedad != -1) {
                alumno.setAntiguedad(antiguedad);
            }
            System.out.println("Escriba la fecha de nacimiento del alumno a actualizar con el siguiente formato (AAAA/MM/DD). Si no quiere cambiar el campo escriba: *");
            fechaCadena = s.next();
            if(!fechaCadena.equals("*")){
                fechaSeparada = fechaCadena.split("/");
                fechaNac = new Date(Integer.parseInt(fechaSeparada[0]) - 1900, Integer.parseInt(fechaSeparada[1]) - 1, Integer.parseInt(fechaSeparada[2]));
                alumno.setFechaNacimiento(fechaNac);
            }
            session.update(alumno);
            transaction.commit();
            System.out.println("Alumno actualizado: "+alumno.getIdAlumnado()+", "+alumno.getNombre()+", "+alumno.getApellidos()+", "+alumno.getFechaNacimiento()+", "+alumno.getAntiguedad());
        }catch (Exception ex){
            transaction.rollback();
            System.out.println(ex);
        }
        session.close();
    }

    private static void actualizarProfesor() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Date fechaNac;
        String fechaCadena;
        String[] fechaSeparada;
        try{
            System.out.println("Escriba el id del profesor a actualizar");
            int id = s.nextInt();
            ProfesorEntidad profesor = session.load(ProfesorEntidad.class, id);
            System.out.println("Escriba el nombre del profesor a actualizar. Si no quiere cambiar el campo escriba: *");
            String nombre = s.next();
            if(!nombre.equals("*")) {
                profesor.setNombre(nombre);
            }
            System.out.println("Escriba los apellidos del profesor a actualizar. Si no quiere cambiar el campo escriba: *");
            String apellido = s.next();
            if(!apellido.equals("*")){
                profesor.setApellidos(apellido);
            }
            System.out.println("Escriba la antiguedad del profesor a actualizar. Si no quiere cambiar el campo escriba: -1");
            int antiguedad = s.nextInt();
            if(antiguedad != -1) {
                profesor.setAntiguedad(antiguedad);
            }
            System.out.println("Escriba la fecha de nacimiento del profesor a actualizar con el siguiente formato (AAAA/MM/DD). Si no quiere cambiar el campo escriba: *");
            fechaCadena = s.next();
            if(!fechaCadena.equals("*")){
                fechaSeparada = fechaCadena.split("/");
                fechaNac = new Date(Integer.parseInt(fechaSeparada[0]) - 1900, Integer.parseInt(fechaSeparada[1]) - 1, Integer.parseInt(fechaSeparada[2]));
                profesor.setFechaNacimiento(fechaNac);
            }
            session.update(profesor);
            transaction.commit();
            System.out.println("Alumno actualizado: "+profesor.getIdProfesorado()+", "+profesor.getNombre()+", "+profesor.getApellidos()+", "+profesor.getFechaNacimiento()+", "+profesor.getAntiguedad());
        }catch (Exception ex){
            transaction.rollback();
            System.out.println(ex);
        }
        session.close();
    }

    private static void actualizarMatrícula() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try{
            System.out.println("Escriba el id de la matrícula a actualizar");
            int id = s.nextInt();
            MatriculaEntidad matricula = session.load(MatriculaEntidad.class, id);
            System.out.println("Escriba el id del alumno a actualizar. Si no quiere cambiar el campo escriba: -1");
            int idAlumno = s.nextInt();
            if(idAlumno != -1) {
                AlumnadoEntidad alumno = session.load(AlumnadoEntidad.class, idAlumno);
                matricula.setAlumnado(alumno);
            }
            System.out.println("Escriba el id el profesor a actualizar. Si no quiere cambiar el campo escriba: -1");
            int idProfesor = s.nextInt();
            if(idProfesor != -1) {
                ProfesorEntidad profesor = session.load(ProfesorEntidad.class, idProfesor);
                matricula.setProfesorado(profesor);
            }
            System.out.println("Escriba el curso a actualizar. Si no quiere cambiar el campo escriba: -1");
            int curso = s.nextInt();
            if(curso != -1) {
                matricula.setCurso(curso);
            }
            System.out.println("Escriba la asignatura a actualizar. Si no quiere cambiar el campo escriba: *");
            String asignatura = s.next();
            if(!asignatura.equals("*")){
                matricula.setAsignatura(asignatura);
            }
            session.update(matricula);
            transaction.commit();
            System.out.println("Alumno actualizado: "+matricula.getIdMatricula()+", "+matricula.getAlumnado().getNombre()+", "+matricula.getProfesorado().getNombre()+", "+matricula.getAsignatura()+", "+matricula.getCurso());
        }catch (Exception ex){
            transaction.rollback();
            System.out.println(ex);
        }
        session.close();
    }

    private static void menuBorrar(){
        int id;
        System.out.println("Escriba el nombre de la tabla a la que desea borrar");
        System.out.println("1.Alumnado, 2.Profesorado, 3.Matrícula");
        int opcion = s.nextInt();
        System.out.println("Escriba el id para eliminar");
        id = s.nextInt();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            switch (opcion) {
                case 1:
                    AlumnadoEntidad alumno = session.load(AlumnadoEntidad.class, id);
                    session.delete(alumno);
                    break;
                case 2:
                    ProfesorEntidad profesor = session.load(ProfesorEntidad.class, id);
                    session.delete(profesor);
                    break;
                case 3:
                    MatriculaEntidad matricula = session.load(MatriculaEntidad.class, id);
                    session.delete(matricula);
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
            System.out.println("Error al eliminar");
        }

    }


}