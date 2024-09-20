package service;

import DataBase.DataBaseClass;
import Model.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class profileService {

    private Map<String, Profile> profiles = DataBaseClass.getProfiles();

    public profileService() {
        profiles.put("tarek arabi",new Profile(1L,"tarek arabi","tarek","arabi"));
    }

    public List<Profile> getAllProfiles(){
        return new ArrayList<Profile>(profiles.values());
    }

    public Profile getProfile(String profileName){
        return profiles.get(profileName);

    }

    public Profile addProfile(Profile profile){
        profile.setId(profiles.size()+1);
        profiles.put(profile.getProfileName(), profile);
        return profile;
    }

    public Profile updateProfile(Profile profile){
        if (profile.getProfileName().isEmpty()){
            return null;
        }
        profiles.put(profile.getProfileName(),profile);
        return profile;
    }

    public Profile removeProfile(String profileName){
        return profiles.remove(profileName);
    }
}
