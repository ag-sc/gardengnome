package de.citec.sc.gardengnome;

import de.citec.sc.gardengnome.service.Gnome;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import rsb.RSBException;

/**
 *
 * @author cunger
 */
public class Start {
    
    
    static String config_file = "config.json";

    static String rsb_scope;
    static String db_host;
    static int    db_port;
    static String db_name;
    static String data_path;
    
    static Gnome  gnome;
    
    private static final Logger log = Logger.getLogger(Logger.class.getName());


    /**
     * Connects to database and starts RSB service. 
     * In "factoryreset" mode, it will also build the database from scratch. 
     * (Previously stored data will be lost.)
     * @param  string argument "factoryreset" (optional)
     * @throws java.io.IOException
     * @throws rsb.RSBException
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws IOException, RSBException, InterruptedException {
                        
        log.info("Waking up...");
    
        readConfig();
        
        try {
            gnome = new Gnome();
            gnome.connectToDB(db_host,db_port,db_name);
            gnome.activate(rsb_scope);

            // If "factoryreset" is provided as argument, then initialize database 
            
            if (args.length == 1 && args[0].equals("factoryreset")) {
                
                Scanner scanner = new Scanner(System.in);

                log.warning("I'm about to initialize the database '" + db_name + "'. This means deleting all its contents! "
                          + "Are you sure you want to continue? y/n: ");
        
                String response = scanner.nextLine();
                if (!response.equals("y")) {
                    log.info("Aborted.");
                    System.exit(0);
                }
                
                gnome.factoryReset(data_path,db_name);
            }
            
            log.info("Ready.");
            
        //  while (true) { Thread.sleep(1); }
        }
        catch (Exception e) {
            
            gnome.deactivate();
            System.exit(1);
        } 
    }

    
    private static void readConfig() throws IOException {
        
        File file = new File(config_file);
        JSONParser json  = new JSONParser();
        
        try {
             
            InputStream in = new FileInputStream(file);    
            String content = IOUtils.toString(in);
            
            JSONObject config = (JSONObject) json.parse(content);     
          
            rsb_scope = (String) config.get("rsb_scope");
            db_host   = (String) config.get("db_host");
            db_port   = (int)    config.get("db_port");
            db_name   = (String) config.get("db_name");
            data_path = (String) config.get("data_path");

        }
        catch (FileNotFoundException e) {               
            throw new IOException("Config file '" + config_file + "' not found.");
        }
        catch (ParseException e) {
            throw new IOException("Config file '" + config_file + "' could not be read (it is not well-formed JSON).");
        }   
    }
    
}
