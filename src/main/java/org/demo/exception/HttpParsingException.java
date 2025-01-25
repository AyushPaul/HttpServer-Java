package org.demo.exception;

import org.demo.http.HttpStatusCode;

public class HttpParsingException extends Exception{

    private final HttpStatusCode errorCode;

    public HttpParsingException(HttpStatusCode httpStatusCode){
        super(httpStatusCode.MESSAGE);
        this.errorCode = httpStatusCode;
    }

    public HttpStatusCode getErrorCode(){
        return errorCode;
    }
}
