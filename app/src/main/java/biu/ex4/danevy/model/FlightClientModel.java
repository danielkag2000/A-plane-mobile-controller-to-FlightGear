package biu.ex4.danevy.model;

import android.util.Log;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class FlightClientModel implements IClientModel {

    private Socket socket;
    private boolean isOpen = false;
    private PrintWriter output;

    @Override
    public void connect(String ip, int port) {
        if (isOpen) {
            return;
        }
        try {
            // put the IP address.
            InetAddress serverAddr = InetAddress.getByName(ip);

            // create a socket to make the connection with the server
            this.socket = new Socket(serverAddr, port);

            // create the OutputStream
            this.output = new PrintWriter(socket.getOutputStream());

        } catch (Exception e) {
            Log.e("TCP", "C: Error", e);
        }
        isOpen = true;
    }

    @Override
    public void disconnect() {
        isOpen = false;
        this.output.close();

        try {
            this.socket.close();
        } catch (Exception e) {
            Log.e("TCP", "Socket close Exception: Error", e);
        }
    }

    @Override
    public void sendCommand(String cmd) {
        this.output.print(cmd);
        this.output.print("\r\n");
        this.output.flush();
    }
}
