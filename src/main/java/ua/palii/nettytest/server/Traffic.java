package ua.palii.nettytest.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.traffic.ChannelTrafficShapingHandler;
import io.netty.handler.traffic.TrafficCounter;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * Created by Mikhail on 07.08.2015.
 */
public class Traffic {

    private LocalDateTime timestamp;
    private long sentBytes;
    private long receivedBytes;
    private double speed;

    private TrafficCounter trafficCounter;

    public Traffic(ChannelHandlerContext ctx) {
        trafficCounter = ctx.pipeline().get(ChannelTrafficShapingHandler.class).trafficCounter();
        trafficCounter.start();

    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public long getSentBytes() {
        return sentBytes;
    }

    public long getReceivedBytes() {
        return receivedBytes;
    }

    public double getSpeed() {
        return speed;
    }

    public void stop() {
        trafficCounter.stop();
        timestamp = LocalDateTime.now();
        sentBytes = trafficCounter.cumulativeWrittenBytes();
        receivedBytes = trafficCounter.cumulativeReadBytes();
        speed = (sentBytes + receivedBytes) * 1000000 /
                (System.currentTimeMillis() - trafficCounter.lastCumulativeTime());
    }

    @Override
    public String toString() {
        return "Traffic: [sent bytes: " + sentBytes +
                " received bytes: " + receivedBytes +
                " speed: " + speed;
    }
}
