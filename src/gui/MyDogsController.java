/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ed
 */
public class MyDogsController implements Initializable {

    /**
     * Initializes the controller class.
     */
     private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    public void switchSceneDogsMatchup (ActionEvent event) throws IOException
    {
       root = FXMLLoader.load(getClass().getResource("DogsMatchup.fxml"));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    }
     @FXML
    public void switchSceneDogsNextDoor (ActionEvent event) throws IOException
    {
       root = FXMLLoader.load(getClass().getResource("DogsNextDoor.fxml"));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    }
     @FXML
    public void switchSceneMissingDogs (ActionEvent event) throws IOException
    {
    root = FXMLLoader.load(getClass().getResource("MissingDogs.fxml"));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
