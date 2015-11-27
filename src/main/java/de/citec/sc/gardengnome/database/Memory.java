package de.citec.sc.gardengnome.database;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author cunger
 */
public abstract class Memory {
           
    MongoClient mongoClient;
    MongoDatabase db;
    
    private static final Logger log = Logger.getLogger(Logger.class.getName());
    
    
    public Memory(String db_host, int db_port) {

        mongoClient = new MongoClient(db_host,db_port);        
    }

    
    public void setDB(String db_name) {
        
        db = mongoClient.getDatabase(db_name);
    }
    
    public MongoDatabase getDB(String db_name) {
        
        return mongoClient.getDatabase(db_name);
    }
   
    
    public void initialize(String data_path, String db_name) throws IOException {

        // Initialize empty database

        db = mongoClient.getDatabase(db_name);
        db.drop();
        db = mongoClient.getDatabase(db_name);
        
        // Load data 

        String[] extensions = { "json" };

        log.info("Loading data from: " + data_path + db_name); // DEBUG
        
        for (File dir : FileUtils.listFilesAndDirs(new File(data_path + db_name),FalseFileFilter.INSTANCE, DirectoryFileFilter.DIRECTORY)) {
                
            String collection_name = dir.getName();
            db.createCollection(collection_name);
            MongoCollection coll = db.getCollection(collection_name);
                
            for (File f : FileUtils.listFiles(dir,extensions,false)) {
                    
                 Document doc = Document.parse(FileUtils.readFileToString(f));
                     
                 coll.insertOne(doc);
            }
        }
        
        // Infer data 
        
        // TODO
        
        // Done 
        
        show();
    }

    public void show() {
        
        String info = "\nDB name: " + db.getName() + "\nCollections: "; 
        for (String coll : db.listCollectionNames()) info += "\n * " + coll;
        
        log.info(info);
    }

    
    public String query(String collection_name, String query) {
                
        MongoCollection collection = db.getCollection(collection_name);
        
	FindIterable results = collection.find((Bson) JSON.parse(query));
        
               
        
        return "Fnord!";
    }
    
    public void shutDown() {
        
        mongoClient.close();
    }
}
