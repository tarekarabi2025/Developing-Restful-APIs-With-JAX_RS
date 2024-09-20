package resources;


import Model.Profile;
import service.profileService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

// the same concept as messageResource

@Path("/profiles")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.TEXT_PLAIN)
public class profileResource {
    private profileService profileService = new profileService();

    @GET
    public List<Profile> getAllMessages() {
        return profileService.getAllProfiles();
    }

    @GET
    @Path("/{profileName}")
    public Profile getProfile(@PathParam("profileName") String profileName){
        return profileService.getProfile(profileName);
    }

    @POST
    public Profile addProfile(Profile profile){
        return profileService.addProfile(profile);
    }

    @PUT
    @Path("/{profileName}")
    public Profile updateMessage(@PathParam("profileName") String prfileName, Profile profile){
        profile.setProfileName(prfileName);
        return profileService.updateProfile(profile);
    }

    @DELETE
    @Path("/{profileName}")
    public void deleteProfile(@PathParam("profileName") String prfileName){
        profileService.removeProfile(prfileName);
    }
}
