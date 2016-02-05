package de.citec.sc.gardengnome.database;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.bson.Document;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author cunger
 */
public abstract class Memory {
           
    MongoClient mongoClient;
    MongoDatabase db;
    
    JSONParser json = new JSONParser();   
    ContainerFactory container = new ContainerFactory() {
        @Override
        public Map createObjectContainer() {
            return new HashMap();
        }
        @Override
        public List creatArrayContainer() {
            return new ArrayList();
        }
    };
    
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
        
        log.info(show());
    }

    public String show() {
        
        String info = "\nDB name: " + db.getName(); 
        
        info += "\nCollections: "; 
        for (String coll : db.listCollectionNames()) {
            info += "\n * " + coll;
            info += " (" + db.getCollection(coll).count() + ")";
        }
                
        return info;
    }

    public String queryDocument(String coll, String doc) {
        
        String answer;
        
        try { 
            Map map = (Map) json.parse(doc,container);
            
            Document query = new Document();
            for (Object key : map.keySet()) {
                 query.append((String) key, (String) map.get(key));
            }
            
            MongoCollection collection = db.getCollection(coll);
            FindIterable results = collection.find(query);
            
            Document result = (Document) results.first();
            answer = result.toJson();
        }
        catch (Exception e) {
            answer = null;
        }
        
        return answer;
    }
        
    public Boolean writeDocument(String coll, String doc, String creator) {
        
        try {           
            DateFormat format = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            Date date = new Date();
 
            Map map = (Map) json.parse(doc,container);
            map.put("_creator",creator);
            map.put("_timestamp",format.format(date));
                    
            MongoCollection collection = db.getCollection(coll);
            collection.insertOne(new Document(map));
            
            return true;
        } 
        catch (Exception e) {
            return false;
        }
    }
    
    public String retrieveDocument(String coll, String doc) {

        try { 
            Map map = (Map) json.parse(doc,container);
            Document query = new Document();
            for (Object key : map.keySet()) {
                query.append((String) key, (String) map.get(key));
            }

            MongoCollection collection = db.getCollection(coll);   
            FindIterable results = collection.find(query).sort(new Document("_timestamp",-1));

            return((Document) results.first()).toJson();
        } 
        catch (Exception e) {
            return "null";
        }
    }
    
    
    public void shutDown() {
        
        mongoClient.close();
    }
}
