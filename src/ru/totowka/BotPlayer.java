package ru.totowka;

/**
 * @author <a href="mailto:tskocharyan@edu.hse.ru"> Tigran Kocharyan</a>
 * Класс Бота. Играет как пользователь, только не работает с банком.
 */
public class BotPlayer extends Player {
    public BotPlayer(int money) {
        super(money, 0);
    }

    /**
     * Переопределенный метод для возврата обозначения бота.
     *
     * @return
     */
    @Override
    public String toString() {
        return "Bot Player";
    }
}
