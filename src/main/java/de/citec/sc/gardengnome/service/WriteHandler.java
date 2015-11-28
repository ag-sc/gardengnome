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
public class WriteHandler extends AbstractEventHandler {
    
    Gnome gnome;
    
    private static final Logger log = Logger.getLogger(Logger.class.getName());

    
    public WriteHandler(Gnome gnome) {
        
        this.gnome = gnome;
    }
    
    
    @Override
    public void handleEvent(final Event event) {
                
        String data = event.getData().toString();

        log.info("Received:  " + event.toString());
        log.info("With data: " + data);
        
        // checking of data
        
        // writing sent document to database (into appropriate collection)
        
    }
}
