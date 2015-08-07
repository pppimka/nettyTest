package ua.palii.nettytest.server;

import java.time.LocalDateTime;

/**
 * Created by Mikhail on 06.08.2015.
 */
public class CountRequestsAndTime {

    private Integer count;

    private LocalDateTime timeLastRequest;

    public CountRequestsAndTime() {
    }

    public CountRequestsAndTime(Integer count, LocalDateTime timeLastRequest) {
        this.count = count;
        this.timeLastRequest = timeLastRequest;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public LocalDateTime getTimeLastRequest() {
        return timeLastRequest;
    }

    public void setTimeLastRequest(LocalDateTime timeLastRequest) {
        this.timeLastRequest = timeLastRequest;
    }
}
