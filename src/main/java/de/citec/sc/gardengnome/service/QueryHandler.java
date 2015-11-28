package de.citec.sc.gardengnome.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import rsb.AbstractEventHandler;
import rsb.Event;
import rsb.RSBException;

/**
 *
 * @author cunger
 */
public class QueryHandler extends AbstractEventHandler {
    
    Gnome gnome;
    
    private static final Logger log = Logger.getLogger(Logger.class.getName());

    
    public QueryHandler(Gnome gnome) {
        
        this.gnome = gnome;
    }
    
    
    @Override
    public void handleEvent(final Event event) {
                
        String data = event.getData().toString();

        log.info("Received:  " + event.toString());
        log.info("With data: " + data);
        
        if (!data.matches("[^.]+\\.[^.]+")) { 
            // TODO more serious checking
            log.warning("Don't know how to handle the data!");
            return;
        } 
        
        String[] parts = data.split("\\.");
        String uid = parts[0];
        String att = parts[1];
        
        String payload = "";
        
        switch (att) {
            
            case "test":     payload += "Yay!"; break;
            
            case "age":      payload += gnome.memory.queryAge(uid); break;
            case "birthday": payload += gnome.memory.hasBirthdayToday(uid); break;
            
            default:         payload += gnome.memory.queryAttribute(uid,att);
        }
        
        try {
            gnome.mouth.speak(payload);
        } catch (RSBException ex) {
            Logger.getLogger(QueryHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
