/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author asus
 */
public interface IPanier<Pa> {
     public void ajouterPa(Pa pa) throws SQLException;
    public List<Pa> afficherPanier() throws SQLException;
    public void deletePanier(int idPanier) throws SQLException;
   public boolean updatePanier(int idPanier,int idProduit,int idUtilisateur,int quantite) ;
}
