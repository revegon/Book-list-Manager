/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package booklistmanager;

import com.mongodb.client.MongoCollection;
import controller.Connector;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.bson.Document;

/**
 *
 * @author Xenon
 */
public class FrontController extends Controller implements Initializable{
    
    @FXML
    VBox list;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connector con = new Connector();
        con.connect();
        MongoCollection<Document> col = con.getData();
        
        for(Document doc : col.find())
        {
            int index = (int)doc.get("bookid");
            String name = (String) doc.get("Name");
            String _for = (String) doc.get("For");
            String author = (String) doc.get("Author(s)");
            Label idLabel = new Label(String.valueOf(index));
            Label nameLabel = new Label(name);
            Label forLabel = new Label(_for);
            Label authLabel = new Label(author);
            
            Button delbtn = new Button("DELETE");
            delbtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    con.delete("bookid", index);
                    changeScene("/fxml/front.fxml");
                }
            });
            
            
            Button updatebtn = new Button("UPDATE");
            updatebtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    UpdateController.index = index;
                    changeScene("/fxml/update.fxml");
                }
            });
            
            
            nameLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    DetailsController.ind = index;
                    changeScene("/fxml/details.fxml");
                }
            });
            
            idLabel.setPrefWidth(90);
            nameLabel.setPrefWidth(105);
            forLabel.setPrefWidth(110);
            authLabel.setPrefWidth(130);
            
            
            HBox cont = new HBox();
            cont.setSpacing(20);
            cont.setPadding(new Insets(5, 5, 5, 5));
            
            cont.getChildren().addAll(idLabel, nameLabel, forLabel, authLabel, delbtn, updatebtn);
            
            list.getChildren().add(cont);
        }
        
        
    }
    
    public void insetion()
    {
        changeScene("/fxml/insert.fxml");
    }
    
    public void exit()
    {
        System.exit(0);
    }
}
