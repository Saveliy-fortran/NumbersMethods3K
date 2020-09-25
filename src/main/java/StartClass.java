import equations.*;
import exceptions.NonSquareArray;

import static java.lang.Math.PI;
import static java.lang.Math.sin;

public class StartClass {
    public static void main(String[] args) throws NonSquareArray {
        Calculation solve = new BurgersEquation(1, 201, 100, 0.1, x -> startEquaction(x));
        solve.solveEquation();
        solve.printEquation();
        solve.printEquationInFile();
        //System.out.println("Значение в ядре " + (-Math.cos(34)));
        return;
    }

    public static double startEquaction(double x){
        return x > 0.5 ? 2 : 1;
    }
}
