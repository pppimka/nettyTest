package ua.palii.nettytest.server;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by VVV on 05.08.2015.
 */
public class Statistics {

    private ConcurrentHashMap<String, Integer> redirect;

    private ConcurrentHashMap<String, CountRequestsAndTime> ipRequestCounter;

    private ConcurrentLinkedQueue<ConnectionInfo> connectionLog;

    private Integer openConnection = 0;

    private volatile long requestCount;

    private static Statistics instance = new Statistics();

    private Statistics() {
        redirect = new ConcurrentHashMap<String, Integer>();
        ipRequestCounter = new ConcurrentHashMap<String, CountRequestsAndTime>();
        connectionLog = new ConcurrentLinkedQueue<ConnectionInfo>();
    }

    public void addConnection() {
        openConnection++;

    }

    public void removeConnection() {
        openConnection--;
    }

    public Integer getOpenConnections() {
        return openConnection;
    }

    public long getRequestCount() {
        return requestCount;
    }

    public void addCountRequest(RequestInfo requestInfo) {
        String ip = requestInfo.getIP();
        if (ipRequestCounter.containsKey(ip)) {
            CountRequestsAndTime requestsAndTime = ipRequestCounter.get(ip);
            requestsAndTime.setCount(requestsAndTime.getCount() + 1);
            requestsAndTime.setTimeLastRequest(LocalDateTime.now());
            ipRequestCounter.put(ip, requestsAndTime);
        } else {
            ipRequestCounter.put(ip, new CountRequestsAndTime(1, LocalDateTime.now()));
        }
        requestCount++;
    }

    public Integer getUniqueRequests() {
        return ipRequestCounter.size();
    }

    public synchronized static Statistics getInstance() {
        return instance;
    }

    public void addLastConnection(ConnectionInfo ci) {
        if (connectionLog.size() >= 16) {
            connectionLog.poll();
        }
        connectionLog.add(ci);
    }

    public Map<String, Integer> getRedirectionMap() {
        return redirect;
    }

    public Map<String, CountRequestsAndTime> getIpRequestCounter() {
        return ipRequestCounter;
    }

    public Queue<ConnectionInfo> getConnectionLog() {
        return connectionLog;
    }

    public void addRedirect(String url) {
        if (redirect.containsKey(url)) {
            redirect.put(url, redirect.get(url) + 1);
        } else {
            redirect.put(url, 1);
        }
    }


}
