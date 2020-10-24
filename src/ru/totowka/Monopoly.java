package ru.totowka;

/**
 * @author <a href="mailto:tskocharyan@edu.hse.ru"> Tigran Kocharyan</a>
 */
public class Monopoly {
    private Field field;
    private Player[] players;
    private double creditCoef;
    private double debtCoef;
    private double penaltyCoef;

    public Field getField() {
        return field;
    }

    public Player[] getPlayers() {
        return players;
    }

    public double getCreditCoef() {
        return creditCoef;
    }

    public double getDebtCoef() {
        return debtCoef;
    }

    public double getPenaltyCoef() {
        return penaltyCoef;
    }

    public Monopoly(int width, int height, Player[] players) {
        field = new Field(width, height);
        creditCoef = Utils.randomDouble(0.002, 0.2);
        debtCoef = Utils.randomDouble(1.0, 3.0);
        penaltyCoef = Utils.randomDouble(0.01, 0.1);
        this.players = players;
    }

    /**
     * Метод для вывода текущей позиции пользователя.
     *
     * @param player
     */
    public void showCoordinates(Player player) {
        int[] coordinates = field.toDoubleDimension(player.getPosition());
        Cell cell = field.getCells()[player.getPosition()];
        System.out.printf("► %s currently is in %s at (%d,%d).\n",
                player, cell.getName(), coordinates[0], coordinates[1]);
    }

    /**
     * Метод для вызова печати поля.
     */
    public void showField() {
        field.showField();
    }

    /**
     * Метод для реализации хода игрока.
     *
     * @param player
     */
    public void turn(Player player) {
        int diceResult = throwDice();
        System.out.printf("☺ %s throws the dice 2 times! In total he got %d points.\n",
                player, diceResult);
        player.move(diceResult, this.field.getCellsAmount());
        field.getCells()[player.getPosition()].enter(player, this);
    }

    /**
     * Методя для реализации окончания игры. Если один из игроков - банкрот,
     * то выводится соответствующая информация.
     */
    public void finishGame() {
        if (players[0].isBankrupt()) {
            System.out.printf("Game has finished! \n(；･ω･)ア %s is a winner!\n" +
                            "There are %d$ in his wallet and %d$ of worth (also %d$ of debt (^.^)/\n",
                    players[1], players[1].getMoney(), players[1].getWorth(), players[1].getDebt());
        } else {
            System.out.printf("Game has finished! \n(；･ω･)ア %s is a winner!\n" +
                            "There are %d$ in his wallet and %d$ of worth (also %d$ of debt (^.^)/\n",
                    players[0], players[0].getMoney(), players[0].getWorth(), players[0].getDebt());
        }
        Utils.exit("See you next time! (^_~)");
    }

    /**
     * Метод для реализации получения суммы двух рандомных чисел от 1 до 6.
     *
     * @return
     */
    public static int throwDice() {
        return Utils.randomInt(1, 7) + Utils.randomInt(1, 7);
    }
}
