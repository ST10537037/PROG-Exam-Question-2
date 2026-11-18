
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            MattressSalesGUI form =
                    new MattressSalesGUI();

            form.setVisible(true);
        });
    }
}


