package terentev.evgenyi.util;

/**
 * Ошибка при вводе отрицательных значений.
 * @autor Терентьев Евгений
 */
public class NotAPositiveValueException extends Exception {
    public NotAPositiveValueException(){
        super("Введено неположительное значение");
    }
}
