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
public class WriteHandler extends AbstractEventHandler {
    
    Gnome gnome;
    JSONParser json;
    
    private static final Logger log = Logger.getLogger(Logger.class.getName());

    
    public WriteHandler(Gnome gnome) {
        
        this.gnome = gnome;
        this.json  = new JSONParser();
    }
    
    
    @Override
    public void handleEvent(final Event event) {
                
        String data = event.getData().toString();

        log.info("Received:  " + event.toString());
        log.info("With data: " + data);
                
        String coll    = null;
        String doc     = null;
        String creator = null;
        try {
             JSONObject object = (JSONObject) json.parse(data);
             if (object.containsKey("coll"))    coll    = (String) object.get("coll");
             if (object.containsKey("creator")) creator = (String) object.get("creator");
             if (object.containsKey("doc"))     doc     = object.get("doc").toString();
        }
        catch (Exception e) {
            giveUp();
        }
        
        gnome.memory.writeDocument(coll,doc,creator);  
        
        answer("SUCCESS");
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
