package de.citec.sc.gardengnome.service;

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
       
    String           scope;
    
    Factory          factory;
    Informer<Object> informer;

    private static final Logger log = Logger.getLogger(Logger.class.getName());

    
    public Mouth(String scope) {
        
        this.scope = scope;
    }
    
    
    public void open() throws InitializeException, RSBException {
        
        factory = Factory.getInstance();
        informer = factory.createInformer(scope);
        informer.activate();
    }
    
    public void speak(String payload) throws RSBException {
                
        informer.send(payload);
    }

    public void shut() throws RSBException, InterruptedException {
        
        informer.deactivate();
    }
    
}
