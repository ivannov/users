package org.example.users.resource;

import org.example.users.model.UserData;
import org.example.users.service.UserDataRetriever;
import org.example.users.service.UserDataRetrieverFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
@ApplicationScoped
public class UsersResource {

    @Inject
    private UserDataRetrieverFactory factory;

    @POST
    @Path("/find")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findUsers(@FormParam("firstName") String firstName,
                              @FormParam("lastName") String lastName) {
        if (firstName == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        UserDataRetriever userDataRetriever = factory.build();

        List<UserData> users;
        if (lastName == null) {
            users = userDataRetriever.findUsersByFirstName(firstName);
        } else {
            users = userDataRetriever.findUsersByFirstAndLastName(firstName, lastName);
        }

        return Response.ok(buildEntity(users)).build();
    }

    private JsonObject buildEntity(List<UserData> users) {
        JsonObjectBuilder usersObjectBuilder = Json.createObjectBuilder();
        usersObjectBuilder.add("mode", System.getProperty("mode", "demo"));
        JsonArrayBuilder userDataArrayBuilder = Json.createArrayBuilder();
        for (UserData user : users) {
            userDataArrayBuilder.add(Json.createObjectBuilder().add("name", user.toString()));
        }
        usersObjectBuilder.add("users", userDataArrayBuilder.build());
        return usersObjectBuilder.build();
    }
}
