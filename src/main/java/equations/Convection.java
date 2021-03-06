package equations;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Math.*;

public class Convection implements Calculation {
    private final double L; //длинна
    private final double m; // в синусе
    private final double C; // c
    private final double CFL; //Число куранта
    private double dt;//шаг по времени
    private final int nX; // количество разбиений
    private int nT; // количество шагов по времени
    private double h;
    private double[] u1;
    private double[] u2;
    private double[] x;
    private String Schema = "Явная схема";

    public Convection(double l, double m, double c, int nX, double cfl, double time) {
        L = l;
        h = l/(nX - 1);
        this.m = m;
        C = c;
        dt = (cfl * l/(nX - 1))/c;
        CFL = cfl;
        this.nT =(int)(time/dt);
        this.nX = nX;
        u1 = new double[nX];
        u2 = new double[nX];
        x = new double[nX];
        System.out.println((int)(time/dt));
    }


    public double[] solveEquation() {
        Scanner scanner = new Scanner(System.in);
        intitialValue();
        System.out.print("Выберите схему: LV (Cхема Лакса-Вендроффа), ES(Явная схема) :\n> ");
        String output = scanner.next();
        switch (output){
            case "LV" : return solveEquationWithLax_WendroffScheme();
            case "ES" : return solveEquationWithExplicitSchema();
            default: System.out.println("Запущена схема по умолчанию!");
        }
        return solveEquationWithExplicitSchema();
    }

    public void printEquation() {
         System.out.println("Вывожу решение для уравнения конвекции:");
         System.out.println(Arrays.toString(u2));
    }

    @Override
    public void printEquationInFile() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("D:/java/IdeaProjects/NumbersMethods/src/main/java/equations/answer.xls");
            PrinterOur printerOur = new PrinterOur(fileOutputStream);
            printerOur.printInExcelFile(x, u2, "конвекция", Schema);
        } catch (FileNotFoundException e) {
            System.out.println("Проблемы с открытием файла");
        }
    }

    private void intitialValue(){
        x[0] = 0;
        for (int i = 1; i < nX; i++){
            x[i] = x[i-1] + h;
        }

        for(int i = 0; i < nX; i++){
            u1[i] = initialFunction(x[i]);
        }
    }

    public double[] solveEquationWithExplicitSchema(){
        System.out.println("Запущена явная схема:");
        for (int n = 1; n <= nT; n++){
            for (int i = 1; i <= nX -1; i++){
                u2[i] = u1[i] - (C*dt/h)*(u1[i]-u1[i-1]);
            }
            u2[0] = u2[nX-1];
            for (int i = 0; i < nX; i++){
                u1[i] = u2[i];
            }
        }
        return u2;
    }

    public double[] solveEquationWithLax_WendroffScheme(){
        Schema = "Лакса-Вендорфа";
        double[] v12 = new double[nX];
        for (int n = 1; n <= nT; n++){
                for(int j = 0; j < nX - 1; j++){
                    v12[j] = 0.5*(u1[j+1] + u1[j]) - (C*dt/h)*(u1[j+1] - u1[j])/2.0;
                }
            for (int i = 1; i < nX-1; i++){
                u2[i] = u1[i] - CFL*(v12[i] - v12[i-1]);
            }
            u2[nX-1] = (u2[1]+u2[nX-2])/2;
            u2[0] = u2[nX-1];
            for (int p = 0; p < nX; p++){
                u1[p] = u2[p];
            }
        }
        return u2;
    }


    private double initialFunction(double x){
        return sin(m*PI/L * x);
    }
}
