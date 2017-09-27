package br.com.improving.j1hol.sse.client;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Comparator;

public class PatientHealthMonitor {
    private final String name;
    private final Instant timestamp;
    private final Duration duration;
    private final String pressure;
    private final int heartRate;
    
    public PatientHealthMonitor(Collection<PatientHealthInfo> infos) {
        // If empty, will throw an exception. That's expected
        PatientHealthInfo info = infos.stream().findAny().get();
        name = info.getName();
        timestamp = infos.stream()
                .map(PatientHealthInfo::getTimestamp)
                .max(Comparator.naturalOrder()).get();
        duration = Duration.between(infos.stream()
                .map(PatientHealthInfo::getTimestamp)
                .min(Comparator.naturalOrder()).get(),
                timestamp);
        pressure = Math.round(infos.stream()
                .mapToInt(PatientHealthInfo::getSystolicPressure)
                .average().getAsDouble()) + " / " +
                Math.round(infos.stream()
                .mapToInt(PatientHealthInfo::getDiastolicPressure)
                .average().getAsDouble());
        heartRate = Math.toIntExact(Math.round(infos.stream()
                .mapToInt(PatientHealthInfo::getHeartRate)
                .average().getAsDouble()));
    }

    public String getName() {
        return name;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Duration getDuration() {
        return duration;
    }

    public String getPressure() {
        return pressure;
    }

    public int getHeartRate() {
        return heartRate;
    }

}
