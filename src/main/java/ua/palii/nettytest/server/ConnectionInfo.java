package ua.palii.nettytest.server;

import java.time.LocalDateTime;

/**
 * Created by VVV on 05.08.2015.
 */
public class ConnectionInfo {

    private String srcIp;

    private String uri;

    private Traffic traffic;

    public ConnectionInfo(RequestInfo requestInfo) {
        srcIp = requestInfo.getIP();
        uri = requestInfo.getUri();
    }

    public String getSrcIp() {
        return srcIp;
    }

    public String getUri() {
        return uri;
    }

    public void setTraffic(Traffic traffic) {
        this.traffic = traffic;
    }

    public long getSentBytes() {
        return traffic.getSentBytes();
    }

    public long getReceivedBytes() {
        return traffic.getReceivedBytes();
    }

    public LocalDateTime getTimestamp() {
        return traffic.getTimestamp();
    }

    public double getSpeed() {
        return traffic.getSpeed();
    }
}
