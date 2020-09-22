import equations.*;
import exceptions.NonSquareArray;

import static java.lang.Math.PI;
import static java.lang.Math.sin;

public class StartClass {
    public static void main(String[] args) throws NonSquareArray {
        Calculation solve = new DiffusionOurEquation(1, 41, 34, 0.02, 1, 0.5, 1);
        solve.solveEquation();
        solve.printEquation();
        solve.printEquationInFile();
        System.out.println("Значение в ядре " + (-Math.cos(34)));
        return;
    }


}
