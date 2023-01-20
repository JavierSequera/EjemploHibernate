package Ejercicio3_1;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Date;
import java.util.Scanner;

public class principal {

    private static Scanner s = new Scanner(System.in);
    private static SessionFactory sessionFactory;

    public static void main(String[] args) throws Exception {

        setUp();
        menu();
        menuInsertar();

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
        System.out.println("3. Mostrar 1 fila");
        System.out.println("Actualizar");
        System.out.println("Borrar");
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
                break;
            case 3:
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

    //Método genérico que inserta un objeto en la BBDD
    private static void insertarObjeto(Object objeto){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(objeto);
        transaction.commit();
        sessionFactory.close();
    }
}
