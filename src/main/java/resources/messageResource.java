package resources;

import Model.Message;
import service.messageService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Path("/messages")
/* by putting these annotations here
it means that these annotations for all APIS in the class and make the code clean and readable
no need to write these annotations at the bottom,but I left them to explain how annotations works */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(value = {MediaType.TEXT_XML,MediaType.APPLICATION_JSON}) // this show how we can support multiple content types
public class messageResource {
    // GET APIS
    //API to preview data for the client in webb app ,Read operation in CRUD
    messageService messageService = new messageService();

    /* we will put more than one query param in one method
     and use IF condition to specify if we want to review all messages or by filtering*/

    /* this @QueryParam("value which we want to map") annotation
     used for filtering messages by pathing the year or paginated or anything we want to filter messages by it */

    //query params
    //http://localHost:8080/apis/webapi/messages?param=value
    //http://localHost:8080/apis/webapi/messages?year=15   the shape of year param in url for one value
    //http://localHost:8080/apis/webapi/messages?start=0&size=1  the shape of start and size params in url for two values
    // here we took all params and put them in one class and use setters and getters and make instance object as parameter to the method and get all values we want
    // NOTICE : we cant do this with @PathParam: Used to inject values from the URI path directly into resource method parameters in JAX-RS.
    //@BeanParam: Used to aggregate multiple parameters into a single bean and inject them into a resource method.
    @GET
    public List<Message> getMessages(@BeanParam MessageFilterBean filterBean) {
        if (filterBean.getYear() >0){
            return messageService.getAllMessagesForYear(filterBean.getYear());
        }
        if ( filterBean.getStart() >= 0 && filterBean.getSize() > 0 ){
            return messageService.getAllMessagesPaginated(filterBean.getStart(),filterBean.getSize());
        }
        return messageService.getAllMessages();
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // API  to access a specific parameter in a database by passing the primary key in the url
    //http://localHost:8080/apis/webapi/messages/2
    // in our case "/{messageId}" variable will take 2 as the value and get the specific data on database
    @GET
    @Path("/{messageId}")
    /* jersey in this case helped us
     to convert the type of id by writing the type we want to convert (Long) in our case easily
     */
    public Message getMessage(@PathParam("messageId") Long id,@Context UriInfo uriInfo){
        Message message = messageService.getMessage(id);
        // HATEOAS : Hypermedia as the engine of application state
        // this example explain how we can create Hateoas
        message.addLink( getUriForSelf(uriInfo, message),"self");
        message.addLink( getUriForProfile(uriInfo, message),"profile");
        message.addLink( getUriForComment(uriInfo, message),"comment");
        return message;
    }
    // method to add comment link in the message body response
    private String getUriForComment(UriInfo uriInfo, Message message) {
        URI uri = uriInfo.getBaseUriBuilder().path(messageResource.class).path(messageResource.class,"getCommentResource")
                .path(CommentResource.class).resolveTemplate("messageId",message.getId()).build();
        return uri.toString();
    }

    // method to add profile name link in the message body response
    private String getUriForProfile(UriInfo uriInfo, Message message) {
        URI uri = uriInfo.getBaseUriBuilder().path(profileResource.class).path(message.getAuthor()).build();
        return uri.toString();
    }
    // method to add link for the message itself in the message body response
    private static String getUriForSelf(UriInfo uriInfo, Message message) {
        String uri = uriInfo.getBaseUriBuilder().path(messageResource.class).path(Long.toString(message.getId())).build().toString();
        return uri;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // POST API
    // create a new message to the database
    // create a new resource
    @POST
    @Consumes(MediaType.APPLICATION_JSON) // this annotation helps method accept a request in  json type
    @Produces(MediaType.TEXT_PLAIN)      /* this annotation helps method to response
     a specific type we can change it to json too */
    public Message addMessage(Message message){
        return messageService.addMessage(message);
    }
 ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    // HERE : we use Response class which implements AutoCloseable interface
    // both help us to control many things in  HTTP response like (status code , header , cookies ,,,and more )
    // .build() locates at the end of implementation after we specify what we want to do in the HTTP response and then use build
    //video number 26  -----> java brains

    //we can also build our uri using @Context and UriInfo interface as shown below

    @POST
    public Response addMessage(Message message , @Context UriInfo uriInfo){
        Message newMessage = messageService.addMessage(message);
        String newId = String.valueOf(newMessage.getId());
        URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
        return  Response.created(uri)
                              .entity(newMessage)
                                .build();
    }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // PUT API
    // to update a specific message
    // to update a specific resource
    /* @Path("/{messageId}" , /{messageId} is a variable that holds data from the http request
     and pass it as an argument in the method
      by @PathParam("messageId") annotation to access a specific value in the database*/


    @PUT
    @Path("/{messageId}") // to map to a specific message by the ID to update it
    public Message updateMessage(@PathParam("messageId") Long id, Message message){
        message.setId(id);
        return messageService.updateMessage( message);
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    // Delete API
    // to delete a specific message by the ID
    // to delete a specific resource by @pathParam annotation
    // it doesn't need consumes annotation because delete doesn't accept anything in request body
    @DELETE
    @Path("/{messageId}")
    public void deleteMessage(@PathParam("messageId") Long id){
        messageService.removeMessage(id);
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // this annotation helps us to access sub resource to do what we want as shown in CommentResource

    @Path("/{messageId}/comments")
    public CommentResource getCommentResource(){
        return new CommentResource();
    }





}


