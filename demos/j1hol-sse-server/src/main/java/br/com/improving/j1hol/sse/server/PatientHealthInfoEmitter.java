package br.com.improving.j1hol.sse.server;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PatientHealthInfoEmitter {
    private final List<String> patients = Arrays.asList("Santos", "Heffelfinger", 
            "Delabassee");

    @Inject
    private Event<PatientHealthInfo> info;

    // Don't do this at home; injecting a proper SES would require an EAR due to
    // JSR-236 not being part of the web profile
    private final ScheduledExecutorService executorService = 
            Executors.newSingleThreadScheduledExecutor();

    void init(@Observes @Initialized(ApplicationScoped.class) Object event) {
        executorService.scheduleAtFixedRate(() -> {
            collectPatientsHealthInfo();
        }, 0, 1, TimeUnit.SECONDS);
    }

    void collectPatientsHealthInfo() {
        IntStream.range(0, patients.size())
                .mapToObj(this::patientHealthInfo)
                .forEach(info::fireAsync);
    }

    private PatientHealthInfo patientHealthInfo(int index) {
        return new PatientHealthInfo(index, patients.get(index),
                Math.round(Math.random() * 40) + 100,
                Math.round(Math.random() * 40) + 60
        );
    }
}
