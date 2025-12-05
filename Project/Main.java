import database.DatabaseHelper;
import view.LoginWindow; 
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        System.out.println("Initializing BCLS Security...");

        
        DatabaseHelper.createNewTable(); 

 
        SwingUtilities.invokeLater(() -> {
            new LoginWindow(); 
        });
    }
}