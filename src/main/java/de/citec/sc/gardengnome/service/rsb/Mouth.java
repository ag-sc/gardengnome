package de.citec.sc.gardengnome.service.rsb;

import java.util.logging.Logger;
import rsb.Factory;
import rsb.Informer;
import rsb.InitializeException;
import rsb.RSBException;

/**
 *
 * @author cunger
 */
public class Mouth {
           
    Informer<Object> informer;

    private static final Logger log = Logger.getLogger(Logger.class.getName());

    
    public Mouth(String scope) throws InitializeException, RSBException {
        
        Factory factory = Factory.getInstance();
        informer = factory.createInformer(scope + "/answer");
        informer.activate();
        
        log.info("Sending to: '" + scope + "/answer'");
    }
    
    public void speak(String payload) throws RSBException {
                
        informer.send(payload);
    }

    public void shut() throws RSBException, InterruptedException {
        
        informer.deactivate();
    }
    
}
