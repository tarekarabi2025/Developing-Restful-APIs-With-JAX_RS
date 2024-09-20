package resources;


// subresource ------->  /messages/{messageId}/comments/{commentId}

import Model.Comment;
import service.CommentService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.TEXT_PLAIN)
public class CommentResource {

    private CommentService commentService  = new CommentService();

    @GET
    public List<Comment> getAllComments(@PathParam("messageId") long messageId){
        return  commentService.getAllComments(messageId) ;
    }

    @POST
    public Comment addComment(@PathParam("messageId") long messageId , Comment comment){
        return commentService.addComment(messageId,comment);

    }


    @PUT
    @Path("/{commentId}")
    public Comment updateComment (@PathParam("messageId") long messageId , @PathParam("commentId") long id , Comment comment ){
        comment.setId(id);
        return commentService.updateComment(messageId,comment);
    }

    @DELETE
    @Path("/{commentId}")
    public void deleteComment(@PathParam("messageId") long messageId ,@PathParam("commentId") long commentId){
        commentService.removeComment(messageId,commentId);
    }


    @GET
    @Path("/{commentId}")
    public Comment getComment(@PathParam("messageId") long messageId , @PathParam("commentId") long commentId){
        return commentService.getComment(messageId,commentId);

    }


/// this annotation to explain how we can go to sub resources
    @GET
    @Path("/{commentId}")
    public String subResource(@PathParam("messageId") long messageId ,@PathParam("commentId") long commentId){
        return "method to return message id :" + messageId + " for comment id :" +commentId;
    }
}
