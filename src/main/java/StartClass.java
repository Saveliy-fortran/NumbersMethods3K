import equations.*;
import exceptions.NonSquareArray;

import static java.lang.Math.PI;
import static java.lang.Math.sin;

public class StartClass {
    public static void main(String[] args) throws NonSquareArray {
        Calculation solve = new Convection(1, 2, 1, 51, 0.4, 0.4);
        solve.solveEquation();
        solve.printEquation();
        solve.printEquationInFile();
        return;
    }


}
