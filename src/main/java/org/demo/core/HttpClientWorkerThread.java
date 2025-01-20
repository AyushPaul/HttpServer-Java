package org.demo.core;

import java.io.*;
import java.net.Socket;

public class HttpClientWorkerThread extends Thread{
    private static Socket socket;
    private static final String CLRF = "\n\r";
    public HttpClientWorkerThread(Socket socket){
        HttpClientWorkerThread.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            String html = "<html><head><title>Simple Http Java Server</title></head><body><h1>This page was served using my Simple Http Java Server</h1></body></html>";
            String response = "HTTP/1.1 200 OK" + CLRF // Status Line
                              + "Content-Length: " + html.getBytes().length + CLRF //Header
                              + CLRF
                              + html
                              + CLRF + CLRF;
            bufferedWriter.write(response);
            bufferedWriter.flush();
            bufferedReader.close();
            bufferedWriter.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
