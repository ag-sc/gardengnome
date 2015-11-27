package db;

import de.citec.sc.gardengnome.database.Memory;
import de.citec.sc.gardengnome.database.PersonMemory;
import java.io.IOException;

/**
 *
 * @author cunger
 */
public class InitDB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        Memory memory = new PersonMemory("localhost",27017);
        
        memory.initialize("src/main/resources/data/","becker");
                
        memory.shutDown();
    }
    
}
