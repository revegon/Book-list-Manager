package controller;



import javafx.scene.control.Button;
import org.bson.Document;

/**
 * Created by Xenon on 3/18/2017.
 */
public class Book {

    private int index;
    private int serial;
    private String name;
    private String _for;
    private String author;
    
    public Book(Document doc, int sl)
    {
        this.serial = sl;
        this.index =(int) doc.get("bookid");
        this.name = (String) doc.get("Name");
        this._for = (String) doc.get("For");
        this.author = (String) doc.get("Author(s)");
    }
    
    public int getIndex()
    {
        return this.index;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFor() {
        return _for;
    }

    public void setFor(String _for) {
        this._for = _for;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    
    
}
