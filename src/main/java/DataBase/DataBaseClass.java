package DataBase;

import Model.Message;
import Model.Profile;

import java.util.HashMap;
import java.util.Map;


public class DataBaseClass {

    // these static Maps created to act like a database and this case not correct scenario in companies and not safe
    // to learn jersey only and practise
    private static Map<Long, Message> messages= new HashMap<>();
    private static Map<String, Profile> profiles= new HashMap<>();

  /*this method (get) in this case take every instance that we made
  and save it in the map by using messages.add() in the other classes*/
    public static Map<Long, Message> getMessages() {
        return messages;
    }

    public static Map<String, Profile> getProfiles() {
        return profiles;
    }
}
