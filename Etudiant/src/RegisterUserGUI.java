import java.awt.Dimension;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterUserGUI extends JFrame {
    private static final String RMI_URL = "rmi://localhost/EtudiantService";
    private EtudiantService etudiantService;

    private JFrame frame;
    private JLabel loginLabel;
    private JTextField textField;
    private JPasswordField passwordField_1;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public RegisterUserGUI(EtudiantService etudiantService) {
        this.etudiantService = etudiantService;
        frame = new JFrame("Register");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200); // Set the size of the JFrame
        frame.setPreferredSize(new Dimension(550, 400));

        JPanel panel = new JPanel();
        panel.setBackground(new Color(128, 128, 192));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(null);
        
        JLabel lblNewLabel_1_1 = new JLabel("REGISTRE");
        lblNewLabel_1_1.setBounds(188, 11, 190, 44);
        lblNewLabel_1_1.setForeground(Color.BLACK);
        lblNewLabel_1_1.setFont(new Font("Harrington", Font.BOLD, 37));
        lblNewLabel_1_1.setBackground(Color.BLACK);
        panel.add(lblNewLabel_1_1);

        loginLabel = new JLabel("Vous avez déjà un compte? Cliquez ici pour vous identifier:");
        loginLabel.setBounds(60, 311, 216, 14);
        panel.add(loginLabel);

        JButton loginButton = new JButton("LOG IN");
        loginButton.setFont(new Font("Serif", Font.BOLD, 15));
        loginButton.setBackground(new Color(192, 192, 192));
        loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
        });
        loginButton.setBounds(286, 301, 134, 35);
        loginButton.addActionListener(e -> switchToLogin());
        panel.add(loginButton);

        frame.getContentPane().add(panel);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBounds(10, 348, 514, 1);
        panel_1.setLayout(null);
        panel_1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel_1.setBackground(new Color(128, 128, 192));
        panel.add(panel_1);
        
        JLabel registerLabel = new JLabel("Don't have an account? Click here to register:");
        registerLabel.setBounds(72, 318, 219, 14);
        panel_1.add(registerLabel);
        
        JButton registerButton_1 = new JButton("REGISTER");
        registerButton_1.setFont(new Font("Serif", Font.BOLD, 15));
        registerButton_1.setBackground(Color.LIGHT_GRAY);
        registerButton_1.setBounds(301, 307, 141, 36);
        panel_1.add(registerButton_1);
        
        JPanel panel_1_1 = new JPanel();
        panel_1_1.setLayout(null);
        panel_1_1.setBackground(new Color(128, 0, 255));
        panel_1_1.setBounds(86, 61, 356, 229);
        panel_1.add(panel_1_1);
        
        JLabel usernameLabel_1 = new JLabel("Username:");
        usernameLabel_1.setFont(new Font("Harrington", Font.BOLD, 15));
        usernameLabel_1.setBounds(10, 63, 79, 14);
        panel_1_1.add(usernameLabel_1);
        
        JLabel passwordLabel_1 = new JLabel("Password:");
        passwordLabel_1.setFont(new Font("Harrington", Font.BOLD, 15));
        passwordLabel_1.setBounds(10, 122, 72, 14);
        panel_1_1.add(passwordLabel_1);
        
        JButton loginButton_1 = new JButton("LOG IN");
        loginButton_1.setForeground(Color.WHITE);
        loginButton_1.setFont(new Font("Serif", Font.BOLD, 15));
        loginButton_1.setBackground(Color.MAGENTA);
        loginButton_1.setBounds(124, 176, 97, 42);
        panel_1_1.add(loginButton_1);
        
        JPanel panel_1_1_1 = new JPanel();
        panel_1_1_1.setLayout(null);
        panel_1_1_1.setBackground(SystemColor.menu);
        panel_1_1_1.setBounds(96, 47, 250, 40);
        panel_1_1.add(panel_1_1_1);
        
        textField = new JTextField();
        textField.setText("Entrer the Username");
        textField.setForeground(new Color(178, 34, 34));
        textField.setFont(new Font("Tahoma", Font.ITALIC, 11));
        textField.setColumns(10);
        textField.setBackground(SystemColor.menu);
        textField.setBounds(10, 11, 169, 20);
        panel_1_1_1.add(textField);
        
        JPanel panel_1_1_1_1 = new JPanel();
        panel_1_1_1_1.setLayout(null);
        panel_1_1_1_1.setBackground(SystemColor.menu);
        panel_1_1_1_1.setBounds(96, 107, 250, 40);
        panel_1_1.add(panel_1_1_1_1);
        
        passwordField_1 = new JPasswordField();
        passwordField_1.setText("Password");
        passwordField_1.setForeground(new Color(178, 34, 34));
        passwordField_1.setBackground(SystemColor.menu);
        passwordField_1.setBounds(10, 11, 171, 20);
        panel_1_1_1_1.add(passwordField_1);
        
        JLabel lblNewLabel_1 = new JLabel("LOGIN");
        lblNewLabel_1.setForeground(Color.BLACK);
        lblNewLabel_1.setFont(new Font("Harrington", Font.BOLD, 37));
        lblNewLabel_1.setBackground(Color.BLACK);
        lblNewLabel_1.setBounds(206, 11, 223, 40);
        panel_1.add(lblNewLabel_1);
        
        JPanel panel_1_2 = new JPanel();
        panel_1_2.setLayout(null);
        panel_1_2.setBackground(new Color(128, 0, 255));
        panel_1_2.setBounds(88, 66, 356, 229);
        panel.add(panel_1_2);
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Harrington", Font.BOLD, 15));
        usernameLabel.setBounds(10, 63, 79, 14);
        panel_1_2.add(usernameLabel);
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Harrington", Font.BOLD, 15));
        passwordLabel.setBounds(10, 122, 72, 14);
        panel_1_2.add(passwordLabel);
        
        JPanel panel_1_1_2 = new JPanel();
        panel_1_1_2.setLayout(null);
        panel_1_1_2.setBackground(SystemColor.menu);
        panel_1_1_2.setBounds(96, 47, 250, 40);
        panel_1_2.add(panel_1_1_2);
        
        usernameField = new JTextField();
        usernameField.setText("Entrer the Username");
        usernameField.setForeground(new Color(178, 34, 34));
        usernameField.setFont(new Font("Tahoma", Font.ITALIC, 11));
        usernameField.setColumns(10);
        usernameField.setBackground(SystemColor.menu);
        usernameField.setBounds(10, 11, 169, 20);
        panel_1_1_2.add(usernameField);
        
        JPanel panel_1_1_1_2 = new JPanel();
        panel_1_1_1_2.setLayout(null);
        panel_1_1_1_2.setBackground(SystemColor.menu);
        panel_1_1_1_2.setBounds(96, 107, 250, 40);
        panel_1_2.add(panel_1_1_1_2);
        
        passwordField = new JPasswordField();
        passwordField.setText("Password");
        passwordField.setForeground(new Color(178, 34, 34));
        passwordField.setBackground(SystemColor.menu);
        passwordField.setBounds(10, 11, 171, 20);
        panel_1_1_1_2.add(passwordField);
        
                JButton registerButton = new JButton("REGISTRER");
                registerButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
                });
                registerButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
                });
                registerButton.setFont(new Font("Serif", Font.BOLD, 15));
                registerButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
                });
                registerButton.setForeground(new Color(255, 255, 255));
                registerButton.setBackground(new Color(255, 128, 255));
                registerButton.setBounds(134, 165, 131, 39);
                panel_1_2.add(registerButton);
                registerButton.addActionListener(e -> register());
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);
    }

    private void register() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            boolean registered = etudiantService.register(username, password);

            if (registered) {
                JOptionPane.showMessageDialog(frame, "Registration successful!");
                frame.dispose(); // Close the registration window

                // Open the login window
                SwingUtilities.invokeLater(() -> {
                    LoginUserGUI loginUser = new LoginUserGUI(etudiantService);
                    loginUser.setLocationRelativeTo(null); // Center the window
                });
            } else {
                JOptionPane.showMessageDialog(frame, "Username already exists.");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void switchToLogin() {
        frame.dispose(); // Close the registration window

        // Open the login window
        SwingUtilities.invokeLater(() -> {
            LoginUserGUI loginUser = new LoginUserGUI(etudiantService);
            loginUser.setLocationRelativeTo(null); // Center the window
        });
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            EtudiantService etudiantService = (EtudiantService) registry.lookup("EtudiantService");
            SwingUtilities.invokeLater(() -> {
                RegisterUserGUI user = new RegisterUserGUI(etudiantService);
                user.setLocationRelativeTo(null); // Center the window
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
