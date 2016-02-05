package de.citec.sc.gardengnome.service.rsb;

import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import rsb.AbstractEventHandler;
import rsb.Event;
import rsb.RSBException;

/**
 *
 * @author cunger
 */
public class QueryHandler extends AbstractEventHandler {
    
    Gnome gnome;
    JSONParser json;
    
    private static final Logger log = Logger.getLogger(Logger.class.getName());

    
    public QueryHandler(Gnome gnome) {
        
        this.gnome = gnome;
        this.json  = new JSONParser();
    }
    
    
    @Override
    public void handleEvent(final Event event) {
                
        String data = event.getData().toString();

        log.info("Received:  " + event.toString());
        log.info("With data: " + data);
        
        // Test
        if (data.equals("test")) try { gnome.mouth.speak("Yay!"); } catch (RSBException e) { log.severe(e.getMessage()); }
        
        // Real query 
        
        String uid  = null;
        String ask  = null;
        String coll = null;
        String doc  = null;
        try {
             JSONObject object = (JSONObject) json.parse(data);
             if (object.containsKey("uid"))  uid  = (String) object.get("uid");
             if (object.containsKey("ask"))  ask  = (String) object.get("ask");
             if (object.containsKey("coll")) coll = (String) object.get("coll");
             if (object.containsKey("doc"))  doc  = (String) object.get("doc");
        }
        catch (Exception e) {
            log.severe(e.getMessage());
        }

        String payload;
                
        if (uid != null && ask != null) {
                        
            String answer = "";
            
            switch (ask) {
                        
                case "age":         answer += gnome.memory.queryAge(uid); break;
                case "hasbirthday": answer += gnome.memory.hasBirthday(uid); break;
            
                case "gender":      answer += gnome.memory.queryAttribute("info",uid,"gender"); break;
                case "height":      answer += gnome.memory.queryAttribute("info",uid,"height"); break;
                case "birthdate":   answer += gnome.memory.queryAttribute("info",uid,"birthdate"); break; 
                    
                default: answer = "ERROR";
            }
                        
            payload = "{ \"uid\" : "+uid+", \""+ask+"\": \""+answer+"\" }";
            
            answer(payload);
            return;
        } 
         
        if (coll != null && doc != null) {
                        
            payload = gnome.memory.queryDocument(coll,doc);
            
            answer(payload);
            return;
        } 
        
        giveUp();        
    }
    
    public void answer(String payload) {
        
        try {
            gnome.mouth.speak(payload);
        } 
        catch (RSBException ex) {
            log.severe(ex.getMessage());
        }
    }
    
    public void giveUp() {
        
        answer("ERROR");
    }
}
