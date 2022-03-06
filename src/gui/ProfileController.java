/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entite.Individu;
import entite.Utilisateur;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.UserIndividuServices;
import services.UtilisateurService;
import utils.Statics;

/**
 * FXML Controller class
 *
 * @author Ed
 */
public class ProfileController implements Initializable {
    @FXML
    private TextField name;
   
 @FXML
    private TextField surname;
    @FXML
    private TextField email;

    @FXML
    private TextField numtel;

    @FXML
    private TextField facebook;

    @FXML
    private TextField whatsapp;

    @FXML
    private TextField instagram;

    @FXML
    private TextField password;

    @FXML
    private TextField password1;

    @FXML
    private Button cancel;

    @FXML
    private Button save;
    
   private Stage stage;
 private Scene scene;
 private Parent parent;
 
 
    @FXML
    void cancelbutton(ActionEvent event) throws IOException {
       
    Parent root = FXMLLoader.load(getClass().getResource("../gui/profiledit.fxml"));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
    }


        @FXML
    void savebutton(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("../gui/profiledit.fxml"));
        
        
                  Alert alert = new Alert(Alert.AlertType.INFORMATION);
            
                  alert.setTitle("Success");
          UserIndividuServices h = new UserIndividuServices();
            UtilisateurService us = new UtilisateurService();
            
            Utilisateur U = new Utilisateur();
             Individu p = new Individu();
              

            U.setEmail(email.getText());
                 U.setPassword(password.getText());
                 U.setNumTel(  Integer.parseInt(numtel.getText()));
                 U.setType("individu");
                 p.setNom(name.getText());
           // h.Update(0, U, nom, prenom, dateNaissance, sexe, adresse, facebook, instagram, whatsapp)
            h.Update(0, U, "7", "o", "u", "j", "", "facebook", "instagram"," whatsapp");
       
            cancelbutton(event);
          
                      name.setText(Statics.currentIndividu.getNom());
            surname.setText(Statics.currentIndividu.getPrenom());
              email.setText(Statics.currentIndividu.getUtilisateur().getEmail());
             password.setText("****");
                  facebook.setText(Statics.currentIndividu.getFacebook());
                    whatsapp.setText(Statics.currentIndividu.getWhatsapp());
                      instagram.setText(Statics.currentIndividu.getInstagram());
         
        
           }
       
                 
            

   


 @FXML
    private Button editnom;


 @FXML
    private Button editprenom;
    @FXML
    private Button editmail;


    @FXML
    private Button editnumtel;


    @FXML
    private Button editfb;

   

    @FXML
    private Button editwhatsapp;

 

    @FXML
    private Button editinstagram;

   

    @FXML
    private Button editpassword;
    

      @FXML
 void editname(ActionEvent event) {  
      
      name.setEditable(true);
    
 }
       @FXML
 void editsurname(ActionEvent event) {  
      
     
      surname.setEditable(true);
 }
 @FXML
  void editemail(ActionEvent event) {  
      
      email.setEditable(true);
      
 }
  @FXML
  void editnumtel(ActionEvent event) {  
      
      numtel.setEditable(true);
   
 } 
  @FXML
 void editfacebook(ActionEvent event) {  
      
      facebook.setEditable(true);
  
 }
 @FXML
 void editwhatsapp(ActionEvent event) {  
      
      whatsapp.setEditable(true);
  
 }
 @FXML
 void editinstagram(ActionEvent event) {  
      
      instagram.setEditable(true);
   
 }
 @FXML
 void editpassword(ActionEvent event) {  
      
      password.setEditable(true);
 
 }
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           
//convertToInterger
       name.setText(Statics.currentIndividu.getNom());
        surname.setText(Statics.currentIndividu.getPrenom());
              email.setText(Statics.currentIndividu.getUtilisateur().getEmail());
              numtel.setText(String.valueOf(Statics.currentIndividu.getUtilisateur().getNumTel()));
                password.setText("****");
                  facebook.setText(Statics.currentIndividu.getFacebook());
                    whatsapp.setText(Statics.currentIndividu.getWhatsapp());
                      instagram.setText(Statics.currentIndividu.getInstagram());
      
    }    
    
}
