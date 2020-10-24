package ru.totowka;

/**
 * @author <a href="mailto:tskocharyan@edu.hse.ru"> Tigran Kocharyan</a>
 */
public class PenaltyCell extends Cell {
    public PenaltyCell() {
        super("%");
    }

    /**
     * Метод для активации действия клетки.
     * Выводит информацию о штрафе и проверяет на банкротство.
     *
     * @param player
     * @param monopoly
     */
    public void enter(Player player, Monopoly monopoly) {
        monopoly.showCoordinates(player);
        int penalty = Utils.round(monopoly.getPenaltyCoef() * player.getMoney());
        System.out.printf("○ %s has got penalty of %d$.\n\n", player, penalty);
        player.pay(penalty);
        if (player.isBankrupt()) {
            monopoly.finishGame();
        }
    }

    /**
     * Возвращает полное название клетки.
     *
     * @return
     */
    @Override
    public String getName() {
        return "Penalty Cell";
    }
}
