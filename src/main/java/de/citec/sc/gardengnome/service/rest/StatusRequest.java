package de.citec.sc.gardengnome.service.rest;

import de.citec.sc.gardengnome.service.rsb.Gnome;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 *
 * @author cunger
 */
public class StatusRequest extends ServerResource {
    
    public Gnome gnome;

            
    public StatusRequest() {
        
        gnome = new Gnome();
        gnome.connectToDB("localhost",27017,"becker"); 
    }

    @Get
    public String process(Representation entity) throws Exception {
        
        return gnome.getMemory().show();
    }
    
}
