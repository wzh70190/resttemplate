package com.example.resttem.rest.http;

/**
 * Created by wangxindong on 18/5/17
 */
public class RetryException  {

    private Object result;

    private Exception exception;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public void clear(){
        this.result = null;
        this.exception = null;
    }
}
