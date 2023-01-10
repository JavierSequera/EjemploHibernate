import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;
import java.util.Scanner;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class principal {

    private static Scanner s = new Scanner(System.in);
    private static  SessionFactory sessionFactory;
    public static void main(String[] args) {

        LogManager.getLogManager().reset();
        Logger globalLogger = Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
        globalLogger.setLevel(java.util.logging.Level.OFF);

        int id, numAciertos;
        String nombre;
        try{
            setUp();
            int opcion = menu();

            switch (opcion){
                case 1:
                    System.out.println("Introduzca el nombre de la persona");
                    nombre = s.next();
                    guardar(nombre, 0);
                    break;
                case 2:
                    listar();
                    break;
                case 3:
                    System.out.println("Introduzca el id de la persona para listarla");
                    id = s.nextInt();
                    listarPorId(id);
                    break;
                case 4:
                    System.out.println("Introduzca el id de la persona que quieres actualizar");
                    id = s.nextInt();
                    System.out.println("Introduzca el nombre de la persona para actualizarla");
                    nombre = s.next();
                    System.out.println("Introduzca el número de aciertos de la persona para actualizarla");
                    numAciertos = s.nextInt();
                    actualizar(id, nombre, numAciertos);
                    break;
                case 5:
                    System.out.println("Introduzca el id de la persona que quieres eliminar");
                    id = s.nextInt();
                    borrar(id);
                    break;
                default:
                    System.out.println("Error al introducir la opción");
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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

    //Método que recibe un string nombre e int numeroAciertos e inserta los datos en la BBDD
    private static void guardar(String nombre, int numeroAciertos) {
        PersonasEntidad persona = new PersonasEntidad(nombre, numeroAciertos);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        int id = (int) session.save(persona);
        transaction.commit();
        System.out.println(id);
        sessionFactory.close();
    }

    //Método que recibe un int id y realiza la búsqueda en la BBDD y muestra dicha persona con ese id
    private static void listarPorId(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        PersonasEntidad persona = session.load(PersonasEntidad.class, id);
        System.out.println(persona.getNombre()+", "+persona.getIdJugador()+", "+persona.getNumAciertos());
        transaction.commit();
        sessionFactory.close();
    }

    //Método que realiza la búsqueda y muestra por pantalla todas las personas
    private static void listar() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<PersonasEntidad> personas = (List<PersonasEntidad>) session.createSQLQuery("SELECT * FROM Jugadores").addEntity(PersonasEntidad.class).list();
        for (int i = 0; i < personas.size(); i++) {
            System.out.println(personas.get(i).getNombre()+", "+personas.get(i).getIdJugador()+", "+personas.get(i).getNumAciertos());
        }
        transaction.commit();
        sessionFactory.close();
    }


    //Método que actualiza una persona de la base de datos. Recibe un int id, numAciertos y string nombre
    private static void actualizar(int id, String nombre, int numAciertos) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try{
            PersonasEntidad persona = session.load(PersonasEntidad.class, id);
            persona.setNombre(nombre);
            persona.setNumAciertos(numAciertos);
            session.update(persona);
            System.out.println("Jugador actualizado: "+persona.getIdJugador()+", "+persona.getNombre()+", "+persona.getNumAciertos());
            transaction.commit();
        }catch (Exception ex){
            transaction.rollback();
            System.out.println(ex);
        }
        sessionFactory.close();
    }

    //Método que recibe un entero id y elimina de la base de datos la persona que corresponde al id recibido.
    private static void borrar(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try{
            PersonasEntidad persona = session.load(PersonasEntidad.class, id);
            session.delete(persona);
            System.out.println("Jugador eliminado: "+persona.getNombre()+", "+persona.getIdJugador()+", "+persona.getNumAciertos());
            transaction.commit();
        }catch (Exception ex){
            transaction.rollback();
            System.out.println(ex);
        }
        sessionFactory.close();
    }



    //Método que muestra por pantalla el menu de opciones
    private static int menu(){
        System.out.println("Elija la opción: ");
        System.out.println("Insertar");
        System.out.println("Listado completo de jugadores");
        System.out.println("Listar por id");
        System.out.println("Actualizar");
        System.out.println("Borrar");
        int opcion = s.nextInt();
        return opcion;
    }
}
