package equations;

import exceptions.NonSquareArray;

public interface Calculation {
    double[] solveEquation() throws NonSquareArray;
    void printEquation();
}
