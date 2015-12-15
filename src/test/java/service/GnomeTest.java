package service;

import de.citec.sc.gardengnome.service.Gnome;
import java.io.IOException;
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
    public static void main(String[] args) throws RSBException, InterruptedException, IOException {
                
        Gnome gnome;
        
        gnome = new Gnome();
        gnome.connectToDB("localhost",27017,"becker"); 
        gnome.activate("/kognihome/userprofiles");
        
        Factory factory = Factory.getInstance();
        Informer<Object> informer = factory.createInformer("/kognihome/userprofiles/query");
        informer.activate();
        
        Informer<Object> creator = factory.createInformer("/kognihome/userprofiles/write");
        creator.activate();

        Listener listener = factory.createListener("/kognihome/userprofiles/answer");       
        listener.activate();
        listener.addHandler(new TestEventListener(),true);
        
        informer.send("test");
        
        informer.send("{ \"uid\": \"katharinabecker\", \"ask\": \"gender\" }");
        informer.send("{ \"uid\": \"katharinabecker\", \"ask\": \"height\" }");
        informer.send("{ \"uid\": \"katharinabecker\", \"ask\": \"age\" }");
        informer.send("{ \"uid\": \"katharinabecker\", \"ask\": \"birthdate\" }");
        informer.send("{ \"uid\": \"katharinabecker\", \"ask\": \"hasbirthday\" }");
        informer.send("{ \"uid\": \"katharinabecker\", \"ask\": \"unknown attribute\" }");

        informer.send("Some inknown input...");
        
        creator.send("{ \"creator\" : \"testcreator\", \"coll\" : \"test\", \"doc\" : " + activityData() + " }");
               
        informer.send("{ \"coll\" : \"test\", \"doc\" : { \"uid\" : \"alexanderbecker\" , \"date\" : \"2015-12-15\" } }");

        gnome.deactivate();
    }
    
    static private String activityData() {
        
        return 
        "{\n" +
        "    \"date\" : \"2015-12-15\",\n" +
        "    \n" +
        "    \"uid\": \"alexanderbecker\",\n" +
        "        \n" +
        "    \"energyrequirement\" : 2000,\n" +
        "    \"activitylevel\": 3 ,\n" +
        "    \n" +
        "    \"activities\" : [ { \"type\" : \"cycling\",\n" +
        "                       \"duration\" : 30,\n" +
        "                       \"intensity\" : 60 }, \n" +
        "                     { \"type\" : \"walking\",\n" +
        "                       \"duration\" : 20,\n" +
        "                       \"intensity\" : 40 },\n" +
        "                     { \"type\" : \"strength training\",\n" +
        "                       \"duration\" : 40,\n" +
        "                       \"intensity\" : 80 }\n" +
        "                   ] , \n" +
        "    \n" +
        "    \"biometrics\" : { \"weight\" : 60,\n" +
        "                     \"bodyfat\": 22,\n" +
        "                     \"bodywater\": 51, \n" +
        "                     \"musclemass\": 20 }\n" +
        "}";
    }
    
}
