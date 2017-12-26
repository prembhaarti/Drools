package com.flipkart.filter;

import com.flipkart.beans.request.RequestContainer;
import com.flipkart.beans.request.RequestContext;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BadRequestException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class RequestFilter implements Filter {
    public static final String X_CLIENT_ID="X-CLIENT-ID";
    public static final String X_CONTEXT="X-CLIENT-CONTEXT";
    public static final String IS_TEST_REQUEST="IS_TEST_REQUEST";

    private final String serverIp;

    private FilterConfig filterConfig;

    public RequestFilter(){
        try {
            serverIp= InetAddress.getLocalHost().getHostAddress().replaceAll("\\.", "");
        } catch (UnknownHostException e) {
            throw new RuntimeException("Unable to get Server ip");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig=filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest= (HttpServletRequest) request;
        String clientId=httpServletRequest.getHeader(X_CLIENT_ID);
        String context=httpServletRequest.getHeader(X_CONTEXT);
        String traceId = String.format("T-%d-%d-%s", System.nanoTime(), Thread.currentThread().getId(), serverIp);
        boolean isTestRequest=Boolean.parseBoolean(httpServletRequest.getHeader(IS_TEST_REQUEST));
        if (StringUtils.isBlank(clientId)) {
            throw new BadRequestException();
        }
        RequestContext requestContext= new RequestContext(clientId,context,traceId,false);
        RequestContainer.setRequestContext(requestContext);
        try{
            System.out.println("RequestContext: "+RequestContainer.getRequestContext());
            chain.doFilter(request,response);
        }
        finally {
            RequestContainer.destroyRequestContext();
        }
    }

    @Override
    public void destroy() {

    }
}
