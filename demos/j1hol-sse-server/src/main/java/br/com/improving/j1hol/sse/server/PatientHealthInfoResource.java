package br.com.improving.j1hol.sse.server;

import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.PostConstruct;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;

@Path("health")
@Singleton
public class PatientHealthInfoResource {
    private final AtomicLong messageId = new AtomicLong();

    @Context
    private Sse sse;
    private volatile SseBroadcaster broadcaster;

    @PostConstruct
    public void init() {
        this.broadcaster = sse.newBroadcaster();
    }

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void register(@Context SseEventSink eventSink) {
        broadcaster.register(eventSink);
    }

    void on(@ObservesAsync PatientHealthInfo info) {
        broadcaster.broadcast(sse.newEventBuilder()
                .id(String.valueOf(messageId.getAndIncrement()))
                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                .name(info.getName())
                .data(PatientHealthInfo.class, info)
                .build());
    }
}
