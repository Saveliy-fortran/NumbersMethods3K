package methods;

import exceptions.NonSquareArray;

public class SweepMethod {



    public static double[] sweepMethod(double[] a, double[] b, double[] c, double[] d) throws NonSquareArray {
        int n = a.length;
        if (b.length != n ||c.length != n ||d.length != n ) {
            throw new NonSquareArray("Массивы не соответсвуют!");
        }
        double[] answer = new double[n];
        double[] aa = new double[n];
        double[] bb = new double[n];

        aa[0] = -c[0]/b[0];
        bb[0] = d[0]/b[0];

        for (int i = 1; i <= n-1; i++){
            aa[i] = -c[i]/( b[i]+a[i]*aa[i-1]);
            bb[i] = (d[i] - a[i]*bb[i-1])/(b[i]+a[i]*aa[i-1]);
        }

        answer[n-1] = bb[n-1];

        for (int i = n-2; i>=0; i--){
            answer[i] = aa[i]*answer[i+1] + bb[i];
        }

        return answer;
    }

}
