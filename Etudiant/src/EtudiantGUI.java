
import javax.swing.*;
import javax.swing.JComboBox;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.util.List;
import javax.swing.DefaultComboBoxModel;


public class EtudiantGUI extends JFrame {
    private EtudiantService etudiantService;
    private DefaultListModel<Etudiant> etudiantListModel;
    private JList<Etudiant> etudiantList;
    private JTextField searchField;
    private JTextField nomField;
    private JTextField prenomField;
    private JComboBox<String> villeComboBox;

    private static int nextId = 1;

    public EtudiantGUI() {
        try {
            etudiantService = (EtudiantService) Naming.lookup("//localhost/EtudiantService");
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Gestion des étudiants");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(600, 600);
        getContentPane().setLayout(new BorderLayout());

        // Panel supérieur
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(128, 0, 255));
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        getContentPane().add(topPanel, BorderLayout.NORTH);

        // Panel central
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(128, 0, 255));

        etudiantListModel = new DefaultListModel<>();
        centerPanel.setLayout(null);

        // Panel droit
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(128, 128, 192));
        rightPanel.setBounds(0, 306, 584, 234);
        JLabel villeLabel = new JLabel("Ville:");
        String[] villesMaroc = {"Casablanca", "Rabat", "Marrakech", "Fès", "Tanger", "Agadir","Meknès","Kénitra","Asfi","Laâyoune"};

        centerPanel.add(rightPanel);
                rightPanel.setLayout(null);
        
                JLabel label = new JLabel("Nom:");
                label.setFont(new Font("Harrington", Font.BOLD, 15));
                label.setBounds(10, 85, 43, 39);
                rightPanel.add(label);
                nomField = new JTextField();
                nomField.setBounds(74, 94, 177, 25);
                rightPanel.add(nomField);
                JLabel label_1 = new JLabel("Prénom:");
                label_1.setFont(new Font("Harrington", Font.BOLD, 15));
                label_1.setBounds(10, 135, 162, 88);
                rightPanel.add(label_1);
                prenomField = new JTextField();
                prenomField.setBounds(74, 169, 177, 25);
                rightPanel.add(prenomField);
                JLabel label_2 = new JLabel("Ville:");
                label_2.setFont(new Font("Harrington", Font.BOLD, 15));
                label_2.setBounds(10, -7, 89, 88);
                rightPanel.add(label_2);
                villeComboBox = new JComboBox<>(new DefaultComboBoxModel<>(villesMaroc));
                villeComboBox.setBackground(new Color(255, 128, 255));
                villeComboBox.setFont(new Font("Serif", Font.PLAIN, 15));
                villeComboBox.setBounds(71, 27, 180, 25);
                rightPanel.add(villeComboBox);
                JLabel label_3 = new JLabel("ID:");
                label_3.setFont(new Font("Harrington", Font.BOLD, 15));
                label_3.setBounds(372, 60, 35, 14);
                rightPanel.add(label_3);
                JButton searchButton = new JButton("Rechercher");
                searchButton.setBackground(new Color(255, 128, 255));
                searchButton.setFont(new Font("Serif", Font.PLAIN, 15));
                searchButton.setBounds(369, 133, 111, 25);
                rightPanel.add(searchButton);
                searchField = new JTextField(15);
                searchField.setBounds(369, 85, 189, 25);
                rightPanel.add(searchField);
                searchButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        rechercherEtudiant();
                    }
                });
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        JButton ajouterButton = new JButton("Ajouter");
        ajouterButton.setBackground(new Color(255, 128, 255));
        ajouterButton.setFont(new Font("Serif", Font.PLAIN, 15));
        ajouterButton.setBounds(34, 247, 107, 34);
        centerPanel.add(ajouterButton);
        JButton modifierButton = new JButton("Modifier");
        modifierButton.setBackground(new Color(255, 128, 255));
        modifierButton.setFont(new Font("Serif", Font.PLAIN, 15));
        modifierButton.setBounds(168, 247, 107, 34);
        centerPanel.add(modifierButton);
        JButton supprimerButton = new JButton("Supprimer");
        supprimerButton.setBackground(new Color(255, 128, 255));
        supprimerButton.setFont(new Font("Serif", Font.PLAIN, 15));
        supprimerButton.setBounds(307, 247, 107, 34);
        centerPanel.add(supprimerButton);
        JButton actualiserButton = new JButton("Actualiser");
        actualiserButton.setBackground(new Color(255, 128, 255));
        actualiserButton.setFont(new Font("Serif", Font.PLAIN, 15));
        actualiserButton.setBounds(436, 247, 107, 34);
        centerPanel.add(actualiserButton);
        etudiantList = new JList<>(etudiantListModel);
        etudiantList.setBounds(0, 22, 583, 198);
        centerPanel.add(etudiantList);
        
        JLabel lblListeOfClasses = new JLabel("Liste des étudiants");
        lblListeOfClasses.setBounds(215, 0, 260, 14);
        centerPanel.add(lblListeOfClasses);
        lblListeOfClasses.setFont(new Font("Harrington", Font.BOLD | Font.ITALIC, 18));
        
                actualiserButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        actualiserListeEtudiants();
                    }
                });
        
                supprimerButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        supprimerEtudiant();
                    }
                });
        
                modifierButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        modifierEtudiant();
                    }
                });
        
                ajouterButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ajouterEtudiant();
                    }
                });

        actualiserListeEtudiants();
    }

    private void rechercherEtudiant() {
        String idText = searchField.getText();
        if (!idText.isEmpty()) {
            int id = Integer.parseInt(idText);
            try {
                Etudiant etudiant = null;
                for (Etudiant e : etudiantService.getEtudiants()) {
                    if (e.getId() == id) {
                        etudiant = e;
                        break;
                    }
                }
                if (etudiant != null) {
                    etudiantListModel.clear();
                    etudiantListModel.addElement(etudiant);
                } else {
                    JOptionPane.showMessageDialog(this, "Aucun étudiant trouvé avec l'ID " + id);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //methode pour ajouter l'étudiant après le click sur le button Ajouter

    private void ajouterEtudiant() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String ville = (String) villeComboBox.getSelectedItem();
        int id = generateNewId(); // Méthode pour générer un nouvel identifiant unique
        Etudiant etudiant = new Etudiant(id, nom, prenom, ville);
        try {
            List<Etudiant> etudiants = etudiantService.getEtudiants();
            boolean studentExists = false;
            for (Etudiant existingEtudiant : etudiants) {
                if (existingEtudiant.getNom().equals(nom) &&
                        existingEtudiant.getPrenom().equals(prenom) &&
                        existingEtudiant.getVille().equals(ville)) {
                    studentExists = true;
                    break;
                }
            }
            if (studentExists) {
                JOptionPane.showMessageDialog(this, "Un étudiant avec les mêmes informations existe déjà.");
            } else {
                etudiantService.ajouterEtudiant(etudiant);
                actualiserListeEtudiants();
                JOptionPane.showMessageDialog(this, "Étudiant ajouté avec succès.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private int generateNewId() {
        return nextId++;
    }
    //methode pour modofer les informations de l'étudiant après le click sur le button Modifier
    private void modifierEtudiant() {
        Etudiant selectedEtudiant = etudiantList.getSelectedValue();
        if (selectedEtudiant != null) {
            String nom = nomField.getText();
            String prenom = prenomField.getText();
            String ville = (String) villeComboBox.getSelectedItem();
            Etudiant etudiant = new Etudiant(selectedEtudiant.getId(), nom, prenom, ville);
            try {
                etudiantService.modifierEtudiant(etudiant);
                actualiserListeEtudiants();
                JOptionPane.showMessageDialog(this, "Étudiant modifié avec succès.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Sélectionnez un étudiant à modifier.");
        }
    }
    //methode pour supprimer l'étudiant après le click sur le button Supprimer
    private void supprimerEtudiant() {
        Etudiant selectedEtudiant = etudiantList.getSelectedValue();
        if (selectedEtudiant != null) {
            int id = selectedEtudiant.getId();
            try {
                etudiantService.supprimerEtudiant(id);
                actualiserListeEtudiants();
                JOptionPane.showMessageDialog(this, "Étudiant supprimé avec succès.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Sélectionnez un étudiant à supprimer.");
        }
    }
    //methode pour actualiser la liste après recherche d'un étudiant avec leur id
    private void actualiserListeEtudiants() {
        try {
            etudiantListModel.clear();
            List<Etudiant> etudiants = etudiantService.getEtudiants();
            for (Etudiant etudiant : etudiants) {
                etudiantListModel.addElement(etudiant);
            }

            // Mettez à jour les champs de texte et la liste déroulante avec les informations du premier étudiant de la liste
            if (!etudiants.isEmpty()) {
                Etudiant premierEtudiant = etudiants.get(0);
                nomField.setText(premierEtudiant.getNom());
                prenomField.setText(premierEtudiant.getPrenom());
                villeComboBox.setSelectedItem(premierEtudiant.getVille());
            } else {
                // Effacer les champs de texte et la liste déroulante s'il n'y a pas d'étudiants dans la liste
                nomField.setText("");
                prenomField.setText("");
                villeComboBox.setSelectedIndex(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    EtudiantGUI frame = new EtudiantGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
