package PruebaOnetoOne;

import org.hibernate.Session;
import org.hibernate.SessionBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.logging.LogManager;
import java.util.logging.Logger;


public class main {

    private static SessionFactory sessionFactory;

    public static void main(String[] args) throws Exception {
        LogManager.getLogManager().reset();
        Logger globalLogger = Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
        globalLogger.setLevel(java.util.logging.Level.OFF);
        DireccionEntidad direcsion=new DireccionEntidad(1, "Plaza del ayuntamiento", 8, "Xativa", "Valencia");
        AlumnoEntidad alumno = new AlumnoEntidad(1, "Juan", "Perez", "García");
        alumno.setDireccion(direcsion);
        setUp();
        Session session=sessionFactory.openSession();
        session.beginTransaction();

        session.save(alumno);

        session.getTransaction().commit();
        session.close();
    }

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
}
