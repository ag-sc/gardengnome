package db;

import de.citec.sc.gardengnome.database.PersonMemory;
import java.io.IOException;

/**
 *
 * @author cunger
 */
public class Writing {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        PersonMemory memory = new PersonMemory("localhost",27017);
        memory.setDB("becker");
        
        memory.writeDocument("test","{ \"data\" : \"test\" }","testcreator");
        
        memory.show();
        
        memory.shutDown();
    }
    
}
