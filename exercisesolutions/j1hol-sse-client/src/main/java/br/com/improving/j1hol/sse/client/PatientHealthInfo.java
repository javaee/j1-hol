package br.com.improving.j1hol.sse.client;

import java.time.Instant;

public class PatientHealthInfo {
    private long id;
    private String name;
    private Instant timestamp;
    private int systolicPressure;
    private int diastolicPressure;
    private int heartRate;

    public PatientHealthInfo() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSystolicPressure() {
        return systolicPressure;
    }

    public void setSystolicPressure(int systolicPressure) {
        this.systolicPressure = systolicPressure;
    }

    public int getDiastolicPressure() {
        return diastolicPressure;
    }

    public void setDiastolicPressure(int diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "PatientHealthInfo{" + "id=" + id + ", name=" + name 
                + ", timestamp=" + timestamp + ", systolicPressure=" 
                + systolicPressure + ", diastolicPressure=" + diastolicPressure 
                + ", heartRate=" + heartRate + '}';
    }

}
