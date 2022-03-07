/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import HabHub.BusinessListener;
import entities.Business;
import entities.Individu;
import entities.Revue;
import entities.ServiceBusiness;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import org.controlsfx.control.Rating;
import services.RevueServices;
import services.ServiceBusinessServices;
import services.UserBusinessServices;
import utils.Statics;
/**
 * FXML Controller class
 *
 * @author Ed
 */
public class BusinessFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField searchBox;

    @FXML
    private GridPane grid;

    @FXML
    private ImageView businessImageLabel;

    @FXML
    private Label businessTitleLabel;

    @FXML
    private Label businessLocationLabel;

    @FXML
    private Text businessDecriptionLabel;

    @FXML
    private Label experienceLabel;

    @FXML
    private Label openingHoursLabel;
    
    @FXML
    private Rating reviewRating;
   @FXML
    private TextArea reviewText;


    @FXML
    private GridPane gridReview;

    @FXML
    private GridPane gridServices;
    @FXML
    private Button groomingButton;
     @FXML
    private Button parkButton;
     @FXML
    private Button vetButton;
     @FXML
    private Button dogSittingButton;
    
    private BusinessListener businessListener;

    public ObservableList<Business> businessItems = FXCollections.observableArrayList();
    UserBusinessServices bs = new UserBusinessServices();
    public ObservableList<Revue> revueItems = FXCollections.observableArrayList();
    RevueServices rs = new RevueServices();
    
    public ObservableList<ServiceBusiness> serviceItems = FXCollections.observableArrayList();
    ServiceBusinessServices sbs = new ServiceBusinessServices();

  
    @FXML
    void submitRatingButton(ActionEvent event)throws SQLException {
        
    int starNumber=(int)reviewRating.getRating();
    String reviewCommentaire=reviewText.getText();
    System.out.println(reviewRating.getRating());
    Business b = new Business(16);
    Individu i1 =Statics.currentIndividu;
    Individu i = new Individu(1);
    
    Revue r = new Revue(i1,b,starNumber,reviewCommentaire);
    System.out.println(r);
    try {
            rs.ajouterRevueBusiness(r);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private ObservableList<Business> getRechercheBusiness(String type, String input) {
        List<Business> businessesRecherche = new ArrayList<>();
        try {
            businessesRecherche = bs.rechercherBusinessByType(type, input);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        businessItems.clear();
        businessItems.addAll(businessesRecherche);
        return businessItems;
    }

    private ObservableList<Business> getBusinessItems(String businessType) {
        List<Business> BI = new ArrayList<>();

        try {
            BI = bs.afficherBusiness(businessType);
            System.out.println(BI);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        businessItems.clear();
        businessItems.addAll(BI);
        return businessItems;

    }

    private List<Revue> getReviewItems(int businessId) {
        List<Revue> RI = new ArrayList<>();

        try {
            RI = rs.afficherRevueId(businessId);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return RI;

    }

    private List<ServiceBusiness> getServiceItems(int businessId) {
        List<ServiceBusiness> SI = new ArrayList<>();

        try {
            SI = sbs.afficherServicesById(businessId);
            System.out.println(SI);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        serviceItems.clear();
        // serviceItems.addAll(SI);
        return SI;

    }

    private void setChosenBusiness(Business b) {
        revueItems.clear();
        gridReview.getChildren().clear();
        Image businessImg = new Image(getClass().getResourceAsStream("../assets/img/business/BusinessItem/SalwaKbira.png"));
        businessImageLabel.setImage(businessImg);
        businessTitleLabel.setText(b.getTitre());
        businessLocationLabel.setText(b.getLocalisation());

        businessDecriptionLabel.setText(b.getDescription());
        experienceLabel.setText(Integer.toString(b.getExperience()));
        openingHoursLabel.setText(b.getHoraire());
        revueItems.addAll(getReviewItems(b.getIdBusiness()));
        //System.out.println(revueItems);
        int column2 = 0;
        int row2 = 1;
        try {
            for (int i = 0; i < revueItems.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("revueBusiness.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                RevueBusinessController revueBusinessController = fxmlLoader.getController();
                revueBusinessController.setData(revueItems.get(i));

                if (column2 == 1) {
                    column2 = 0;
                    row2++;
                }

                gridReview.add(anchorPane, column2++, row2); //(child,column,row)
                //set grid width
                gridReview.setMinWidth(Region.USE_PREF_SIZE);
                gridReview.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridReview.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                gridReview.setMinHeight(Region.USE_PREF_SIZE);
                gridReview.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridReview.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //serviceItems.clear();
        gridServices.getChildren().clear();
        serviceItems.addAll(getServiceItems(b.getIdBusiness()));
        //System.out.println(serviceItems);
        int column3 = 0;
        int row3 = 1;
        try {
            for (int i = 0; i < serviceItems.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ServicesFXML.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ServicesFXMLController servicesFXMLController = fxmlLoader.getController();
                servicesFXMLController.setData(serviceItems.get(i));

                if (column3 == 1) {
                    column3 = 0;
                    row3++;
                }

                gridServices.add(anchorPane, column3++, row3); //(child,column,row)
                //set grid width
                gridServices.setMinWidth(Region.USE_PREF_SIZE);
                gridServices.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridServices.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                gridServices.setMinHeight(Region.USE_PREF_SIZE);
                gridServices.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridServices.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayBusiness(ObservableList<Business> businesses) {

        grid.getChildren().clear();
        int column = 0;
        int row = 1;

        try {
            businessListener = new BusinessListener() {
                @Override
                public void onClickListener(Business business) {
                    setChosenBusiness(business);
                }

            };
            for (int i = 0; i < businesses.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("BusinessItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                BusinessItemController itemController = fxmlLoader.getController();
                itemController.setData(businesses.get(i), businessListener);

                if (column == 1) {
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
    void displayGrooming(ActionEvent event) {
        displayBusiness(getBusinessItems("grooming"));
        System.out.println("groom");
    if (businessItems.size() > 0) {
            setChosenBusiness(businessItems.get(0));
            businessListener = new BusinessListener() {
                @Override
                public void onClickListener(Business business) {
                    setChosenBusiness(business);

                }
            };
        }
        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
            displayBusiness(getRechercheBusiness("grooming",newValue));

        });        
        searchBox.clear();

        
        
    }

    @FXML
    void displayVet(ActionEvent event) {
        displayBusiness(getBusinessItems("vet"));
        System.out.println("vet");
        if (businessItems.size() > 0) {
            setChosenBusiness(businessItems.get(0));
            businessListener = new BusinessListener() {
                @Override
                public void onClickListener(Business business) {
                    setChosenBusiness(business);

                }
            };
        }

        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
            displayBusiness(getRechercheBusiness("vet",newValue));

        });
        searchBox.clear();

    }

    @FXML
    void displayParks(ActionEvent event) {
        displayBusiness(getBusinessItems("park"));
        System.out.println("park");
        if (businessItems.size() > 0) {
            setChosenBusiness(businessItems.get(0));
            businessListener = new BusinessListener() {
                @Override
                public void onClickListener(Business business) {
                    setChosenBusiness(business);

                }
            };
        }

        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
            displayBusiness(getRechercheBusiness("park",newValue));

        });
                searchBox.clear();


    }

    @FXML
    void displayDogSitting(ActionEvent event) {
        displayBusiness(getBusinessItems("dogSitting"));
        System.out.println("dogSitting");
        if (businessItems.size() > 0) {
            setChosenBusiness(businessItems.get(0));
            businessListener = new BusinessListener() {
                @Override
                public void onClickListener(Business business) {
                    setChosenBusiness(business);

                }
            };
        }
        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
            displayBusiness(getRechercheBusiness("dogSitting",newValue));

        });
                searchBox.clear();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayBusiness(getBusinessItems("grooming"));
        //businessItems.addAll(getBusinessItems());
        if (businessItems.size() > 0) {
            setChosenBusiness(businessItems.get(0));
            businessListener = new BusinessListener() {
                @Override
                public void onClickListener(Business business) {
                    setChosenBusiness(business);

                }
            };
        }

        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            
            System.out.println(newValue);
            displayBusiness(getRechercheBusiness("grooming",newValue));

        });
    }

}
