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
public class RetrieveRequest extends ServerResource {
    
    public Gnome gnome;

            
    public RetrieveRequest() {
        
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
            
            String coll = null;
            String find = null;
            try {
                if (input.containsKey("coll")) coll = (String) input.get("coll");
                if (input.containsKey("find")) find = ((JSONObject) input.get("find")).toJSONString();
                
                response = gnome.getMemory().retrieveDocument(coll,find);                        
            }
            catch (Exception e) {
            }
                  
        } catch (ParseException ex) {
        }
    
        return response;
    }
    
}
