import equations.Calculation;
import equations.DiffusionOurEquation;
import exceptions.NonSquareArray;

public class StartClass {
    public static void main(String[] args) throws NonSquareArray {
        Calculation solve = new DiffusionOurEquation(1, 40 , 5, 1, 2, 0.3, 1);
        solve.solveEquation();
        solve.printEquation();
        return;
    }

}
