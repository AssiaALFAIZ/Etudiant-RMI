import java.sql.Connection;
import java.sql.DriverManager;

//pour passer la connexion avec la base de donnees
public class Connector {
    private static Connection connection;
    
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projet", "root", "");
            System.out.println("Connection successful!");
        } catch (Exception e) {
            System.out.println(e);
        }
        return connection;
    }

    public static void main(String[] args) {
        Connection conn = getConnection();
    }
}