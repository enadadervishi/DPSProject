package restAPI.cleaningRobots.services;

import restAPI.cleaningRobots.beans.Robot;
import restAPI.cleaningRobots.beans.Robots;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("cleaning_robots")
public class CleaningRobotsService {

    @Path("list")
    @GET
    @Produces({"application/json", "application/xml"})
    public Response getRobotsList(){
        return Response.ok(Robots.getInstance()).build();
    }

    @Path("add")
    @POST
    @Consumes({"application/json", "application/xml"})
    public Response addRobot(Robot r){
        Robots.getInstance().postRobot(r);
        return Response.ok().build();
    }

    @Path("remove")
    @DELETE
    @Consumes({"application/json", "application/xml"})
    public Response removeRobot(Robot r){
        Robots.getInstance().deleteRobot(r);
        return Response.ok().build();
    }

    @Path("get/{robot}/air_pollution_level")
    @GET
    @Produces({"application/json", "application/xml"})
    public Response getRobotAPLevels(@PathParam("robot") String name){
        String APLevel = Robots.getInstance().getAPLevelsRobot(name);
        if(APLevel!= null)
            return Response.ok(APLevel).build();
        else
            return Response.status(Response.Status.NOT_FOUND).build();
    }


}
