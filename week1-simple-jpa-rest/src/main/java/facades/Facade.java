/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Employee;
import facades.Facade;
import entities.Employee;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Marcus
 */
public class Facade {

    private static EntityManagerFactory emf;
    private static Facade instance;


    private Facade() {
    }

    public static Facade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new Facade();
        }
        return instance;

    }

    public void addEmployee(String name, String adress, int salary) {
        Employee employee = new Employee(name, adress, salary);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(employee);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }

    public Employee getEmployeebyID(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Employee employee = em.find(Employee.class, id);
            return employee;
        } finally {
            em.close();
        }
    }

    public List<Employee> getAllEmployees() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Employee> query
                    = em.createQuery("Select employee from Employee employee", Employee.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    //Bruger id til at søge efter en og med id, og idet man bruger facaden søger denefter author og får Author ud.
    public List<Employee> getEmployeebyName(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Employee> query
                    = em.createQuery("SELECT employee from Employee employee WHERE employee.lastName = :name", Employee.class);
            query.setParameter("name", name);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public  List<Employee> getEmployeebyHighestSalary() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Employee> query
                    = em.createQuery("SELECT employee from Employee employee WHERE employee.salary = "
                            + "(SELECT MAX (employee.salary) from Employee employee)", Employee.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

}
