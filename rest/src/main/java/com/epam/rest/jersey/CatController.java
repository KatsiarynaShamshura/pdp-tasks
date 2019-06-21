package com.epam.rest.jersey;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Service
@Path("/cats")
@Produces("application/json")
@Consumes("application/json")
public class CatController {

    private List<Cat> cats = new ArrayList<>();

    @GET
    @Path("/{id}")
    public Cat getCat(@PathParam("id") String id) {
        for (Cat cat : cats) {
            if (cat.getId().equals(id)) {
                return cat;
            }
        }
        return null;
    }

    @GET
    public List<Cat> getCats() {
        return cats;
    }

    @POST
    public ResponseEntity<String> create(Cat cat) {
        cat.setId(UUID.randomUUID().toString());
        cats.add(cat);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PUT
    @Path("/{id}")
    public ResponseEntity<String> update(@PathParam("id") String id, Cat newCat) {
        for (Cat cat : cats) {
            if (cat.getId().equals(id)) {
                cat.setName(newCat.getName());
                cat.setEat(newCat.getEat());
                break;
            }
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DELETE
    @Path("/{id}")
    public ResponseEntity<String> delete(@PathParam("id") String id) {
        for (int i = 0; i < cats.size(); i++) {
            if (cats.get(i).getId().equals(id)) {
                cats.remove(i);
                break;
            }
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
