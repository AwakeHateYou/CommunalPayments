package terentev.evgenyi.util;

/**
 * Ошибка, при внесении в базу пустого поля.
 */
public class EmptyFieldException extends Exception {
    public EmptyFieldException(){
        super("Одно из ваших полей пусто");
    }
}
