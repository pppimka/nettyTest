package ua.palii.nettytest.server;

/**
 * Created by PC on 01.08.2015.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        int port;

        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new Server(port).start();
    }
}


