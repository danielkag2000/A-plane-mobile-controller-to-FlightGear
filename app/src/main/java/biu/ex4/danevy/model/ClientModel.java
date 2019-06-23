package biu.ex4.danevy.model;

public interface ClientModel {

    String AILERON_PATH = "/controls/flight/aileron";
    String ELEVATOR_PATH = "/controls/flight/elevator";

    /**
     * Connect to a flight server.
     * @param ip the ip of the server
     * @param port the port of the server
     */
    void connect(String ip, int port);

    /**
     * Close the current connection to the flight server.
     */
    void disconnect();

    /**
     * Send one command to the flight server.
     * @param cmd the command string
     */
    void sendCommand(String cmd);

    default void set(String path, float value) {
        String cmd = "set " + path + " " + value;
        sendCommand(cmd);
    }
}
