/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package booklistmanager;

import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.eq;
import controller.Connector;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.bson.Document;

/**
 * FXML Controller class
 *
 * @author Xenon
 */
public class DetailsController extends Controller implements Initializable {
    
    public static int ind;
    
    @FXML
    Label nameshower, forshower, authorshower, publishshower, editionshower;
    
    @FXML
    VBox container;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Connector con = new Connector();
        con.connect();
        MongoCollection<Document> col = con.getData();
        
        Document index = col.find(eq("bookid", ind)).first();
        
        System.out.println(index.toJson());
        nameshower.setText("Name : "+(String)index.get("Name"));
        forshower.setText("For : "+(String)index.get("For"));
        if(index.containsKey("Author(s)"))authorshower.setText("Author(s) : "+(String)index.get("Author(s)"));
        else authorshower.setVisible(false);
        if(index.containsKey("Publish date"))publishshower.setText("Publish date : "+(String)index.get("Publish date"));
        else container.getChildren().remove(publishshower);
        if(index.containsKey("Edition"))editionshower.setText("Edition : "+(String)index.get("Edition"));
        else container.getChildren().remove(editionshower);
                
        
    }    
    
    
    public void done()
    {
        changeScene("/fxml/tablefront.fxml");
    }
}
