package org.demo.core;

import org.demo.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListenerThread extends Thread{

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);

    private static int port;
    private static String webRoot;

    private static ServerSocket serverSocket;

    private static final String CLRF = "\n\r";

    public ServerListenerThread(int port, String webRoot) throws IOException {
        ServerListenerThread.port = port;
        ServerListenerThread.webRoot = webRoot;
        ServerListenerThread.serverSocket = new ServerSocket(port);
    }
    @Override
    public void run() {
        try {
//            ServerSocket serverSocket = new ServerSocket(port);

            LOGGER.info("A new client has connected");
//            while(serverSocket.isBound() && !serverSocket.isClosed()){
//                HttpClientWorkerThread httpClientWorkerThread = new HttpClientWorkerThread(socket);
//                httpClientWorkerThread.start();
//            }
            while(serverSocket.isBound() && !serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
//                InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
//                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
//                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                OutputStream outputStream = socket.getOutputStream();
                String html = "<html><head><title>Simple Http Java Server</title></head><body><h1>This page was served using my Simple Http Java Server</h1></body></html>";
                String response = "HTTP/1.1 200 OK" + CLRF // Status Line
                        + "Content-Length: " + html.getBytes().length + CLRF //Header
                        + CLRF
                        + html
                        + CLRF + CLRF;
                outputStream.write(response.getBytes());
//                bufferedWriter.write(response);
//                bufferedWriter.flush();
//                bufferedReader.close();
//                bufferedWriter.close();
                socket.close();
            }
//            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
