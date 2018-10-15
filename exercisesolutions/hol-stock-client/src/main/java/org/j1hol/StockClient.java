/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.j1hol;

import java.time.LocalDateTime;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.sse.SseEventSource;

/**
 *
 * @author boblarsen
 */
public class StockClient {

    private WebTarget target;

    public static void main(String[] args) {
        StockClient stockClient = new StockClient();
        stockClient.listen();
    }

    public StockClient() {
        Client client = ClientBuilder.newClient();
        target = client.target("http://localhost:8080/hol-stock-server/webresources/sse");
    }

    public void listen() {
        SseEventSource sse = SseEventSource.target(target).build();
        sse.register((event) -> System.out.println(LocalDateTime.now() + ": " + event.readData()));
        sse.open();
    }
}
