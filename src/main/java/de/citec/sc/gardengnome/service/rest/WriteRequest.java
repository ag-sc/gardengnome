package de.citec.sc.gardengnome.service.rest;

import de.citec.sc.gardengnome.service.rsb.Gnome;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

/**
 *
 * @author cunger
 */
public class WriteRequest extends ServerResource {
    
    public Gnome gnome;

            
    public WriteRequest() {
        
        gnome = new Gnome();
        gnome.connectToDB("localhost",27017,"becker"); 
    }

    @Post
    public boolean process(Representation entity) throws Exception {
                          
        try {
            JSONParser json  = new JSONParser();
            JSONObject input = (JSONObject) json.parse(entity.getText());
                    
            String str  = (String) input.get("string");
            String lang = (String) input.get("language");
                             
            String coll    = null;
            String doc     = null;
            String creator = null;
            try {
                if (input.containsKey("coll"))    coll    = (String) input.get("coll");
                if (input.containsKey("creator")) creator = (String) input.get("creator");
                if (input.containsKey("doc"))     doc     = input.get("doc").toString();
                 
                boolean success = gnome.getMemory().writeDocument(coll,doc,creator);  
                return  success;
            }
            catch (Exception e) {
            }
                  
        } catch (ParseException ex) {
        }
            
        return false;
    }
    
}
