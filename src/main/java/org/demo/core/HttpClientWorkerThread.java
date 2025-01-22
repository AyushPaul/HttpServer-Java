package org.demo.core;

import java.io.*;
import java.net.Socket;

public class HttpClientWorkerThread extends Thread {
    private static Socket socket;
    private static final String CLRF = "\n\r";

    public HttpClientWorkerThread(Socket socket) {
        HttpClientWorkerThread.socket = socket;
    }

    @Override
    public void run() {
        OutputStream outputStream = null;
        try {
            outputStream = socket.getOutputStream();
            String html = "<html><head><title>Simple Http Java Server</title></head><body><h1>This page was served using my Simple Http Java Server</h1></body></html>";
            String response = "HTTP/1.1 200 OK" + CLRF // Status Line
                    + "Content-Length: " + html.getBytes().length + CLRF //Header
                    + CLRF
                    + html
                    + CLRF + CLRF;
            outputStream.write(response.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if(outputStream != null) outputStream.close();
                if(socket != null) socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
