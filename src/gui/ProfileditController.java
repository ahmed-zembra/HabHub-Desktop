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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sun.security.util.Length;
import utils.Statics;

/**
 * FXML Controller class
 *
 * @author NADA_USER
 */
public class ProfileditController implements Initializable {
   

        @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    private TextField email;

    @FXML
    private TextField password;

    @FXML
    private TextField facebook;

    @FXML
    private TextField whatsapp;

    @FXML
    private TextField instagram;

  
    private Stage stage;
 private Scene scene;
 private Parent parent;
    @FXML
    void editprofile(ActionEvent event) throws IOException {
         
    Parent root = FXMLLoader.load(getClass().getResource("../gui/profile.fxml"));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();

    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     // !!password!!  String y =  Statics.currentIndividu.getUtilisateur().getPassword();
          name.setText(Statics.currentIndividu.getNom());
            surname.setText(Statics.currentIndividu.getPrenom());
              email.setText(Statics.currentIndividu.getUtilisateur().getEmail());
               password.setText("****");
                  facebook.setText(Statics.currentIndividu.getFacebook());
                    whatsapp.setText(Statics.currentIndividu.getWhatsapp());
                      instagram.setText(Statics.currentIndividu.getInstagram());
                      
    }    
    
}