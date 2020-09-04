package equations;

import static java.lang.Math.*;

public class Convection implements Calculation {
    private final double L;
    private final double m;
    private final double C;
    private final double C0, C1;
    private final int nX;
    private final int nT;
    private final double CFL;
    private double[] u;
    private double[] uN;
    private double[] x;

    public Convection(double l, double m, double c, double c0, double c1, int nX, int nT, double cfl) {
        L = l;
        this.m = m;
        C = c;
        C0 = c0;
        C1 = c1;
        this.nX = nX;
        this.nT = nT;
        CFL = cfl;
        u = new double[nX];
        uN = new double[nX];
        x = new double[nX];
    }


    public double[] solveEquation() {
        return new double[0];
    }

     public void printEquation() {

    }
    private void intitialValue(){
        double h = L/(nX-1);
        x[0] = 0;
        for (int i = 1; i < nX; i++){
            x[i] = x[i-1] + h;
        }

        for(int i = 0; i < nX; i++){
            u[i] = initialFunction(x[i]);
        }
    }
    private double[] explicitFirstOrderAntiFlowScheme(){
        return new double[1];
    }

    private double initialFunction(double x){
        return sin(m*PI/L * x);
    }
}
