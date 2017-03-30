/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package booklistmanager;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 *
 * @author Xenon
 */
public class BooklistManager extends Application {
    
    public static Stage window;
    
    @Override
    public void start(Stage primaryStage) {
        
        window = primaryStage;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/tablefront.fxml"));
            primaryStage.setTitle("Book List Manager");
            primaryStage.setScene(new Scene(root));
            
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("No MongoDB server found!!!");
            a.showAndWait();
            System.exit(0);
        
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
