/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.j1hol;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.enterprise.event.ObservesAsync;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;


/**
 *
 * @author boblarsen
 */
@Path("sse")
@Stateless
public class SseService {
    private SseBroadcaster broadcaster;

    @Context
    private Sse sse;

    @PostConstruct
    public void setup() {
        broadcaster = sse.newBroadcaster();
    }

    @PreDestroy
    public void tearDown() {
        broadcaster.close();
    }

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void register(@Context SseEventSink sseEventSink) {
        broadcaster.register(sseEventSink);
    }

    public void listen(@ObservesAsync Double event) {
        OutboundSseEvent sseEvent = sse.newEvent("ticker value", String.format("$%.2f", event));
        broadcaster.broadcast(sseEvent);
    }
}
