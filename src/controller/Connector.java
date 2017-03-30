package controller;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;
import javafx.scene.control.Alert;

/**
 * Created by Xenon on 3/15/2017.
 */
public class Connector {

    MongoDatabase db;

    public void connect()
    {
        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);
            db = mongoClient.getDatabase("booklists");
            System.out.println("connected");
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("No MongoDB server found!!!");
            a.show();
            System.exit(0);
        }
    }

    public void insert(Document doc)
    {
        MongoCollection<Document> booklist = db.getCollection("booklist");
        booklist.insertOne(doc);
    }

    public void delete(String attr, Object val)
    {
        MongoCollection<Document> booklist = db.getCollection("booklist");
        booklist.deleteOne(eq(attr, val));
    }

    public void update(String attr, Object val, Document doc)
    {
        MongoCollection<Document> booklist = db.getCollection("booklist");
        booklist.updateOne(eq(attr, val), new Document("$set", doc));       
    }
    
    public MongoCollection<Document> getData()
    {
        return db.getCollection("booklist");
    }
    
    public int getIndex()
    {
        Document doc = new Document();
        MongoCollection<Document> col = db.getCollection("booklist");
        if(col.count()<=0) return 0;
        for(Document d: col.find()) doc = d;
        int j =(int) doc.get("bookid");
        return j+1;
    }
    
    
}
