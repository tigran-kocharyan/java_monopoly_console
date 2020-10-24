package ru.totowka;

/**
 * @author <a href="mailto:tskocharyan@edu.hse.ru"> Tigran Kocharyan</a>
 */
public class SoulPlayer extends Player {
    public SoulPlayer(int money) {
        super(money, 0);
    }

    /**
     * Переопределенный метод для возврата обозначения живого игрока.
     *
     * @return
     */
    @Override
    public String toString() {
        return "Human Player";
    }
}
