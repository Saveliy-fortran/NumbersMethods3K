import equations.*;
import exceptions.NonSquareArray;

import static java.lang.Math.PI;
import static java.lang.Math.sin;

public class StartClass {
    public static void main(String[] args) throws NonSquareArray {
        Calculation solve = new Convection(1, 8,1, 101, 0.5, 2);
        solve.solveEquation();
        solve.printEquation();
        solve.printEquationInFile();
        //System.out.println("Значение в ядре " + (-Math.cos(34)));
        return;
    }


}
