package ru.totowka;

/**
 * @author <a href="mailto:tskocharyan@edu.hse.ru"> Tigran Kocharyan</a>
 */
public class TaxiCell extends Cell {
    public TaxiCell() {
        super("T");
    }

    /**
     * Если игрок наступает на клетку такси, то сдвигается на <code>distance</code> клеток вперед
     * и активирует действие этой клетки.
     *
     * @param player
     * @param monopoly
     */
    public void enter(Player player, Monopoly monopoly) {
        int distance = Utils.randomInt(3, 6);
        monopoly.showCoordinates(player);
        player.move(distance, monopoly.getField().getCellsAmount());
        System.out.printf("%s is shifted -> forward by %d!\n", player, distance);
        monopoly.getField().getCells()[player.getPosition()].enter(player, monopoly);
    }

    /**
     * Возврат полного названия клетки такси.
     *
     * @return
     */
    @Override
    public String getName() {
        return "Taxi Cell";
    }
}
