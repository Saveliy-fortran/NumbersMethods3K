import equations.Calculation;
import equations.DiffusionOurEquation;
import exceptions.NonSquareArray;
import javafx.application.Application;
import javafx.stage.Stage;

public class StartClass {
    public static void main(String[] args) throws NonSquareArray {
        Calculation solve = new DiffusionOurEquation(1, 20 , 10, 1, 2, 1);
        solve.solveEquation();
        solve.printEquation();
        return;
    }

}
