package org.demo.http;


import org.demo.exception.HttpParsingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpParser.class);

    private static final int SP = 0x20; // 32
    private static final int CR = 0x0D; // 13
    private static final int LF = 0x0A; // 10

    public HttpRequest parseHttpRequest(InputStream inputStream) throws HttpParsingException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);
        HttpRequest httpRequest = new HttpRequest();
        try {
            parseRequestLine(inputStreamReader,httpRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        parseHeader(inputStreamReader,httpRequest);
        parseBody(inputStreamReader,httpRequest);
        return httpRequest;
    }

    private void parseBody(InputStreamReader inputStreamReader,HttpRequest httpRequest) {
    }

    private void parseHeader(InputStreamReader inputStreamReader, HttpRequest httpRequest) {
    }

    private void parseRequestLine(InputStreamReader inputStreamReader, HttpRequest httpRequest) throws IOException, HttpParsingException {
        int _byte;
        StringBuilder stringBuilder = new StringBuilder();
        boolean methodParsed = false;
        boolean requestTargetParsed = false;
        while((_byte = inputStreamReader.read()) >= 0){
            if(_byte == CR){
                _byte = inputStreamReader.read();
                if(_byte == LF){
                    LOGGER.info("HTTP Version : {}",stringBuilder.toString());
                    if(!methodParsed || !requestTargetParsed){
                        throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                    }
                    httpRequest.setHttpVersion(stringBuilder.toString());
                    return; // The request line is finished
                } else{
                    throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                }
            }

            if(_byte == SP){
                if(!methodParsed){
                    LOGGER.info("Method : {}",stringBuilder.toString());
                    httpRequest.setHttpMethod(stringBuilder.toString());
                    methodParsed = true;
                }else if(!requestTargetParsed){
                    LOGGER.info("Request Target : {}",stringBuilder.toString());
                    requestTargetParsed = true;
                    httpRequest.setRequestTarget(stringBuilder.toString());
                }else{
                    throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                }
                stringBuilder.delete(0,stringBuilder.length());
            }else {
                stringBuilder.append((char) _byte);
                if(!methodParsed){
                    if(stringBuilder.length() > HttpMethod.MAX_LENGTH){
                        throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
                    }
                }
            }
        }
    }
}
