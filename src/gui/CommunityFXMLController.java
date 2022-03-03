/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import HabHub.CommunityListener;
import com.jfoenix.controls.JFXTextField;
import entities.AnnonceProprietaireChien;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import services.AnnonceProprietaireChienService;

/**
 * FXML Controller class
 *
 * @author Ed
 */
public class CommunityFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField searchBox;

    @FXML
    private GridPane grid;

    @FXML
    private ImageView dogImage;

    @FXML
    private Label dogNameLabel;

    @FXML
    private Label ownerLocationLabel;

    @FXML
    private ImageView genderImage;

    @FXML
    private Label ageLabel;

    @FXML
    private Label genderColor;

    @FXML
    private Label genderLabel;

    @FXML
    private Label dogRaceLabel;

    @FXML
    private Label dogGroupLabel;

    @FXML
    private Label dogLastSeenOnLabel;

    @FXML
    private Label dogLastSeenInLabel;

    @FXML
    private Text dogStory;

    @FXML
    private Label ownerNameLabel;

    @FXML
    private Label ownerLocationLabel2;

    private CommunityListener communityListener;

    public ObservableList<AnnonceProprietaireChien> data = FXCollections.observableArrayList();
    AnnonceProprietaireChienService sa = new AnnonceProprietaireChienService();

    private ObservableList<AnnonceProprietaireChien> getRechercheAnnonceProprietaireChien(String input, String type) {
        List<AnnonceProprietaireChien> annoncesRecherche = new ArrayList<>();

        try {
            annoncesRecherche = sa.rechercheAnnonceProprietaireChien(input, type);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        data.clear();
        data.addAll(annoncesRecherche);
        return data;

    }

    private ObservableList<AnnonceProprietaireChien> getAnnoncesProprietaireChien(String type) {
        List<AnnonceProprietaireChien> annonces = new ArrayList<>();

        try {
            annonces = sa.afficherAnnonceProprietaireChien(type);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        data.clear();
        data.addAll(annonces);
        return data;

    }

    private void setChosenChien(AnnonceProprietaireChien a) {

        dogNameLabel.setText(a.getChien().getNom());
        Image genderImg = new Image(getClass().getResourceAsStream("../assets/img/female.png"));
        genderLabel.setText("Female");
        if ("M".equals(a.getChien().getSexe())) {
            genderImg = new Image(getClass().getResourceAsStream("../assets/img/male.png"));
            genderLabel.setText("Male");
        }
        genderImage.setImage(genderImg);

        ageLabel.setText(a.getChien().getAge());
        dogStory.setText(a.getChien().getDescription());
        ownerLocationLabel.setText(a.getLocalisation());
        ownerLocationLabel2.setText(a.getLocalisation());
        dogRaceLabel.setText("Golden Retriever");
        dogGroupLabel.setText("Golden Retriever");

        //dogLastSeenOnLabel.setText(a.getDatePerte().toString());
        dogLastSeenInLabel.setText(a.getLocalisation());
        ownerNameLabel.setText(a.getChien().getProprietaireChien().getIndividu().getPrenom());

        Image dogImg = new Image(getClass().getResourceAsStream("../assets/img/dog.png"));
        dogImage.setImage(dogImg);

    }

    private void displayAnnonces(ObservableList<AnnonceProprietaireChien> annonces) {
        grid.getChildren().clear();
        int column = 0;
        int row = 1;

        try {
            communityListener = new CommunityListener() {
                @Override
                public void onClickListener(AnnonceProprietaireChien annonceProprietaireChien) {
                    setChosenChien(annonceProprietaireChien);
                }

            };
            for (int i = 0; i < annonces.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ChienFXML.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ChienFXMLController chienController = fxmlLoader.getController();
                chienController.setData(annonces.get(i), communityListener);

                if (column == 4) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_PREF_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_COMPUTED_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_PREF_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_COMPUTED_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayAnnonces(getAnnoncesProprietaireChien("P"));

        //displayAnnonces(getRechercheAnnonceProprietaireChien("luna","P"));
        if (data.size() > 0) {
            setChosenChien(data.get(0));
            communityListener = new CommunityListener() {
                @Override
                public void onClickListener(AnnonceProprietaireChien annonceProprietaireChien) {
                    setChosenChien(annonceProprietaireChien);
                }

            };
        }

        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
            displayAnnonces(getRechercheAnnonceProprietaireChien(newValue, "P"));

        });

        //displayAnnonces(annoncesProprietaireChien);
        /* 

            keyboardTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                 
            String searchKeyboard = newValue.toLowerCase();
            annoncesProprietaireChien.addAll(getRechercheAnnonceProprietaireChien(searchKeyboard,"P"));
                
            }*/
    }

}