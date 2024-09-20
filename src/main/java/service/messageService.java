package service;

import DataBase.DataBaseClass;
import Model.Message;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class messageService {

    // help us to save every instance objects of messageService to the same instance of messages(map)
    // help us to add a message in one request , update it in another request
    // handy for trying a new things
    private Map <Long , Message> messages= DataBaseClass.getMessages();



    public messageService() {
        messages.put(1L, new Message(1,"hello world","tarek arabi"));
        messages.put(2L, new Message(2,"hello jersey world","tarek arabi"));
    }

    public List <Message> getAllMessages(){
        //to return all messages values that saved in the Map we created
       return new ArrayList<Message>(messages.values());
    }

    // to filter messages by the year فلترة الرسايل باستخدام السنه
    public List <Message> getAllMessagesForYear(int year){
        List<Message> messageForYear = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        for(Message message: messages.values()){
            cal.setTime(message.getCreated());
            if (cal.get(Calendar.YEAR) == year){
                messageForYear.add(message);
            }
        }
        return messageForYear;
    }


    // to paginate the messages ترقيم الرسايل
    public List <Message> getAllMessagesPaginated(int start, int size){
        ArrayList<Message> list = new ArrayList<Message>(messages.values());
        return  list.subList(start,start+size);
    }


    public Message getMessage(Long id){
        return messages.get(id);
    }

    public Message addMessage(Message message) {
        message.setId(messages.size()+1);
        messages.put(message.getId(),message);
        return message;
    }

    public Message updateMessage(Message message) {
        if (message.getId()<=0) return null;
        messages.put(message.getId(),message);
        return message;
    }

    public Message removeMessage(Long id){
        return messages.remove(id);

    }



}
