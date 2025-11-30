import database.DatabaseHelper;
import javax.swing.SwingUtilities;
import view.MainMenu;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Project...");

        DatabaseHelper.createNewTable();

        SwingUtilities.invokeLater(() -> {
            new MainMenu();
        });
    }
}
