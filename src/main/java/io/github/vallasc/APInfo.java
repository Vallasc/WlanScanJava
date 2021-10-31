package io.github.vallasc;

public class APInfo {
    private final String BSSID;
    private final String SSID;
    private final double signal;
    private final double frequency;

    public APInfo(String BSSID, String SSID, double signal, double frequency) {
        this.BSSID = BSSID;
        this.SSID = SSID;
        this.signal = signal;
        this.frequency = frequency;
    }

    public double getFrequency() {
        return frequency;
    }

    public double getSignal() {
        return signal;
    }

    public String getSSID() {
        return SSID;
    }

    public String getBSSID() {
        return BSSID;
    }

    @Override
    public String toString() {
        return "BSSID: " + BSSID + "\n" +
                "SSID: " + SSID + "\n" +
                "Signal: " + signal + "\n" +
                "Frequency: " + frequency;
    }
}