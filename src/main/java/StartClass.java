import equations.Calculation;
import equations.Diffusion;
import equations.DiffusionOurEquation;
import exceptions.NonSquareArray;

public class StartClass {
    public static void main(String[] args) throws NonSquareArray {
        Calculation solve = new DiffusionOurEquation(1, 101 , 6, 1, 5, 0.5, 2);
        solve.solveEquation();
        solve.printEquation();
        return;
    }

}
