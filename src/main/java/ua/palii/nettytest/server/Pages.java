package ua.palii.nettytest.server;

import ua.palii.nettytest.server.ConnectionInfo;
import ua.palii.nettytest.server.Statistics;

/**
 * Created by VVV on 05.08.2015.
 */
public class Pages {

    private static Statistics statistics = Statistics.getInstance();

    public static String getHelloPage() {
        return "<html><head><title>Hello world</title>\n" +
                "<link rel=\"shortcut icon\" href=\"data:image/x-icon;,\" type=\"image/x-icon\">\n" +
                "</head><body><h1>Hello world!</h1></body></html>";
    }

    public static String getNotFoundPage() {
        return "<html><head><title>404</title>\n" +
                "<link rel=\"shortcut icon\" href=\"data:image/x-icon;,\" type=\"image/x-icon\">\n" +
                "</head><body><h1>Not found!</h1></body></html>";
    }

    public static String getStatisticsPage() {
        StringBuffer s = new StringBuffer("<!DOCTYPE html><html><head><title>Statistics</title>\n" +
                "<link rel=\"shortcut icon\" href=\"data:image/x-icon;,\" type=\"image/x-icon\">\n" +
                "</head><body><h1>Statistics</h1><hr><table border=\"1\"><tr><th>Total requests</th>\n" +
                "<th>Unique requests</th></tr><tr><td>");
        s.append(statistics.getRequestCount());
        s.append("</td><td>");
        s.append(statistics.getUniqueRequests());
        s.append("</td></tr></table> <h2>IP requests counter :</h2><table border='1'><tr><th>IP</th><th>Request count</th><th>Last request</th></tr>");

        for (String key : statistics.getIpRequestCounter().keySet()) {
            s.append("<tr><td>");
            s.append(key);
            s.append("</td><td>");
            s.append(statistics.getIpRequestCounter().get(key).getCount());
            s.append("</td><td>");
            s.append(statistics.getIpRequestCounter().get(key).getTimeLastRequest());
            s.append("</td></tr>");
        }
        s.append("</table><hr><h2>URL redirection counter :</h2><table border='1'><tr><th>URL</th><th>Redirection count</th></tr>");
        for (String key : statistics.getRedirectionMap().keySet()) {
            s.append("<tr><td>");
            s.append(key);
            s.append("</td><td>");
            s.append(statistics.getRedirectionMap().get(key));
            s.append("</td></tr>");
        }
        s.append("</table><h2>Open connections:" );
        s.append(statistics.getOpenConnections());
        s.append("</h2><h2>Connections log :</h2><table border='1'><tr><th>SRC_IP</th>" +
                "<th>URI</th><th>timestamp</th><th>sent bytes</th><th>received bytes</th>" +
                "<th>speed (bytes/sec)</th></tr>");
        for (ConnectionInfo ci : statistics.getConnectionLog()) {
            s.append("<tr><td>");
            s.append(ci.getSrcIp());
            s.append("</td><td>");
            s.append(ci.getUri());
            s.append("</td><td>");
            s.append(ci.getTimestamp());
            s.append("</td><td>");
            s.append(ci.getSentBytes());
            s.append("</td><td>");
            s.append(ci.getReceivedBytes());
            s.append("</td><td>");
            s.append(ci.getSpeed());
            s.append("</td></tr>");
        }

        s.append("</table><hr></body></html>");
        return s.toString();
    }
}

