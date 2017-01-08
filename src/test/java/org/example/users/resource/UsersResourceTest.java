/*
 * Copyright 2016 Microprofile.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.example.users.resource;

import org.example.users.Application;
import org.example.users.model.UserData;
import org.example.users.service.UserDataRetriever;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Arquillian.class)
@RunAsClient
public class UsersResourceTest {

    @ArquillianResource
    private URL base;

    @Deployment
    public static WebArchive getDeployment() {
        WebArchive archive = ShrinkWrap.create(WebArchive.class, "users-test.war")
                .addClasses(UserData.class, Application.class, UsersResource.class)
                .addPackage(UserDataRetriever.class.getPackage())
                .addAsResource("META-INF/persistence.xml");
        System.out.println(archive.toString(true));
        return archive;
    }

    @Test
    public void shouldReturnProperResponseForOneUser() throws Exception {
        Response response = requestUserData("Jon", "Snow");
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        JsonObject content = readJsonContent(response);
        assertEquals("demo", content.getString("mode"));
        JsonArray users = content.getJsonArray("users");
        assertEquals(1, users.size());
        assertTrue(users.getJsonObject(0).getString("name").equals("mr. jon snow"));
    }

    @Test
    public void shouldReturnProperResponseMultipleUsers() throws Exception {
        Response response = requestUserData("Jon", null);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        JsonObject content = readJsonContent(response);
        assertEquals("demo", content.getString("mode"));
        JsonArray users = content.getJsonArray("users");
        assertEquals(2, users.size());
    }

    @Test
    public void shouldReturnEmptyArrayWhenNoUserMatching() throws Exception {
        Response response = requestUserData("Foo", "Bar");
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        JsonObject content = readJsonContent(response);
        assertEquals("demo", content.getString("mode"));
        JsonArray users = content.getJsonArray("users");
        assertEquals(0, users.size());
    }

    @Test
    public void shouldReturnBadRequestIfFirstNameIsMissing() throws Exception {
        Response response = requestUserData(null, "Blah");
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    private Response requestUserData(String firstName, String lastName) throws MalformedURLException {
        final URL url = new URL(base, "users/find");
        final WebTarget target = ClientBuilder.newClient().target(url.toExternalForm());
        Form form = new Form();
        if (firstName != null) {
            form.param("firstName", firstName);
        }
        if (lastName != null) {
            form.param("lastName", lastName);
        }
        return target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));
    }

    private static JsonObject readJsonContent(Response response) {
        final JsonReader jsonReader = readJsonStringFromResponse(response);
        return jsonReader.readObject();
    }

    private static JsonReader readJsonStringFromResponse(Response response) {
        final String competitionJson = response.readEntity(String.class);
        final StringReader stringReader = new StringReader(competitionJson);
        return Json.createReader(stringReader);
    }
}
