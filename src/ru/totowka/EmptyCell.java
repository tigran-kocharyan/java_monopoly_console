package ru.totowka;

/**
 * @author <a href="mailto:tskocharyan@edu.hse.ru"> Tigran Kocharyan</a>
 */
public class EmptyCell extends Cell {
    public EmptyCell() {
        super("E");
    }

    /**
     * Попадание на пустую клетку.
     *
     * @param player
     * @param monopoly
     */
    public void enter(Player player, Monopoly monopoly) {
        monopoly.showCoordinates(player);
        System.out.println("○ That's the empty cell! " +
                "Just relax there (^_~)\n");
    }

    /**
     * Переопределенный возврат полного имени.
     *
     * @return
     */
    @Override
    public String getName() {
        return "Empty Cell";
    }
}
