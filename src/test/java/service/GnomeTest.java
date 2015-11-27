package service;

import de.citec.sc.gardengnome.service.Gnome;
import rsb.Factory;
import rsb.Informer;
import rsb.Listener;
import rsb.RSBException;
import rsb.TestEventListener;

/**
 *
 * @author cunger
 */
public class GnomeTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RSBException, InterruptedException {
                
        Gnome gnome;
        
        gnome = new Gnome();
        gnome.connectToDB("localhost",27017,"becker"); 
        gnome.activate("/kognihome/userprofiles");
               
        Factory factory = Factory.getInstance();
        Informer<Object> informer = factory.createInformer("/kognihome/userprofiles/request");
        informer.activate();
        
        Listener listener = factory.createListener("/kognihome/userprofiles/answer");       
        listener.activate();
        listener.addHandler(new TestEventListener(),true);
        
        informer.send("rsb.test");
        
        informer.send("katharinabecker.gender");
        informer.send("katharinabecker.height");
        informer.send("katharinabecker.age");
        informer.send("katharinabecker.birthday");
        
        informer.send("katharinabecker.unknownAttribute");
       
        informer.send("Some inknown input...");

        gnome.deactivate();
    }
    
}
