/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import HabHub.MyListener;
import entities.Produit;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import services.ProduitService;

/**
 * FXML Controller class
 *
 * @author Ed
 */
public class BoutiqueFXMLController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent parent;

    @FXML
    private TextField searchBox;

    @FXML
    private GridPane grid;

    @FXML
    private ImageView ProdImage;

    @FXML
    private Label nameLabel;

    @FXML
    private Label Price;

    @FXML
    private ImageView genderImage;

    @FXML
    private Label Desc;

    @FXML
    private Label addReview;

    @FXML
    private GridPane gridReview;

    @FXML
    private Button reduce;

    @FXML
    private Label quantity;

    @FXML
    private Button add;

    @FXML
    private Button addtocart;

    @FXML
    private Label totprice;

    public ObservableList<Produit> produits = FXCollections.observableArrayList();
    ProduitService sa = new ProduitService();

    private ObservableList<Produit> getProduits() {
        List<Produit> Produit = new ArrayList<>();

        try {
            Produit = sa.afficherproduit();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        produits.clear();
        produits.addAll(Produit);
        return produits;

    }

    private ObservableList<Produit> getRechercheProduits(String input) {
        List<Produit> ProduitRecherche = new ArrayList<>();

        try {
            ProduitRecherche = sa.RechercheProduit(input);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        produits.clear();
        produits.addAll(ProduitRecherche);
        return produits;

    }

    @FXML
    void switchSceneCart(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../gui/PanierFXML.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void increaseQuantity() {
        quantity.setText(Integer.toString(Integer.parseInt(quantity.getText()) + 1));
        totprice.setText(Float.toString(Float.parseFloat(totprice.getText()) + (Float.parseFloat(Price.getText()))));

    }

    public void decreaseQuantity() {
        if (Integer.parseInt(quantity.getText()) > 1) {
            quantity.setText(Integer.toString(Integer.parseInt(quantity.getText()) - 1));
            totprice.setText(Float.toString(Float.parseFloat(totprice.getText()) - (Float.parseFloat(Price.getText()))));
        }

    }

    private void setChosenProduit(Produit p) {
        quantity.setText("1");

        Image Image = new Image(getClass().getResourceAsStream("../assets/img/sq.jpg"));
        ProdImage.setImage(Image);
        nameLabel.setText(p.getNom());
        String s = Float.toString(p.getPrix());
        Price.setText(s);
        Desc.setText(p.getDescription());

        totprice.setText(Price.getText());
        // totprice.setText(  (Integer.parseInt  (quantity.getText()) ) * (Integer.parseInt (Price.getText() )) )  ;
        //try{
        //totprice.setText(String.valueOf(  (Float.parseFloat (Price.getText())) * (Integer.parseInt(quantity.getText())) ) ) ;
        //  }catch(NumberFormatException ex){
        // totprice.setText(Price.getText()); // handle your exception
        // }
    }

    /*chosenFruitCard.setStyle("-fx-background-color: #" + fruit.getColor() + ";\n" +
                "    -fx-background-radius: 30;");*/

    private void displayProduits(ObservableList<Produit> produits) {
        grid.getChildren().clear();
        int column = 0;
        int row = 1;

        try {
            myListener = new MyListener() {
                @Override
                public void onClickListener(Produit produit) {
                    setChosenProduit(produit);
                }

            };
            for (int i = 0; i < produits.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ProduitFXML.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ProduitFXMLController produitController = fxmlLoader.getController();
                produitController.setData(produits.get(i), myListener);

                if (column == 6) {
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

    @FXML
    void RechercheProduit(ActionEvent event) {

    }

    private MyListener myListener;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        displayProduits(getProduits());
        if (produits.size() > 0) {
            setChosenProduit(produits.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Produit produit) {
                    setChosenProduit(produit);
                }

            };
        }

        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
            displayProduits(getRechercheProduits(newValue));

        });
    }

}
