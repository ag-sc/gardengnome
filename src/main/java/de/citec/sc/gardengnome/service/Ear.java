package de.citec.sc.gardengnome.service;

import rsb.Factory;
import rsb.Listener;
import rsb.RSBException;


/**
 *
 * @author cunger
 */
public class Ear {
        
    Brain    brain; 
    
    String   scope;
    Factory  factory;
    Listener listener;

    
    public Ear(Brain brain, String scope) {
        
        this.brain = brain;
        this.scope = scope;
    }
        
    public void open() throws RSBException, InterruptedException {

        factory  = Factory.getInstance();        
        listener = factory.createListener(scope);       
        listener.activate();
        listener.addHandler(brain,true);
    }   
        
    public void shut() throws RSBException, InterruptedException {
        
        listener.deactivate();
    }
}
