import equations.*;
import exceptions.NonSquareArray;

import static java.lang.Math.PI;
import static java.lang.Math.sin;

public class StartClass {
    public static void main(String[] args) throws NonSquareArray {
        Calculation solve = new Diffusion(1, 21,0,5, 3, 0.5, 0.0025, -0.5);
        solve.solveEquation();
        solve.printEquation();
        solve.printEquationInFile();
        return;
    }


}
