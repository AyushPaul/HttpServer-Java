package org.demo.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class HttpClientWorkerThread extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientWorkerThread.class);
    private static Socket socket;
    private static final String CLRF = "\n\r";

    public HttpClientWorkerThread(Socket socket) {
        HttpClientWorkerThread.socket = socket;
    }

    @Override
    public void run() {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();
//            int _byte;
//            This loop will block the execution of the code , as even if the browser/client is not sending any data it is still listening
//            while((_byte = inputStream.read()) >= 0){
//                System.out.print((char) _byte);
//            }
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            LOGGER.info("REQUEST : \n" + bufferedReader.readLine());
            String html = "<html><head><title>Simple Http Java Server</title></head><body><h1>This page was served using my Simple Http Java Server</h1></body></html>";
            String response = "HTTP/1.1 200 OK" + CLRF // Status Line
                    + "Content-Length: " + html.getBytes().length + CLRF //Header
                    + CLRF
                    + html
                    + CLRF + CLRF;
            LOGGER.info("RESPONSE : \n" + response );
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
