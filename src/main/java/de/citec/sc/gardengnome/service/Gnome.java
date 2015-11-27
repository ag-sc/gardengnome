package de.citec.sc.gardengnome.service;

import de.citec.sc.gardengnome.database.PersonMemory;
import java.io.IOException;
import java.util.logging.Logger;
import rsb.RSBException;

/**
 *
 * @author cunger
 */
public class Gnome {
    
    Brain brain;   
    Ear   ear;
    Mouth mouth;
    PersonMemory memory;

    private static final Logger log = Logger.getLogger(Logger.class.getName());

    
    public Gnome() {

        brain = new Brain(this);
    }
    
    public void connectToDB(String db_host, int db_port, String db_name) {
        
        log.info("Connecting to database (host: " + db_host + ", port: " + db_port + ")...");
        
        memory = new PersonMemory(db_host,db_port);
        memory.setDB(db_name);
        
        log.info("Done. Active database: " + db_name);
    }   
    
    public void activate(String rsb_scope) throws RSBException, InterruptedException {
        
        log.info("Waking up...");
        
        ear = new Ear(brain,rsb_scope + "/request");
        ear.open();

        mouth = new Mouth(rsb_scope + "/answer");
        mouth.open();
        
        log.info("Ready.\n Listening to: '" + rsb_scope + "/request' \n Sending to:   '" + rsb_scope + "/answer'");
    }
    
    public void factoryReset(String data_path, String db_name) throws IOException {
        
        memory.initialize(data_path,db_name);
    }
    
    public boolean selfTest() throws RSBException {
        
        say("rsb.test");
        
        return true;
    }
    
    public void say(String payload) throws RSBException {
        
        mouth.speak(payload);
    }
    
    public void deactivate() throws RSBException, InterruptedException {
        
        ear.shut();
        mouth.shut();
        memory.shutDown();
    }
}
