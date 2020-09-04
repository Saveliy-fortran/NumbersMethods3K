package equations;

import exceptions.NonSquareArray;
import methods.SweepMethod;

import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Math.pow;
import static java.lang.Math.sin;

public class DiffusionOurEquation implements Calculation{
    private final double L; // Длинна рассчетной области
    private final int Nx; // Количество точек разбиения
    private double h; // шаг по иксу
    private double dt; // шаг по времени
    private final double time; // конечное время
    private double[] v1; // первый массив для слоя
    private double[] v2; // второй массив для слоя
    private double[] x; // массив иксов
    private final double density; // Вязкозть
    private int nT; // Количество разбиений по времени
    private final double w; // Скорость в синусе
    private final double numberFonNeymana; // число Фон-Неймана
    private double[] r;

    public DiffusionOurEquation(double l, int nx, double time, double density, double w, double numberFonNeymana) {
        L = l;
        Nx = nx;
        this.time = time;
        this.density = density;
        this.w = w;
        this.numberFonNeymana = numberFonNeymana;

        h = l/(nx-1);
        dt = this.numberFonNeymana *pow(h,2)/density;
        nT = (int) (time/dt);
        x = new double[nx];
        v1 = new double[nx];
        v2 = new double[nx];
        r = new double[nx];
    }

    public double[] solveEquationWithCrankaNicholsonForStocksEquation() {
        System.out.println("Запущена схема Кранка-Николсона для модельного уравнения");
        double A[] = new double[Nx];
        double B[] = new double[Nx];
        double C[] = new double[Nx];
        double D[] = new double[Nx];
        for (int t = 0; t <= nT; t++) {
            A[0] = -1;
            B[0] = 1;
            C[0] = 0;
            D[0] = 0;
            for (int i = 1; i < Nx - 1; i++) {
                A[i] = -density / pow(2 * h, 2) * (r[i] + r[i+1]);
                B[i] = r[i]/dt + density/pow(2*h,2) * (r[i+1] + 2*r[i] + r[i-1]);
                C[i] = -density/pow(2*h,2) * (r[i] + r[i-1]);
                D[i] = r[i] * sin(w * dt * t) + density/pow(2*h,2) * ( (r[i+1] + r[i])*(v1[i+1] - v1[i]) - (r[i] + r[i-1])*(v1[i] - v1[i-1])) + r[i]*v1[i]/dt;
            }
            A[Nx - 1] = 0;
            B[Nx - 1] = 1;
            C[Nx - 1] = 0;
            D[Nx - 1] = 0;
            try {
                v2 = SweepMethod.sweepMethod(Nx, A, B, C, D);
            } catch (NonSquareArray e) {
                System.out.println("Решение не получено, ошибка!");
            }
            for (int i = 0; i < v2.length; i++) {
                v1[i] = v2[i];
            }
        }
        return v2;
    }

    @Override
    public double[] solveEquation() throws NonSquareArray {
        Scanner scanner = new Scanner(System.in);
        intitialValue();
        //boundValue();
        return solveEquationWithCrankaNicholsonForStocksEquation();
    }

    private void intitialValue() {
        x[0] = 0;
        v1[0] = 0;
        v2[0] = 0;
        r[0] = 0;
        for(int i = 1; i < Nx; i++){
            x[i] = x[i-1] + h;
            v1[i] = 0;
            v2[i] = 0;
            r[i] = r[i-1] + h;
        }
    }


    @Override
    public void printEquation() {
        System.out.println("Вывожу решение для уравнения диффузии:");
        System.out.println(Arrays.toString(v2));
        return;
    }
}
