


package services;

import java.sql.SQLException;
import java.util.List;
import entite.Utilisateur;
import entite.Individu;


public interface IUtilisateur <Utilisateur > {
    public boolean login(String login, String mdp)throws SQLException;
     public List<Utilisateur> chercherUtilisateur(String input) throws SQLException;
    public List<Utilisateur> TrierUtilisateur() throws SQLException;
   
}
