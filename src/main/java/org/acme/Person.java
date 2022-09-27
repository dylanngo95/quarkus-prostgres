package org.acme;


import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Random;

@Path("/insert")
public class Person {

    @Inject
    io.vertx.mutiny.pgclient.PgPool client;

    @Inject
    @ConfigProperty(name = "myapp.schema.create", defaultValue = "true")
    boolean schemaCreate;

    @GET
    public String insert() {
        Random rand = new Random();
        int n = rand.nextInt(50);
        String query = "INSERT INTO fruits (name) VALUES ('Orange" + n + "')";
        client.query(query).execute()
                .await().indefinitely();
        return "init db is done";
    }
}
