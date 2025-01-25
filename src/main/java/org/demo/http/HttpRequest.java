package org.demo.http;

import org.demo.exception.HttpParsingException;

public class HttpRequest extends HttpMessage{

    private HttpMethod httpMethod;
    private String requestTarget;
    private String httpVersion;
    HttpRequest(){

    }

    public String getRequestTarget() {
        return requestTarget;
    }

    public void setRequestTarget(String requestTarget) {
        this.requestTarget = requestTarget;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    void setHttpMethod(String httpMethod) throws HttpParsingException {

        for(HttpMethod httpMethod1 : HttpMethod.values()){
            if(httpMethod1.name().equalsIgnoreCase(httpMethod)){
                this.httpMethod = httpMethod1;
                return;
            }
        }
        throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
    }
}
