package equations;

import exceptions.NonSquareArray;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Function;

public class BurgersEquation implements Calculation {
    private final int time;
    private final double L;
    private final int nX;
    private final double dt;
    private double[] u1;
    private double[] u2;
    private double[] r;
    private double h;
    private final double Currant;
    private Function<Double, Double> initialStartValue;
    private String Schema = "Явная";

    public BurgersEquation( double l, int nX, int time, double currant, Function<Double, Double> initialStartValue) {
        this.time = time;
        L = l;
        this.nX = nX;
        this.dt = currant*l/(nX-1);
        Currant = currant;
        this.initialStartValue = initialStartValue;

        h = l/(nX-1);
        u1 = new double[nX];
        u2 = new double[nX];
        r = new double[nX];

    }

    @Override
    public double[] solveEquation() throws NonSquareArray {
        Scanner scanner = new Scanner(System.in);
        intitialValue();
        System.out.print("Выберите схему: LV (Cхема Лакса-Вендроффа), ES(Явная схема), TT(Точное решение) :\n> ");
        String output = scanner.next();
        switch (output){
            case "LV" : return solveEquationWithLax_WendroffScheme();
            case "ES" : return solveEquationWithExplicitSchema();
            case "TT" : return tochnoeSolution();
            default: System.out.println("Запущена схема по умолчанию!");
        }
        return solveEquationWithExplicitSchema();
    }

    public double[] tochnoeSolution(){
        double timer = dt*time;
        for(int i = 0; i < nX; i++){
            if((r[i] - L/2)/timer <= 1){
                u2[i] = 1;
            } else if ((r[i] - L/2)/timer > 1 && (r[i] - L/2)/timer < 2) {
                u2[i] = (r[i] - L/2)/timer;
            } else if((r[i] - L/2)/timer >= 2){
                u2[i] = 2;
            }
        }
        return u2;
    }

    @Override
    public void printEquation() {
        System.out.println("Вывожу решение для уравнения Бюргерса:");
        System.out.println(Arrays.toString(u2));
    }

    @Override
    public void printEquationInFile() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("D:/java/IdeaProjects/NumbersMethods/src/main/java/equations/answer.xls");
            PrinterOur printerOur = new PrinterOur(fileOutputStream);
            printerOur.printInExcelFile(r, u2, "Бургерс", Schema);
        } catch (FileNotFoundException e) {
            System.out.println("Проблемы с открытием файла");
        }
    }

    public double[] solveEquationWithExplicitSchema(){
        System.out.println("Запущена явная схема:");
        for (int t = 0; t < time; t++){
            for(int i = 1; i < nX-1; i++){
                if(u1[i]>0){
                    u2[i] = u1[i] - dt / (2 * h) * (u1[i]*u1[i] - u1[i-1]*u1[i-1]);
                } else {
                    u2[i] = u1[i] - dt / (2 * h) * (u1[i + 1]*u1[i + 1] - u1[i]*u1[i]);
                }
            }
            u2[0] = 1;
            u2[nX-1] = 2;

            for(int i = 0; i < nX; i++){
                u1[i] = u2[i];
            }
        }
        return u2;
    }

    public double[] solveEquationWithLax_WendroffScheme(){
        Schema = "Лакса-Вендорфа";
        double[] v12 = new double[nX];
        for (int t = 0; t < time; t++){
            for(int j = 0; j < nX - 1; j++){
                v12[j] = 0.5*(0.5*u1[j+1]*u1[j+1] + 0.5*u1[j]*u1[j]) - 0.5*(dt/h)*(u1[j+1] - u1[j]);
            }
            for (int i = 1; i < nX; i++){
                u2[i] = u1[i] - (dt/h)*(v12[i] - v12[i-1]);
            }
            u2[0] = 1;
            u2[nX-1] = 2;
            for (int j = 0; j < nX; j++){
                u1[j] = u2[j];
            }
        }
        return u2;
    }

    public void intitialValue(){
        r[0] = 0;
        for(int i = 1; i < nX; i++){
            r[i] = r[i-1] + h;
        }

        for (int i = 0; i < nX; i++){
            u1[i] = initialStartValue.apply(r[i]);
        }
    }
}
