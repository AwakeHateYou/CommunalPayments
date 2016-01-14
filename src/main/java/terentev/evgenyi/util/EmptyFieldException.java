package terentev.evgenyi.util;

/**
 * Ошибка, при внесении в базу пустого поля.
 * @autor Терентьев Евгений
 */
public class EmptyFieldException extends Exception {
    public EmptyFieldException(){
        super("Одно из ваших полей пусто");
    }
}
