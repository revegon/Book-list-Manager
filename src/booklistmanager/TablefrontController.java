/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package booklistmanager;

import com.mongodb.BasicDBObject;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.eq;
import controller.Book;
import controller.Connector;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.bson.Document;

/**
 * FXML Controller class
 *
 * @author Xenon
 */
public class TablefrontController extends Controller implements Initializable {

    @FXML
    TextField searchField;
    
    @FXML
    HBox signingbox;
    
    @FXML
    VBox list;
    
    @FXML
    ChoiceBox<String> forChooser;
    
    @FXML
    TableView<Book> table;
    
    @FXML TableColumn<Book, Integer> slColumn;
    @FXML TableColumn<Book, String> nameColumn;
    @FXML TableColumn<Book, String> forColumn;
    @FXML TableColumn<Book, String> authorColumn;
    
    MongoCollection<Document> col;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        signingbox.setVisible(false);
        
        
        // table init
        slColumn.setCellValueFactory(new PropertyValueFactory<>("serial"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        forColumn.setCellValueFactory(new PropertyValueFactory<>("for"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        table.setItems(getBook());
        
        // choiceBox init
        Connector con = new Connector();
        con.connect();
         col = con.getData();
        
        DistinctIterable<String> items = col.distinct("For", String.class);
        
        forChooser.getItems().add("All");
        forChooser.setValue("All");
        for(String s: items) forChooser.getItems().add(s);
        forChooser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String s = forChooser.getSelectionModel().getSelectedItem();
                if(s.equals("All")) 
                {
                    if(forChooser.getItems().contains("Search")) forChooser.getItems().remove("Search");
                    table.setItems(getBook());
                }
                else if(s.equals("Search")) ;
                else
                {
                    if(forChooser.getItems().contains("Search")) forChooser.getItems().remove("Search");
                    table.setItems(getBook(s));
                }
            }
        });
        
    }
    
    public void insert()
    {
        changeScene("/fxml/insert.fxml");
    }
    
    public void exit()
    {
        System.exit(0);
    } 
    
    public void search()
    {
        if(searchField.getText().isEmpty()) 
        {
            errorhandle("Search Field is empty");
            return;
        }

        String text = searchField.getText();
        searchField.clear();
        
        Connector con = new Connector();
        con.connect();
        MongoCollection<Document> col = con.getData();

        BasicDBObject q = new BasicDBObject();
        q.put("Name", Pattern.compile("^"+text, Pattern.UNIX_LINES));

        forChooser.getItems().add("Search Result for " +text);
        forChooser.getSelectionModel().clearAndSelect(forChooser.getItems().size()-1);

        table.setItems(getBook(col.find(q)));
    }
    
    public void update()
    {
        Connector con = new Connector();
        con.connect();
        MongoCollection<Document> col = con.getData();
        
        Book b = table.getSelectionModel().getSelectedItem();
        if(b==null) return;
                
        UpdateController.index = b.getIndex();
        changeScene("/fxml/update.fxml");
    }
    
    public void delete()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure?");
        ButtonType yesbtn = new ButtonType("YES");
        ButtonType nobtn = new ButtonType("NO");
        
        alert.getButtonTypes().removeAll(alert.getButtonTypes());
        alert.getButtonTypes().addAll(yesbtn, nobtn);
        
        Optional<ButtonType> res = alert.showAndWait();
        
        if(res.get() == yesbtn)
        {
        Connector con = new Connector();
        con.connect();
        MongoCollection<Document> col = con.getData();
        
        ObservableList<Book> booksSelected;
        booksSelected = table.getSelectionModel().getSelectedItems();
        if(booksSelected==null) return;
        for(Book b : booksSelected)
        {
            int index = b.getIndex();
            col.deleteOne(eq("bookid", index));
        }
        
            changeScene("/fxml/tablefront.fxml");
        }
        
        else return;
    }
    
    public void details()
    {
        Book b = table.getSelectionModel().getSelectedItem();
        if(b==null) return;
        DetailsController.ind = b.getIndex();
        changeScene("/fxml/details.fxml");
    }
    
    public void login()
    {
        
    }
    
    public void signup()
    {
        
    }
    
    public ObservableList<Book> getBook()
    {
        ObservableList<Book> books = FXCollections.observableArrayList();
        
        Connector con = new Connector();
        con.connect();
        MongoCollection<Document> col = con.getData();
        int sl =1;
        for(Document d : col.find())
        {
            Book b = new Book(d, sl++);
            books.add(b);
        }
        return books;
    }
    
    public ObservableList<Book> getBook(String s)
    {
        ObservableList<Book> books = FXCollections.observableArrayList();
        
        Connector con = new Connector();
        con.connect();
        MongoCollection<Document> col = con.getData();
        int sl =1;
        for(Document d : col.find(eq("For", s)))
        {
            Book b = new Book(d, sl++);
            books.add(b);
        }
        return books;
    }
    
    public ObservableList<Book> getBook(FindIterable<Document> res)
    {
        ObservableList<Book> books = FXCollections.observableArrayList();
        
        int sl =1;
        for(Document d : res)
        {
            Book b = new Book(d, sl++);
            books.add(b);
        }
        return books;
    }
}
