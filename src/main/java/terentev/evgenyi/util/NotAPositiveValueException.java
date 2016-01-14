package terentev.evgenyi.util;

/**
 * Ошибка при вводе отрицательных значений.
 */
public class NotAPositiveValueException extends Exception {
    public NotAPositiveValueException(){
        super("Введено отрицательное значение");
    }
}
