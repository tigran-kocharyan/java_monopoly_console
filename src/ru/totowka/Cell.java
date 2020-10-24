package ru.totowka;

/**
 * @author <a href="mailto:tskocharyan@edu.hse.ru"> Tigran Kocharyan</a>
 */
public abstract class Cell {
    private String name;

    public Cell(String name) {
        this.name = name;
    }

    /**
     * Метод реализует попадание на клетку.
     *
     * @param player
     * @param monopoly
     */
    public abstract void enter(Player player, Monopoly monopoly);

    /**
     * Метод возвращает название магазина.
     *
     * @return
     */
    public abstract String getName();

    /**
     * Переопределение toString() для корректного вывода игрового поля.
     *
     * @return
     */
    @Override
    public String toString() {
        return name;
    }
}
