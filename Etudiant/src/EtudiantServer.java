import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EtudiantServer implements EtudiantService {
	
    private List<Etudiant> etudiants;
    private Map<String, String> users;
    private Connection connection;
    

    public EtudiantServer() {
        etudiants = new ArrayList<>();
        users = new HashMap<>();
        connection = null;
    }
    //ajouter l'étudiant dans la bd    
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
    //modifier l'étudiant dans la bd   
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
    
    
    //supprimer l'étudiant dans la bd   
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
    
    public List<Etudiant> getEtudiants() throws RemoteException {
        return etudiants;
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
        try {
            String query = "SELECT * FROM étudiant";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String ville = resultSet.getString("ville");

                Etudiant etudiant = new Etudiant(id, nom, prenom, ville);
                etudiants.add(etudiant);
            }

            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement des étudiants depuis la base de données");
        }
    }
  //inserer l'étudiant dans la bd
    private void insertEtudiantIntoDatabase(Etudiant etudiant) {
        try {
            String query = "INSERT INTO étudiant (id, nom, prenom, ville) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, etudiant.getId());
            statement.setString(2, etudiant.getNom());
            statement.setString(3, etudiant.getPrenom());
            statement.setString(4, etudiant.getVille());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'insertion de l'étudiant dans la base de données");
        }
    }
  // mettre à jour l'étudiant dans la bd
    private void updateEtudiantInDatabase(Etudiant etudiant) {
        try {
            String query = "UPDATE étudiant SET nom = ?, prenom = ?, ville = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, etudiant.getNom());
            statement.setString(2, etudiant.getPrenom());
            statement.setString(3, etudiant.getVille());
            statement.setInt(4, etudiant.getId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la mise à jour de l'étudiant dans la base de données");
        }
    }
    //supprimer l'étudiant dans la bd
    private void deleteEtudiantFromDatabase(int id) {
        try {
            String query = "DELETE FROM étudiant WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la suppression de l'étudiant de la base de données");
        }
    }
    
 //les conditions de login
    public boolean login(String username, String password) throws RemoteException {
        if (users.containsKey(username) && users.get(username).equals(password)) {
            return true; // Authentification réussie
        }
        return false; // Nom d'utilisateur ou mot de passe incorrect
    }
    
    //les conditions de registre
	@Override
	public boolean register(String username, String password) throws RemoteException {
		 if (users.containsKey(username)) {
	            return false; // L'utilisateur existe déjà
	        }
	        users.put(username, password);
	        return true; // Enregistrement réussi
	}




    public static void main(String[] args) {
        try {
            // Création de l'objet serveur
            EtudiantServer etudiantServer = new EtudiantServer();
            EtudiantServiceImpl etudiantServiceImpl = new EtudiantServiceImpl();

            // Exportation de l'objet serveur
            EtudiantService stub = (EtudiantService) UnicastRemoteObject.exportObject(etudiantServer, 0);

            // Création du registre RMI
            Registry registry = LocateRegistry.createRegistry(1099);

            // Enregistrement du stub dans le registre
            registry.rebind("EtudiantService", etudiantServiceImpl);

            System.out.println("Serveur prêt !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
}

