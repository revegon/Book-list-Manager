/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package booklistmanager;

import controller.Connector;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.bson.Document;

/**
 * FXML Controller class
 *
 * @author Xenon
 */
public class InsertController extends Controller implements Initializable {
    
    ArrayList<HBox> lcList; 
    
    @FXML 
    VBox list;
    
    @FXML
    TextField nameField, forField, authorsField, publishField, editionField;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lcList = new ArrayList<>();
    } 
    
    public void back()
    {
        changeScene("/fxml/tablefront.fxml");
    }
    
    
    
    public void getFieldData()
    {
        Document doc = new Document();
        
        
        if(!nameField.getText().isEmpty()) doc.append("Name", nameField.getText());
        else
        {
            errorhandle(" * fields must be filled");
            return;
        }
        if(!forField.getText().isEmpty()) doc.append("For", forField.getText());
        else
        {
            errorhandle(" * fields must be filled");
            return;
        }
        if(!authorsField.getText().isEmpty()) doc.append("Author(s)", authorsField.getText());
        else
        {
            errorhandle(" * fields must be filled");
            return;
        }
        if(!publishField.getText().isEmpty())doc.append("Publish date", publishField.getText());
        if(!editionField.getText().isEmpty()) doc.append("Edition", editionField.getText());
        
        Connector con = new Connector();
        con.connect();
        int index = con.getIndex();
        doc.append("bookid", index);
        con.insert(doc);
        
        
        errorhandle("Data inserted");
        nameField.clear();
        forField.clear();
        authorsField.clear();
        publishField.clear();
        editionField.clear();
        
    }
    
    
    
}
