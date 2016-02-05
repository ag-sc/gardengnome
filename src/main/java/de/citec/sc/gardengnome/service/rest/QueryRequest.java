package de.citec.sc.gardengnome.service.rest;

import de.citec.sc.gardengnome.service.rsb.Gnome;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 *
 * @author cunger
 */
public class QueryRequest extends ServerResource {
    
    public Gnome gnome;

            
    public QueryRequest() {
        
        gnome = new Gnome();
        gnome.connectToDB("localhost",27017,"becker"); 
    }

    @Get
    public String process(Representation entity) throws Exception {
        
        String response = "null";
        
        try {
            JSONParser json  = new JSONParser();
            JSONObject input = (JSONObject) json.parse(entity.getText());
                    
            String answer = "";
            
            String uid = null;
            String att = null;
            try {
                if (input.containsKey("uid")) uid = (String) input.get("uid");
                if (input.containsKey("ask")) att = (String) input.get("ask");
                 
                switch (att) {
                        
                    case "age":         answer += gnome.getMemory().queryAge(uid); break;
                    case "hasbirthday": answer += gnome.getMemory().hasBirthday(uid); break;
            
                    case "gender":      answer += gnome.getMemory().queryAttribute("info",uid,"gender"); break;
                    case "height":      answer += gnome.getMemory().queryAttribute("info",uid,"height"); break;
                    case "birthdate":   answer += gnome.getMemory().queryAttribute("info",uid,"birthdate"); break; 
                    
                    default: answer = "ERROR";
                }
                        
                response = "{ \"uid\" : "+uid+", \""+att+"\": \""+answer+"\" }";
            }
            catch (Exception e) {
            }
                  
        } catch (ParseException ex) {
        }
    
        return response;
    }
    
}
