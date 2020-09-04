package methods;

import exceptions.NonSquareArray;

public class SweepMethod {

    public static double[] sweepMethod(double[][] array, double[] rightPart) throws NonSquareArray {
        double[] A = new double[array.length]; // диагональ под главной
        double[] B = new double[array.length]; // диагональ главная
        double[] C = new double[array.length]; // диагональ над главной
        double[] x;
        if (array.length != rightPart.length || array[0].length != array.length){
            throw new NonSquareArray("Массив не квадратный");
        }
        A[0] = 0;
        for (int i = 1; i<array.length; i++){
            A[i] = array[i][i-1];
        }
        for (int i = 0; i < array.length-1; i++){
            C[i] = array[i][i+1];
        }
        C[array.length-1] = 0;
        for(int i = 0; i < array.length; i++){
            B[i] = array[i][i];
        }
        x = sweepMethod(array.length, A, B, C, rightPart);
        return x;
    }
    /**
     * n - число уравнений (строк матрицы)
     * b - диагональ, лежащая над главной (нумеруется: [0;n-2])
     * c - главная диагональ матрицы A (нумеруется: [0;n-1])
     * a - диагональ, лежащая под главной (нумеруется: [1;n-1])
     * f - правая часть (столбец)
     * x - решение, массив x будет содержать ответ
     */
    public static double[] sweepMethod(int n, double[] a, double[] c, double[] b, double[] f) throws NonSquareArray {
        if (a.length != n || b.length != n ||c.length != n ||f.length != n ) {
            throw new NonSquareArray("Массивы не соответсвуют!");
        }
        double m;
        double[] x = new double[n];
        for (int i = 1; i < n; i++)
        {
            m = a[i]/c[i-1];
            c[i] = c[i] - m*b[i-1];
            f[i] = f[i] - m*f[i-1];
        }

        x[n-1] = f[n-1]/c[n-1];

        for (int i = n - 2; i >= 0; i--)
        {
            x[i]=(f[i]-b[i]*x[i+1])/c[i];
        }
        return x;
    }

}
