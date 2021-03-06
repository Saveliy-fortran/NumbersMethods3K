package equations;

import com.sun.xml.internal.bind.v2.TODO;
import equations.Calculation;
import exceptions.NonSquareArray;
import methods.SweepMethod;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Math.*;

public class Diffusion implements Calculation {
    private final double L; // Длинна рассчетной области
    private final int Nx; // Количество точек разбиения
    private final double v0; // скорость на нижней стенке
    private final double vN; // скорость на верхней стенке
    private double h; // шаг по иксу
    private double dt; // шаг по времени
    private final double time; // конечное время
    private double[] v1; // первый массив для слоя
    private double[] v2; // второй массив для слоя
    private double[] x; // массив иксов
    private final double numberFonNeymana; // число Фон-Неймана
    private final double density; // Вязкозть
    private int nT; // Количество разбиений по времени
    private String Schema = "Явная";
    private double Aa;

    public Diffusion(double l, int nx, double v0, double vN, double time, double numberFonNeymana, double density, double a) {
        L = l;
        Nx = nx;
        this.v0 = v0;
        this.vN = vN;
        this.time = time;
        this.numberFonNeymana = numberFonNeymana;
        this.density = density;
        Aa = a;

        h = l/(nx-1);
        dt = numberFonNeymana*pow(h,2)/density;
        nT = (int) (time/dt);
        x = new double[nx];
        v1 = new double[nx];
        v2 = new double[nx];
    }


    public void printEquation(){
        System.out.println("Вывожу решение для уравнения диффузии:");
        System.out.println(Arrays.toString(v2));
    }

    @Override
    public void printEquationInFile() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("D:/java/IdeaProjects/NumbersMethods/src/main/resources/answer/answer.xls");
            PrinterOur printerOur = new PrinterOur(fileOutputStream);
            printerOur.printInExcelFile(x, v2, "Диффузия", Schema);
        } catch (FileNotFoundException e) {
            System.out.println("Проблемы с открытием файла");
        }
    }

    public double[] solveEquation()  {
        Scanner scanner = new Scanner(System.in);
        intitialValue();
        boundValue();
        System.out.print("Выберите схему: CR (схема Кранка-Николсона для модельного уравнения), ES(Явная схема для модельного уравнения) :\n> ");
        String output = scanner.next();
        switch (output){
            case "CR" : return solveEquationWithCrankaNicholsonForModelEquation();
            case "ES" : return solveEquationWithExplicitSchema();
            default: System.out.println("Запущена схема по умолчанию!");
        }
        return solveEquationWithExplicitSchema();
    }

    private void intitialValue(){
        x[0] = 0;
        v1[0] = 0;
        v2[0] = 0;
        for(int i = 1; i < Nx; i++){
            x[i] = x[i-1] + h;
            v1[i] = 0;
            v2[i] = 0;
        }
    }
    private void boundValue(){
        v1[Nx-1] = vN;
        v1[0] = v0;
        v2 [Nx-1] = vN;
        v2[0] = v0;
    }

    public double[] solveEquationWithExplicitSchema(){
        System.out.println("Запущена явная схема! Для модельного уравнения");
        for(int t = 0; t <= nT; t++) {
            for (int i = 1; i < Nx - 1; i++) {
                v2[i] = (density * dt / (pow(h, 2))) * (v1[i - 1] - 2 * v1[i] + v1[i + 1]) + v1[i] + Aa*dt;
            }
            for (int i = 0; i < v2.length; i++) {
                v1[i] = v2[i];
            }
        }
        for (int i = 0; i<v2.length; i++){
            v2[i] = v2[i]/vN;
            x[i] = x[i]/L;
        }
        return v2;
    }

    public double[] solveEquationWithCrankaNicholsonForModelEquation()  {
        Schema = "КранкНиколс";
        System.out.println("Запущена схема Кранка-Николсона для модельного уравнения");
        double A[] = new double[Nx];
        double B[] = new double[Nx];
        double C[] = new double[Nx];
        double D[] = new double[Nx];
        double q = dt/pow(h,2);
        for(int t = 0; t <= nT; t++){
            A[0] = 0;
            B[0] = 1;
            C[0] = 0;
            D[0] = v0;

            for (int i = 1; i < Nx - 1; i++){
                A[i] = -density*q/2;
                B[i] = 1+q*density;
                C[i] = -density*q/2;
                D[i] = v1[i] + (density*q/2)*(v1[i-1] - 2*v1[i] + v1[i+1]) + Aa*dt;
            }

            A[Nx-1] = 0;
            B[Nx-1] = 1;
            C[Nx-1] = 0;
            D[Nx-1] = vN;
            try {
                v2 = SweepMethod.sweepMethod( A, B, C, D);
            } catch (NonSquareArray e){
                System.out.println("Решение не получено, ошибка!");
            }
            for (int i = 0; i < v2.length; i++) {
                v1[i] = v2[i];
            }
        }
        for (int i = 0; i<v2.length; i++){
            v2[i] = v2[i]/vN;
            x[i] = x[i]/L;
        }
        return v2;
    }



}
