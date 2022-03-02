/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import HabHub.BusinessListener;
import entities.Business;
import entities.Revue;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;


/**
 * FXML Controller class
 *
 * @author User
 */
public class RevueBusinessController implements Initializable {
  @FXML
    private ImageView UserImage;

    @FXML
    private Label idIndividuLabel;

    @FXML
    private ImageView starReviewsImage;

    @FXML
    private Text commentaireText;

    @FXML
    private Label datePubLabel; 
    private Revue revue;
    /**
     * Initializes the controller class.
     */
    
       public void setData(Revue revue) {
        this.revue = revue;

        idIndividuLabel.setText(revue.getIndividu().getPrenom());
        commentaireText.setText(revue.getCommentaire());
        datePubLabel.setText(revue.getDatePublication().toString());
        Image bImage = new Image(getClass().getResourceAsStream("../assets/img/business/BusinessItem/vetImage.png"));
        UserImage.setImage(bImage);
        Image starImg = new Image(getClass().getResourceAsStream("../assets/img/revue/0.png"));
        if (revue.getNbEtoiles()==1){
        starImg = new Image(getClass().getResourceAsStream("../assets/img/revue/1.png"));
        }
        else if(revue.getNbEtoiles()==2){
         starImg = new Image(getClass().getResourceAsStream("../assets/img/revue/2.png"));
        }
        else if(revue.getNbEtoiles()==3){
         starImg = new Image(getClass().getResourceAsStream("../assets/img/revue/3.png"));
        }
        else if(revue.getNbEtoiles()==4){
         starImg = new Image(getClass().getResourceAsStream("../assets/img/revue/4.png"));
        }
        else if(revue.getNbEtoiles()==5){
         starImg = new Image(getClass().getResourceAsStream("../assets/img/revue/5.png"));
        }
        starReviewsImage.setImage(starImg);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    
}


