import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Creiamo un modello di dimensione 6x6
        KenKenModel model = new KenKenModel(6);

        // Creiamo la vista e la associamo al modello
        KenKenView view = new KenKenView(model);

        // Creiamo il controller e lo associamo al modello e alla vista
        KenKenController controller = new KenKenController(model, view);

        // Configuriamo qualche esempio di vincolo per testare l'applicazione
        List<Point> cells1 = Arrays.asList(new Point(0, 0), new Point(0, 1));
        Constraint constraint1 = new Constraint(cells1, 3, '+');
        controller.addConstraint(constraint1);

        List<Point> cells2 = Arrays.asList(new Point(0, 2), new Point(1, 2));
        Constraint constraint2 = new Constraint(cells2, 2, '/');
        controller.addConstraint(constraint2);

        // Altri vincoli di esempio possono essere aggiunti qui...

        // Mostriamo la GUI
        view.setVisible(true);
    }
}
