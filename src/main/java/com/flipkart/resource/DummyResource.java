package com.flipkart.resource;

import com.flipkart.hysterix.DummyCommand;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Path("/dummy/")
public class DummyResource {


    @GET
    @Path("name")
    public String getName(){
        DummyCommand command = new DummyCommand();
        Future<String> result=command.queue();
        try {
            return result.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "Interrupted exception";
    }
}