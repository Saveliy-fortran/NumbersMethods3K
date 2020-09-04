package exceptions;
/*
    Класс для исключения неквадратного массива
 */
public class NonSquareArray extends Exception {
    public NonSquareArray(String message) {
        super(message);
    }
}
