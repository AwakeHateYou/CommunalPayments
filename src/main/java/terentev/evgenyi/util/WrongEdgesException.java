package terentev.evgenyi.util;

/**
 * Ошибка с границами.
 * @author Терентьев Евгений
 */
public class WrongEdgesException extends Exception {
    public WrongEdgesException(){
        super("Заданы ошибочные границы.");
    }
}
