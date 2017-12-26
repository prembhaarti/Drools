package com.flipkart.beans.request;

public class RequestContainer {

    private final static InheritableThreadLocal<RequestContext> REQUEST_CONTEXT=  new InheritableThreadLocal<RequestContext>(){
        @Override
        protected RequestContext initialValue() {
            return new RequestContext("","","",false);
        }
    };

    public static RequestContext getRequestContext() {
        return REQUEST_CONTEXT.get();
    }

    public static void setRequestContext(RequestContext requestContext) {
        REQUEST_CONTEXT.set(requestContext);
    }

    public static void destroyRequestContext() {
        REQUEST_CONTEXT.remove();
    }

}
