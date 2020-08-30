package facades;

import entities.Employee;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FacadeTest {
 
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
    private static final Facade facade = Facade.getFacade(emf);

    private static Employee e1 = new Employee("Torben", "Torbengade 1", 789622);
    private static Employee e2 = new Employee("Jurgen", "Jurgengade 22", 12456);
    private static Employee e3 = new Employee("Tom", "Tomgade 23", 123411);
    private static List<Employee> ems = new ArrayList();

    public FacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        EntityManager em = emf.createEntityManager();

        ems.add(e1);
        ems.add(e2);
        ems.add(e3);

        try {
            for (Employee employee : ems) {
                em.getTransaction().begin();
                em.persist(employee);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
//        createEmployee();

//        Add code to setup entities for test before running any test methods
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    @BeforeEach
    public void setUp() {
//        Put the test database in a proper state before each test is run
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    @Test
    public void testGetEmployeeById() {
        Employee ex = e1;
        Employee ac = facade.getEmployeebyID(1);
        assertEquals(ex, ac);
    }

    @Test
    public void testGetAllEmployees() {
        List<Employee> ex = ems;
        List<Employee> ac = facade.getAllEmployees();
        assertEquals(ex, ac);
    }

    @Test
    public void testGetEmployeesWithHighestSalary() {
        List<Employee> ex = new ArrayList();
        ex.add(e1);
        List<Employee> ac = facade.getEmployeebyHighestSalary();
        assertEquals(ex, ac);
    }

    @Test
    public void testGetEmployeesByName() {
        List<Employee> ex = new ArrayList();
        ex.add(e3);
        List<Employee> ac = facade.getEmployeebyName("Jurgen");
        assertEquals(ex, ac);
    }

    @Test
    public void createEmployee() {
        Employee e = new Employee("Jurgen", "Jurgengade 1", 12342);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(e);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        Employee ex = e;
        Employee ac = facade.getEmployeebyID(36);
        assertEquals(ex, ac);
    }

    
}
