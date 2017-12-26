package com.flipkart.hysterix;

import com.netflix.hystrix.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class DummyCommand extends HystrixCommand<String> {
    private static final String DUMMY_GROUP="dummy_group";
    private static final String DUMMY_THREAD_POOL_GROUP="dummy_thread_pool_group";
    private static final String DUMMY_URL="http://www.google.com";
    private static final int EXECUTION_TIMEOUT=2000;

    public DummyCommand() {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(DUMMY_GROUP))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(EXECUTION_TIMEOUT))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(DUMMY_THREAD_POOL_GROUP))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(150)
                        .withMetricsRollingStatisticalWindowInMilliseconds(10000)
                        .withMetricsRollingStatisticalWindowBuckets(10)));
    }

    @Override
    protected String getFallback() {
        return "Unable to connect to url";
    }

    @Override
    protected String run() {
        try {
            return callURL(DUMMY_URL,3000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("xyz");
    }

    private static String callURL(String myURL,int timeout) throws IOException {
        System.out.println("Requeted URL:" + myURL);
        StringBuilder sb = new StringBuilder();
        URLConnection urlConn = null;
        InputStreamReader in = null;
        //try {
            URL url = new URL(myURL);
            urlConn = url.openConnection();
            if (urlConn != null)
                urlConn.setReadTimeout(timeout);
            if (urlConn != null && urlConn.getInputStream() != null) {
                in = new InputStreamReader(urlConn.getInputStream(),
                        Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(in);
                if (bufferedReader != null) {
                    int cp;
                    while ((cp = bufferedReader.read()) != -1) {
                        sb.append((char) cp);
                    }
                    bufferedReader.close();
                }
            }
            in.close();
       // } catch (Exception e) {
         //   throw new RuntimeException("Exception while calling URL:"+ myURL, e);
        //}

        return sb.toString();
    }

   /* public static void main(String[] args) {
        try {
            System.out.println(callURL("http://www.google.com",30000));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
