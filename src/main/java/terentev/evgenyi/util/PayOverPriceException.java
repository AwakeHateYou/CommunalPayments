package terentev.evgenyi.util;

/**
 * Ошибка, когда оплачено больше, чем надо.
 * @autor Терентьев Евгений
 */
public class PayOverPriceException extends Exception {
    public PayOverPriceException(){
        super("Оплачено больше, чем надо.");
    }
}
