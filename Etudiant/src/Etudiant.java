import java.io.Serializable;

public class Etudiant implements Serializable {
    private int id;
    private String nom;
    private String prenom;
    private String ville;

    public Etudiant(int id, String nom, String prenom, String ville) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.ville = ville;
    }
    public void setId(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nom: " + nom + ", Prénom: " + prenom + ", Ville: " + ville;
    }
}