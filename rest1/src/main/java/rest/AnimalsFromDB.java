/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import entity.Animal;
import javax.ws.rs.PathParam;

/**
 * REST Web Service
 *
 * @author Marcus
 */
@Path("animals_db")
public class AnimalsFromDB {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
    
    
    @Path("animals")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAnimals() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Animal> query = em.createQuery("SELECT a FROM Animal a", Animal.class);
            List<Animal> animals = query.getResultList();
            return new Gson().toJson(animals);
        } finally {
            em.close();
        }
    }

    @Path("animalbyid/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAnimal(@PathParam("id") int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Animal animal = em.find(Animal.class, id);
            return new Gson().toJson(animal);
        } catch (Exception e) {
            String err = "{\"err\":404}";
            return err;
            //Hvis den kaldes med .../animalbyid/2  vil id nu være lig 2.
            //Den værdi kan I så benytte til at slå op i databasen med em.find(

        }
    }
    @Path("animalbytype/{type}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String findByType(@PathParam("type") String type) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Animal> query = em.createQuery("SELECT a from Animal a WHERE a.type = :type", Animal.class);
            return new Gson().toJson(query.setParameter("type", type).getResultList());
           
        } catch (Exception e) {
            String err = "{\"err\":404}";
            return err;
            //Hvis den kaldes med .../animalbyid/2  vil id nu være lig 2.
            //Den værdi kan I så benytte til at slå op i databasen med em.find(

        }finally{
        em.close();
    }
    }    
    
    
    @Path("randomanimal")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String randomanimals() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Animal> query = em.createQuery("SELECT a FROM Animal a", Animal.class);
            List<Animal> animals = query.getResultList();
            return new Gson().toJson(animals.get((int)(Math.random()* animals.size())));
             } catch (Exception e) {
            String err = "{\"err\":404}";
            return err;
        } finally {
            em.close();
        }
    }
    
}


