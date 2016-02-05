package de.citec.sc.gardengnome.service.rsb;

import java.util.logging.Logger;
import rsb.Factory;
import rsb.Listener;
import rsb.RSBException;


/**
 *
 * @author cunger
 */
public class Ears {
        
    Listener queryListener;
    Listener writeListener;
    
    private static final Logger log = Logger.getLogger(Logger.class.getName());

    
    public Ears(String scope, QueryHandler queryHandler, WriteHandler writeHandler) throws RSBException, InterruptedException {

        Factory factory  = Factory.getInstance();        
        
        queryListener = factory.createListener(scope + "/query");       
        queryListener.activate();
        queryListener.addHandler(queryHandler,true);
        
        writeListener = factory.createListener(scope + "/write");       
        writeListener.activate();
        writeListener.addHandler(writeHandler,true);
        
        log.info("Listening to: '" + scope + "/query' and '" + scope + "/write'");
    }   
        
    public void shut() throws RSBException, InterruptedException {
        
        queryListener.deactivate();
        writeListener.deactivate();
    }
}
