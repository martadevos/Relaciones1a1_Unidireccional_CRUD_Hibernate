import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Scanner;

public class CRUD {

    private static SessionFactory sessionFactory;
    private static Session session;

    protected static void setUp() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // por defecto: hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static void open(Transaction transaction) {
        transaction.commit();
        sessionFactory.close();
    }
    public static void close(Transaction transaction) {
        sessionFactory.close();
    }

    public static void create(int idAlumno, String[] datos, int numCalle) {
        AlumnadoEntity alumno = new AlumnadoEntity(idAlumno, datos[0], datos[1], datos[2]);
        DireccionEntity direccion = new DireccionEntity(idAlumno, datos[3], numCalle, datos[4], datos[5]);

        alumno.setDireccion(direccion);
        Transaction transaction = session.beginTransaction();
        int id = (int) session.save(alumno);
        transaction.commit();
        System.out.println("Id insertado: " + id);
    }

    /*public static void read(int id) {
        Transaction transaction = session.beginTransaction();
        AgendaEntity contacto = session.get(AgendaEntity.class, id);
        transaction.commit();
        System.out.println("Nombre: " + contacto.getNombre() + "    Telf: " + contacto.getTelf());
    }

    public static void update(int id, String nombre, String telf) {
        Transaction transaction = session.beginTransaction();
        AgendaEntity contacto = session.get(AgendaEntity.class, id);
        contacto.setNombre(nombre);
        contacto.setTelf(telf);
        AgendaEntity contactoActualizado = session.merge(contacto);
        transaction.commit();
        System.out.println("Nombre: " + contactoActualizado.getNombre() + "    Telf: " + contactoActualizado.getTelf());
    }

    public static void delete(int id) {
        Transaction transaction = session.beginTransaction();
        AgendaEntity contacto = session.get(AgendaEntity.class, id);
        session.remove(contacto);
        transaction.commit();
    }*/

    public static boolean menu(int resp) {
        Scanner s = new Scanner(System.in);
        boolean salir = false;
        session = sessionFactory.openSession();
        switch (resp) {
            case 1 -> {
                String[] datos = new String[6];
                int id, numCalle;
                System.out.println("Introduce el id del Alumno a crear");
                id = s.nextInt();
                System.out.println("Introduce el nombre del Alumno a crear");
                datos[0] = s.next();
                System.out.println("Introduce el primer apellido del Alumno a crear");
                datos[1] = s.next();
                System.out.println("Introduce el segundo apellido del Alumno a crear");
                datos[2] = s.next();
                System.out.println("Introduce la calle de residencia del Alumno a crear");
                datos[3] = s.next();
                System.out.println("Introduce el numero de calle del Alumno a crear");
                numCalle = s.nextInt();
                System.out.println("Introduce la poblacion de residencia del Alumno a crear");
                datos[4] = s.next();
                System.out.println("Introduce la provincia de residencia del Alumno a crear");
                datos[5] = s.next();
                CRUD.create(id, datos, numCalle);
            }
            /*case 2 -> {
                int id;
                System.out.println("Introduce el id del contacto a buscar");
                id = s.nextInt();
                CRUD.read(id);
            }
            case 3 -> {
                int id;
                String nombre, telf;
                System.out.println("Introduce el id del contacto a cambiar");
                id = s.nextInt();
                System.out.println("Introduce el nombre nuevo para el contacto a cambiar");
                nombre = s.next();
                System.out.println("Introduce el telefono nuevo para el contacto a cambiar");
                telf = s.next();
                CRUD.update(id, nombre, telf);
            }
            case 4 -> {
                int id;
                System.out.println("Introduce el id del contacto a borrar");
                id = s.nextInt();
                CRUD.delete(id);
            }*/
            default -> salir = true;
        }
        return salir;
    }

}
