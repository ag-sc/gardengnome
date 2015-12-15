package db;

import de.citec.sc.gardengnome.database.PersonMemory;

/**
 *
 * @author cunger
 */
public class Querying {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        PersonMemory memory = new PersonMemory("localhost",27017);
        
        System.out.println("Height:    " + memory.queryAttribute("info","katharinabecker","height"));             
        System.out.println("Gender:    " + memory.queryAttribute("info","katharinabecker","gender"));                  
        System.out.println("Birthdate: " + memory.queryAttribute("info","katharinabecker","birthdate"));             
        System.out.println("Age:       " + memory.queryAge("katharinabecker"));
        System.out.println("Birthday?  " + memory.hasBirthday("katharinabecker"));
        
        memory.shutDown();
    }
    
}
