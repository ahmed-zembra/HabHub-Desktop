/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entite.Individu;
import entite.Utilisateur;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.MyDB;

/**
 *
 * @author NADA_USER
 */
public class UserIndividuServices implements IIndividu {
    
         Connection connect;
      Statement stm;

    public UserIndividuServices() {
        connect=MyDB.getInstance().getConnexion();
    }

    @Override
    public void ajouter(Individu I , Utilisateur U) {
        try{
            PreparedStatement preUser = connect.prepareStatement("INSERT INTO utilisateur (email,password,numTel,type)VALUES (?,?,?,?);");
        preUser.setString(1, U.getEmail());
        preUser.setString(2, doHashing(U.getPassword()));
        preUser.setInt(3, U.getNumTel());
        preUser.setString(4, U.getType());
        preUser.executeUpdate();

        PreparedStatement stm = connect.prepareStatement("select idUtilisateur from utilisateur where email =?;");
        stm.setString(1, U.getEmail());
        ResultSet rst = stm.executeQuery();
        
        Utilisateur u = new Utilisateur ();
        while (rst.next()){
        u.setIdUtilisateur(rst.getInt("idUtilisateur"));
        }
      
        PreparedStatement pre = connect.prepareStatement("INSERT INTO individu (idUtilisateur,nom)VALUES (?,?);");
        pre.setInt(1,u.getIdUtilisateur());      
        pre.setString(2, I.getNom());
     
        
                                    
        pre.executeUpdate();
        }catch (SQLException e){
        e.printStackTrace();}
    }

    @Override
    public boolean Update(int idIndividu, Utilisateur utilisateur, String nom, String prenom, String dateNaissance, String sexe,String adresse, String facebook, String instagram, String whatsapp) {
                    try {

            PreparedStatement pre = connect.prepareStatement("UPDATE individu SET nom = ? , prenom= ? ,dateNaissance= ? , sexe= ? , adresse= ? , facebook= ?,instagram= ? , whatsapp= ? , where idIndividu= ? ;");
            pre.setString(1, nom);
            pre.setString(2, prenom);   
            pre.setString(3,dateNaissance);
            pre.setString(4, sexe);
            pre.setString(5, adresse);
            pre.setString(6, facebook);
            pre.setString(7, instagram);
            pre.setString(8, whatsapp);

            if (pre.executeUpdate() != 0) {
                System.out.println("individu Updated successfully!!");
                 } else {
                System.out.println("not Updated!!!");
            }
                return true;
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int idIndividu) throws SQLException {
        PreparedStatement pre = connect.prepareStatement("Delete from individu where idIndividu=? ;");
        pre.setInt(1, idIndividu);
        if (pre.executeUpdate() != 0) {
            System.out.println("individu Deleted");
            return true;
        }
        System.out.println("id of individu not found");
        return false;

    }
 
    
 
   public String doHashing (String password) {
         try {
          MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");

          messageDigest.update(password.getBytes());

          byte[] resultByteArray = messageDigest.digest();

          StringBuilder sb = new StringBuilder();

          for (byte b : resultByteArray) {
           sb.append(String.format("%02x", b));
          }

          return sb.toString();

         } catch (NoSuchAlgorithmException e) {
          e.printStackTrace();
         }

         return "";
        }
   public Boolean verifLogin (String email ,String  mdp)
     {
         String hashedMdp= doHashing(mdp); 
         String requete = "select * from utilisateur where email=? and password=?";
                 try {
            PreparedStatement ps = connect.prepareStatement(requete);
                     ps.setString(1, email);
                     ps.setString(2, hashedMdp);
            ResultSet resultat = ps.executeQuery();
            if (resultat.next()) {
             
                return true ;
 
            }
            else{
                           

            return false;
            }
        } catch (SQLException ex) {
            //Logger.getLogger(PersonneDao.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("erreur lors de la recherche du depot " + ex.getMessage());
            return false;
        }}
   
    @Override
    public List<Individu> afficherIndividu() throws SQLException {
               
        List<Individu> Individu = new ArrayList<>();
        String req = "select * from individu;";
        stm = connect.createStatement();
        ResultSet rst = stm.executeQuery(req);
            while (rst.next()) {
            Individu k = new Individu(rst.getInt("idIndividu"),
                       new Utilisateur(rst.getInt     ("idUtilisateur"))  ,
            rst.getString("nom"),
            rst.getString("prenom"),
            rst.getString("dateNaissance"),
            rst.getString("sexe"),
            rst.getString("adresse"),
                        rst.getString("facebook"),
                        rst.getString("instagram"),
                        rst.getString("whatsapp"));



            Individu.add(k);
        }
        return Individu;
    }
    public static Individu currentIndividu = new Individu();

public Individu findIndividuByIdUtilisateur(Utilisateur u) throws SQLException{
       Individu i = new   Individu();
          try {
       PreparedStatement req = connect.prepareStatement("select * from individu where idUtilisateur=?");
            req.setInt(1,u.getIdUtilisateur());

             ResultSet rst= req.executeQuery();
             while(rst.next())
             {

                 //String nom, String prenom, String sexe, String date,String email, String login, String mdp, String role
                i =   new Individu(rst.getInt("idIndividu"),u,rst.getString("nom"),rst.getString("prenom"),rst.getString("sexe"),rst.getString("adresse"),
                        rst.getString("facebook"),
                        rst.getString("instagram"),
                        rst.getString("whatsapp"));
             }
         } catch (SQLException ex) {
            ex.printStackTrace();
         }
return i;
    }
    
}
