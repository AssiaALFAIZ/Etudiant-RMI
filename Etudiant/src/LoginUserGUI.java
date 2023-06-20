import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;

public class LoginUserGUI extends JFrame {
    private EtudiantService etudiantService;

    private JFrame frame;
    private JLabel registerLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginUserGUI(EtudiantService etudiantService) {
        this.etudiantService = etudiantService;
        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(550, 400));

        JPanel panel = new JPanel();
        panel.setBackground(new Color(128, 128, 192));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(null);

        registerLabel = new JLabel("Vous n'avez pas de compte ? Cliquez ici pour vous inscrire:");
        registerLabel.setBounds(72, 318, 219, 14);
        panel.add(registerLabel);

        JButton registerButton = new JButton("REGISTER");
        registerButton.setBackground(new Color(192, 192, 192));
        registerButton.setFont(new Font("Serif", Font.BOLD, 15));
        registerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
        });
        registerButton.setBounds(301, 307, 141, 36);
        registerButton.addActionListener(e -> switchToRegister());
        panel.add(registerButton);

        frame.getContentPane().add(panel);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(128, 0, 255));
        panel_1.setBounds(86, 61, 356, 229);
        panel.add(panel_1);
                        panel_1.setLayout(null);
                
                        JLabel usernameLabel = new JLabel("Username:");
                        usernameLabel.setFont(new Font("Harrington", Font.BOLD, 15));
                        usernameLabel.setBounds(10, 63, 79, 14);
                        panel_1.add(usernameLabel);
                        
                                JLabel passwordLabel = new JLabel("Password:");
                                passwordLabel.setFont(new Font("Harrington", Font.BOLD, 15));
                                passwordLabel.setBounds(10, 122, 72, 14);
                                panel_1.add(passwordLabel);
                                
                                        JButton loginButton = new JButton("LOG IN");
                                        loginButton.setForeground(new Color(255, 255, 255));
                                        loginButton.setBackground(new Color(255, 128, 255));
                                        loginButton.setFont(new Font("Serif", Font.BOLD, 15));
                                        loginButton.addActionListener(new ActionListener() {
                                        	public void actionPerformed(ActionEvent e) {
                                        	}
                                        });
                                        loginButton.setBounds(124, 176, 97, 42);
                                        panel_1.add(loginButton);
                                        
                                        JPanel panel_1_1 = new JPanel();
                                        panel_1_1.setLayout(null);
                                        panel_1_1.setBackground(SystemColor.menu);
                                        panel_1_1.setBounds(96, 47, 250, 40);
                                        panel_1.add(panel_1_1);
                                        
                                        usernameField = new JTextField();
                                        usernameField.setText("Entrer the username");
                                        usernameField.setForeground(new Color(178, 34, 34));
                                        usernameField.setFont(new Font("Tahoma", Font.ITALIC, 11));
                                        usernameField.setColumns(10);
                                        usernameField.setBackground(SystemColor.menu);
                                        usernameField.setBounds(10, 11, 169, 20);
                                        panel_1_1.add(usernameField);
                                        
                                        JPanel panel_1_1_1 = new JPanel();
                                        panel_1_1_1.setLayout(null);
                                        panel_1_1_1.setBackground(SystemColor.menu);
                                        panel_1_1_1.setBounds(96, 107, 250, 40);
                                        panel_1.add(panel_1_1_1);
                                        
                                        passwordField = new JPasswordField();
                                        passwordField.setText("Password");
                                        passwordField.setForeground(new Color(178, 34, 34));
                                        passwordField.setBackground(SystemColor.menu);
                                        passwordField.setBounds(10, 11, 171, 20);
                                        panel_1_1_1.add(passwordField);
                                        
                                        JLabel lblNewLabel_1 = new JLabel("LOGIN");
                                        lblNewLabel_1.setForeground(new Color(0, 0, 0));
                                        lblNewLabel_1.setFont(new Font("Harrington", Font.BOLD, 37));
                                        lblNewLabel_1.setBackground(Color.BLACK);
                                        lblNewLabel_1.setBounds(206, 11, 223, 40);
                                        panel.add(lblNewLabel_1);
                                        loginButton.addActionListener(e -> login());
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            boolean loggedIn = etudiantService.login(username, password);

            if (loggedIn) {
                JOptionPane.showMessageDialog(frame, "Login successful!");
                frame.dispose();

                SwingUtilities.invokeLater(() -> {
                    EtudiantGUI etudiantGUI = new EtudiantGUI();
                    etudiantGUI.setLocationRelativeTo(null); // Center the window
                    etudiantGUI.show();
                });
            } else {
                JOptionPane.showMessageDialog(frame, "Incorrect username or password.");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void switchToRegister() {
        frame.dispose(); // Close the login window

        // Open the registration window
        SwingUtilities.invokeLater(() -> {
            RegisterUserGUI registerUser = new RegisterUserGUI(etudiantService);
            registerUser.setLocationRelativeTo(null); // Center the window
        });
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            EtudiantService etudiantService = (EtudiantService) registry.lookup("EtudiantService");
            SwingUtilities.invokeLater(() -> {
                LoginUserGUI user = new LoginUserGUI(etudiantService);
                user.setLocationRelativeTo(null); // Center the window
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
