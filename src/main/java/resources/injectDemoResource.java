package resources;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/injectdemo")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class injectDemoResource {

    //http://localHost:8080/apis/webapi/injectdemo/annotations:param=value
    //here ("value") will be passed to @MatrixParam("param") annotation and will be @MatrixParam("value")
    // the return will be ( matrix param value )
    // @HeaderParam("customHeaderParam") this used to access a specific http header by passing its name inside the annotation
    // @CookieParam("cookie") to access cookie values
    @GET
    @Path("annotations")
    public String getParamUsingAnnotations(@MatrixParam("param") String matrixParam
                                           ,@HeaderParam("customHeaderParam") String headerParam
                                           ,@CookieParam("name") String cookie
                                           ,@FormParam("html")String formParam ){


        return "matrix param :"+ matrixParam + "headerParam" + headerParam + "cookie Param" + cookie;

    }

    @GET
    @Path("context")
    public String getParamUsingContext(@Context UriInfo uriInfo
                                       , @Context HttpHeaders httpHeaders){
        String path = uriInfo.getAbsolutePath().toString();
        String cookie = httpHeaders.getCookies().toString();

        return  "path is :" + path + "cookie :" + cookie;
    }
}
