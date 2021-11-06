package io.github.vallasc;

public class APInfo {
    private final String BSSID;
    private final String SSID;
    private final double signal;
    private final double frequency;
    private final String interfaceName;

    public APInfo(String BSSID, String SSID, double signal, double frequency, String interfaceName) {
        this.BSSID = BSSID;
        this.SSID = SSID;
        this.signal = signal;
        this.frequency = frequency;
        this.interfaceName = interfaceName;
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

    public String getInterfaceName() {
        return interfaceName;
    }

    @Override
    public String toString() {
        return "BSSID: " + BSSID + "\n" +
                "SSID: " + SSID + "\n" +
                "Signal: " + signal + "\n" +
                "Frequency: " + frequency + "\n" +
                "Interface name: " + interfaceName;
    }
}