package org.demo.core;

import org.demo.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListenerThread extends Thread{

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);

    private int port;
    private  String webRoot;

    private ServerSocket serverSocket;

    private static final String CLRF = "\n\r";

    public ServerListenerThread(int port, String webRoot) throws IOException {
        this.port = port;
        this.webRoot = webRoot;
        this.serverSocket = new ServerSocket(port);
    }
    @Override
    public void run() {
        try {
//            ServerSocket serverSocket = new ServerSocket(port);
//            Socket socket = serverSocket.accept(); // Initializing socket here will result, the serverSocket.accept() to not listen to the port for the 2nd time as the code never reaches here.
            while(serverSocket.isBound() && !serverSocket.isClosed()){
                Socket socket = serverSocket.accept(); // It will continue to listen as long as the serverSocket is not closed.
                LOGGER.info("A new client has connected");
                HttpClientWorkerThread httpClientWorkerThread = new HttpClientWorkerThread(socket);
                httpClientWorkerThread.start();
//                socket.close(); we are not closing the socket here , because as soon as the httpclientworkerthread starts it's execution, the socket.close() is also called in parallel and hence the socket is closed before a response could be sent
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
