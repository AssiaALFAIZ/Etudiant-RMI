import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface EtudiantService extends Remote{
boolean login(String username, String password) throws RemoteException;
boolean register(String username, String password) throws RemoteException;
List<Etudiant> getEtudiants() throws RemoteException;
Etudiant getEtudiantById(int id) throws RemoteException;
void ajouterEtudiant(Etudiant etudiant) throws RemoteException;
void modifierEtudiant(Etudiant etudiant) throws RemoteException;
void supprimerEtudiant(int id) throws RemoteException;
}
