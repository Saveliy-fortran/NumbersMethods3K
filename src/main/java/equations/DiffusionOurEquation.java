package equations;

import exceptions.NonSquareArray;
import methods.SweepMethod;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    private final double Aa;
    private String Schema = "Явная";

    public DiffusionOurEquation(double l, int nx, double time, double density, double w, double numberFonNeymana, double a) {
        L = l;
        Nx = nx;
        this.time = time;
        this.density = density;
        this.w = w;
        this.numberFonNeymana = numberFonNeymana;
        Aa = a;

        h = l/(nx-1);
        dt = this.numberFonNeymana *pow(h,2)/density;
        nT = (int) (time/dt);
        x = new double[nx];
        v1 = new double[nx];
        v2 = new double[nx];
        r = new double[nx];
    }

    public double[] solveEquationWithCrankaNicholsonForStocksEquation() {
        Schema = "Кранк";
        System.out.println("Запущена схема Кранка-Николсона для Обратная вторая задача Стокса в цилиндрической трубе ");
        double A[] = new double[Nx];
        double B[] = new double[Nx];
        double C[] = new double[Nx];
        double D[] = new double[Nx];
        for (int t = 1; t <= nT; t++) {
            A[0] = 0;
            B[0] = -1;
            C[0] = 1;
            D[0] = 0;
            for (int i = 1; i < Nx - 1; i++) {
                A[i] = -density * dt * (r[i] + r[i-1])/(4*r[i]*h*h);
                B[i] = 1  +  density * dt/(4*r[i]*h*h) * (r[i+1]+2*r[i]+r[i-1]);
                C[i] = -density * dt * (r[i+1]+r[i])/(4*r[i]*h*h);
                D[i] = Aa*dt*sin(w*dt*t) + (density * dt/(4*r[i]*h*h)) * ((r[i]+r[i-1])*(v1[i]-v1[i-1]) - (r[i+1]+r[i])*(v1[i+1]-v1[i]));
            }
            A[Nx - 1] = 0;
            B[Nx - 1] = 1;
            C[Nx - 1] = 0;
            D[Nx - 1] = 0;
            try {
                v2 = SweepMethod.sweepMethod( A, B, C, D);
            } catch (NonSquareArray e) {
                System.out.println("Решение не получено, ошибка!");
            }
            for (int i = 0; i < v2.length; i++) {
                v1[i] = v2[i];
            }
        }
        return v2;
    }

    public double[] solveEquationWithExplicitSchema(){
        System.out.println("Запущена явная схема! Для Обратная вторая задача Стокса в цилиндрической трубе  ");
        double t = dt;
        for(int n = 1; n <= nT; n++) {
            for (int i = 1; i < Nx - 1; i++) {
                v2[i] = v1[i] + density*dt/(2*r[i]*h*h) * ((r[i+1]+r[i])*(v1[i+1]-v1[i]) - (r[i]+r[i-1])*(v1[i]-v1[i-1])) + dt*Aa*sin(t*w);
            }
            v2[0]=v2[1];
            for (int i = 0; i < v2.length; i++) {
                v1[i] = v2[i];
            }
            t=t+dt;
        }
        return v2;
    }

    @Override
    public double[] solveEquation() throws NonSquareArray {
        Scanner scanner = new Scanner(System.in);
        intitialValue();
        System.out.println("Обратная вторая задача Стокса в цилиндрической трубе:");
        System.out.print("Выберите схему: CR (схема Кранка-Николсона), ES(Явная схемая) :\n> ");
        String output = scanner.next();
        switch (output){
            case "CR" : return solveEquationWithCrankaNicholsonForStocksEquation();
            case "ES" : return solveEquationWithExplicitSchema();
            default: System.out.println("Запущена схема по умолчанию!");
        }
        return solveEquationWithExplicitSchema();
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

    @Override
    public void printEquationInFile() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("D:/java/IdeaProjects/NumbersMethods/src/main/resources/answer/answer.xls");
            PrinterOur printerOur = new PrinterOur(fileOutputStream);
            printerOur.printInExcelFile(x, v2, "ОбрЗачСтокса", Schema);
        } catch (FileNotFoundException e) {
            System.out.println("Проблемы с открытием файла");
        }
    }
}
