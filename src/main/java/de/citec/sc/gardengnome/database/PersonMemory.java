package de.citec.sc.gardengnome.database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import java.util.Calendar;
import java.util.logging.Logger;
import org.bson.Document;

/**
 *
 * @author cunger
 */
public class PersonMemory extends Memory {
 
    private static final Logger log = Logger.getLogger(Logger.class.getName());
    
    
    public PersonMemory(String db_host, int db_port) {
        super(db_host, db_port);
    }

        
    /*  
    /* Get a particular attribute of person (uid) as string,  
    /* e.g. birthdate, gender, height.
    */
    public String queryAttribute(String coll, String uid, final String attribute) {
               
        String answer;
        
        try {
            MongoCollection collection = db.getCollection(coll);       
            FindIterable results = collection.find(new Document("uid",uid));
            
            answer = ((Document) results.first()).get(attribute).toString();
        }
        catch (Exception e) {
            answer = "ERROR";
            log.warning("Querying for '" + attribute + "' of '" + uid + "' failed.");
        }
                
        return answer;
    }
    
    // Custom queries 
    
    public int queryAge(String uid) {
        
        String birthdate = queryAttribute("info",uid,"birthdate");
        
        String[] parts = birthdate.split("-");       
        int year  = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day   = Integer.parseInt(parts[2]);
        
        Calendar now = Calendar.getInstance();
        int now_year  = now.get(Calendar.YEAR);
        int now_month = now.get(Calendar.MONTH) + 1;
        int now_day   = now.get(Calendar.DAY_OF_MONTH);

        int age = now_year - year;
        if ((month < now_month) || (month == now_month && day < now_day)) age -= 1;
        
        return age;
    }

    public boolean hasBirthday(String uid) {
        
        String birthdate = queryAttribute("info",uid,"birthdate");
        
        String[] parts = birthdate.split("-");       
        int month = Integer.parseInt(parts[1]);
        int day   = Integer.parseInt(parts[2]);
        
        Calendar now = Calendar.getInstance();
        int now_month = now.get(Calendar.MONTH) + 1;
        int now_day   = now.get(Calendar.DAY_OF_MONTH);

        return (month == now_month && day == now_day);
    }
    
    // getRelations(String uid1, String uid2)

    // getStatus(String uid) 
    // hasAccessTo(String uid, String device_id), or: accessLevel(String uid, String device_id)
    // getDevices(String uid)
    
}
