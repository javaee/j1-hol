package br.com.improving.j1hol.sse.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.InboundSseEvent;
import javax.ws.rs.sse.SseEventSource;

@Singleton
public class PatientHealthInfoCollector {
    private static final Logger LoGGER = Logger.getLogger(PatientHealthInfoCollector.class.getName());

    private final Map<String,List<PatientHealthInfo>> infosPerPatient 
            = new HashMap<>();

    @PostConstruct
    void init() {
        LoGGER.info("Starting");
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://127.0.0.1:8080/j1hol-sse-server/api/health/");
        SseEventSource source = SseEventSource.target(target).build();
        LoGGER.info("Connecting");
        source.register(this::process, ex -> LoGGER.log(Level.SEVERE, "Error processing SSE", ex));
        source.open();
        LoGGER.info("Reading events");
    }

    private void process(InboundSseEvent event) {
        final PatientHealthInfo info = event.readData(PatientHealthInfo.class, 
                MediaType.APPLICATION_JSON_TYPE);
        infosPerPatient.computeIfAbsent(event.getName(), s -> new ArrayList<>())
                .add(info);
        LoGGER.info(info.toString());
    }
    
    public Optional<PatientHealthMonitor> get(String name) {
        LoGGER.log(Level.INFO, "Monitoring {0}", name);
        return Optional.ofNullable(infosPerPatient.get(name))
                .filter(infos -> !infos.isEmpty())
                .map(PatientHealthMonitor::new);
    }
}
