package terentev.evgenyi.util;

/**
 * Ошибка с границами.
 */
public class WrongEdgesException extends Exception {
    public WrongEdgesException(){
        super("Заданы ошибочные границы.");
    }
}
