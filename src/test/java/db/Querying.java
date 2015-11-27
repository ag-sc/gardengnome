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
        
        System.out.println("Height:    " + memory.queryAttribute("katharinabecker","height"));             
        System.out.println("Gender:    " + memory.queryAttribute("katharinabecker","gender"));                  
        System.out.println("Birthdate: " + memory.queryAttribute("katharinabecker","birthdate"));             
        System.out.println("Age:       " + memory.queryAge("katharinabecker"));
        System.out.println("Birthday?  " + memory.hasBirthdayToday("katharinabecker"));
        
        memory.shutDown();
    }
    
}
