package rest;

import entities.Employee;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.Facade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("employee")
public class RenameMeResource {

    //NOTE: Change Persistence unit name according to your setup
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
    Facade facade = Facade.getFacade(emf);
    private static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"succes\"}";
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public void create(Employee entity) {
        throw new UnsupportedOperationException();
    }

    
    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getById(@PathParam("id") int id) {
        String json = GSON.toJson(facade.getEmployeebyID(id));
       return json;
    }
    
    @Path("/all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAll() {
        List<Employee> json = facade.getAllEmployees();
        
            return GSON.toJson(json);
    }
    
    @Path("/higestpaid")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Employee> getHigestPaid() {
        return facade.getEmployeebyHighestSalary();
    }
    
    @Path("/{name}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getByName(@PathParam("name") String name) {
        List<Employee> json = facade.getEmployeebyName(name);
       return GSON.toJson(json);
    }
    
    
    
    
}
