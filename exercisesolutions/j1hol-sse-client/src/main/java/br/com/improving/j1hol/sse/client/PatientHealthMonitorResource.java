package br.com.improving.j1hol.sse.client;

import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("health")
@Singleton
public class PatientHealthMonitorResource {
    @Inject
    private PatientHealthInfoCollector collector;

    @Path("{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response monitor(@PathParam("name") String name) {
        Optional<PatientHealthMonitor> monitor = collector.get(name);

        if (monitor.isPresent()) {
            return Response.ok(monitor.get()).build();
        }

        return Response.status(Status.NOT_FOUND).build();
    }
}
