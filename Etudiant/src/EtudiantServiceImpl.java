
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EtudiantServiceImpl extends UnicastRemoteObject implements EtudiantService {
    private List<Etudiant> etudiants;

    public EtudiantServiceImpl() throws RemoteException {
        etudiants = new ArrayList<>();
    }
    // lister les étudiants
    @Override
    public List<Etudiant> getEtudiants() throws RemoteException {
        return etudiants;
    }

    
     
    public void ajouterEtudiant(Etudiant etudiant) throws RemoteException {
    	int nextId = generateNextId(); // Genenrer nouveau unique id pour l'étudiant
        etudiant.setId(nextId);
        etudiants.add(etudiant);
        insertEtudiantIntoDatabase(etudiant);
    }
    private int generateNextId() {
        int maxId = 0;
        for (Etudiant etudiant : etudiants) {
            maxId = Math.max(maxId, etudiant.getId());
        }
        return maxId + 1;
    }

    public void modifierEtudiant(Etudiant etudiant) throws RemoteException {
        for (Etudiant e : etudiants) {
            if (e.getId() == etudiant.getId()) {
                e.setNom(etudiant.getNom());
                e.setPrenom(etudiant.getPrenom());
                e.setVille(etudiant.getVille());
                updateEtudiantInDatabase(etudiant); // Mettre à jour l'étudiant dans la base de données
                break;
            }
        }
    }
    

    public void supprimerEtudiant(int id) throws RemoteException {
        Etudiant etudiantToRemove = null;
        for (Etudiant etudiant : etudiants) {
            if (etudiant.getId() == id) {
                etudiantToRemove = etudiant;
                break;
            }
        }
        if (etudiantToRemove != null) {
            etudiants.remove(etudiantToRemove);
            deleteEtudiantFromDatabase(id); // Supprimer l'étudiant de la base de données
        }
    }

    public Etudiant getEtudiantById(int id) throws RemoteException {
        for (Etudiant etudiant : etudiants) {
            if (etudiant.getId() == id) {
                return etudiant;
            }
        }
        return null;
    }

    // Méthodes pour manipuler la base de données

    private void loadEtudiantsFromDatabase() {
        	 try (Connection c= (Connection) Connector.getConnection();
    	             PreparedStatement stmt = c.prepareStatement("SELECT * FROM étudiant")) {
           
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String ville = resultSet.getString("ville");

                Etudiant etudiant = new Etudiant(id, nom, prenom, ville);
                etudiants.add(etudiant);
            }

            stmt.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement des étudiants depuis la base de données");
        }
    }
   //inserer l'étudiant
    private void insertEtudiantIntoDatabase(Etudiant etudiant) {
        	 try (Connection c= (Connection) Connector.getConnection();
    	             PreparedStatement stmt = c.prepareStatement("INSERT INTO étudiant (id, nom, prenom, ville) VALUES (?, ?, ?, ?)")) {
           
        		 stmt.setInt(1, etudiant.getId());
        		 stmt.setString(2, etudiant.getNom());
            stmt.setString(3, etudiant.getPrenom());
            stmt.setString(4, etudiant.getVille());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'insertion de l'étudiant dans la base de données");
        }
    }
   //modifier l'étudiant
    private void updateEtudiantInDatabase(Etudiant etudiant) {
        	 try (Connection c= (Connection) Connector.getConnection();
	             PreparedStatement stmt = c.prepareStatement("UPDATE étudiant SET nom = ?, prenom = ?, ville = ? WHERE id = ?")) {
          
        		 stmt.setString(1, etudiant.getNom());
        		 stmt.setString(2, etudiant.getPrenom());
        		 stmt.setString(3, etudiant.getVille());
        		 stmt.setInt(4, etudiant.getId());
                 stmt.executeUpdate();
                 stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la mise à jour de l'étudiant dans la base de données");
        }
        
    }
   //méthode pour supprimer l'étudiant dans la bd
    private void deleteEtudiantFromDatabase(int id) {
    	 try (Connection c= (Connection) Connector.getConnection();
	             PreparedStatement stmt = c.prepareStatement("DELETE FROM étudiant WHERE id = ?")) {
    		 stmt.setInt(1, id);
    		 stmt.executeUpdate();
    		 stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la suppression de l'étudiant de la base de données");
        }
    }
	//méthode pour que l'utilisateur d'authentifier
	@Override
	public boolean login(String username, String password) throws RemoteException {
		 try (Connection c= (Connection) Connector.getConnection();
	             PreparedStatement stmt = c.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {

	            stmt.setString(1, username);
	            stmt.setString(2, password);

	            try (ResultSet rs = stmt.executeQuery()) {
	                return rs.next(); // If a matching row is found, login is successful
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw new RemoteException("Database error occurred.");
	        }
	}
	//méthode pour que l'utilisateur s'enregistrer
	@Override
	public boolean register(String username, String password) throws RemoteException {
		try (Connection c= (Connection) Connector.getConnection();
	             PreparedStatement stmt = c.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)")) {

	            stmt.setString(1, username);
	            stmt.setString(2, password);

	            int rowsAffected = stmt.executeUpdate();
	            return rowsAffected > 0;
	            // If any row is affected, registration is successful
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw new RemoteException("Database error occurred.");
	        }
	}
    
}