package de.citec.sc.gardengnome.service.rsb;

import de.citec.sc.gardengnome.database.PersonMemory;
import java.io.IOException;
import java.util.logging.Logger;
import rsb.RSBException;

/**
 *
 * @author cunger
 */
public class Gnome {
    
    Ears  ears;
    Mouth mouth;
    
    PersonMemory memory;

    private static final Logger log = Logger.getLogger(Logger.class.getName());

    
    public Gnome() {
    }
    
    
    public void connectToDB(String db_host, int db_port, String db_name) {
                
        memory = new PersonMemory(db_host,db_port);
        memory.setDB(db_name);
        
        log.info("Connected to database '" + db_name +"' (host: " + db_host + ", port: " + db_port + ").");
    }   
        
    public void activate(String rsb_scope) throws RSBException, InterruptedException {
                
        ears  = new Ears(rsb_scope,new QueryHandler(this),new WriteHandler(this));
        mouth = new Mouth(rsb_scope);
    }
    
    public boolean selfTest() throws RSBException {
        
        say("rsb.test");
        
        return true;
    }
    
    public PersonMemory getMemory() {
        return memory;
    }
            
    public void factoryReset(String data_path, String db_name) throws IOException {
        
        memory.initialize(data_path,db_name);
    }
    
    
    public void say(String payload) throws RSBException {
        
        mouth.speak(payload);
    }
    
    public void deactivate() throws RSBException, InterruptedException {
        
        ears.shut();
        mouth.shut();
        memory.shutDown();
    }
}
