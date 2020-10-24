package ru.totowka;

/**
 * @author <a href="mailto:tskocharyan@edu.hse.ru"> Tigran Kocharyan</a>
 * Класс для запуска игры.
 */
public class GameStart {
    /**
     * Метод для проверки аргументов переданных из консоли.
     * При их корректности, создает объект Monopoly и выбирает очередность ходьбы игроков.
     *
     * @param args
     */
    public static void main(String[] args) {
        Monopoly monopoly = checkParams(args);
        Player[] players = monopoly.getPlayers();
        greetingMessage(players, monopoly);
        Utils.wait(1000);

        System.out.println("○ The starting field looks like this:");
        monopoly.showField();
        Utils.wait(1000);
        System.out.println("\n☺ You both start at cell (0,0).\nHave a nice game! (^_~)");
        Utils.wait(1000);

        start(players, monopoly);
    }

    /**
     * Метод для запуска начала игры. По ходу игры выводит сообщения-оповещения.
     *
     * @param players
     * @param monopoly
     */
    public static void start(Player[] players, Monopoly monopoly) {
        // В данном случае игры на 2, isFinished == true, если один пользователь банкрот.
        boolean isFinished = false;
        int turn = 1;
        while (!isFinished) {
            System.out.printf("● Now it is %d turn.\n\n", turn);
            for (Player player : players) {
                monopoly.turn(player);
                Utils.wait(2000);
            }
            monopoly.showField();
            Utils.wait(1000);
            System.out.printf("\n● %d turn has finished.\n", turn++);
            for (Player player : players) {
                monopoly.showCoordinates(player);
                System.out.printf("\t%s has %d$ cash with %d$ of total worth and %d$ debt!\n",
                        player, player.getMoney(), player.getWorth(), player.getDebt());
                if (player.isBankrupt()) {
                    isFinished = true;
                    monopoly.finishGame();
                }
            }
            System.out.print("\nPress ENTER to continue:");
            Utils.scanner.nextLine();
        }
    }

    /**
     * Метод проверяет входные данные на соответствие границам.
     *
     * @param args
     * @return
     */
    public static Monopoly checkParams(String[] args) {
        if (args.length != 3) {
            Utils.exit("Not enough params. Try again with correct data :(");
        }
        int[] params = new int[args.length];
        try {
            for (int i = 0; i < params.length; i++) {
                params[i] = Integer.parseInt(args[i]);
            }
        } catch (NumberFormatException ex) {
            Utils.exit("The params should be Integers. Try again with correct data :(");
        }

        if (!Utils.checkBorders(params[0], 6, 30)) {
            Utils.exit("Height should be >= 6 and <= 30 :(");
        }
        if (!Utils.checkBorders(params[1], 6, 30)) {
            Utils.exit("Width should be >= 6 and <= 30 :(");
        }
        if (!Utils.checkBorders(params[2], 500, 15000)) {
            Utils.exit("Cash should be >= 500 and <= 15000 :(");
        }
        Player[] players = rollSequence(2, params[2]);
        return new Monopoly(params[1], params[0], players);
    }

    /**
     * Вывод приветственного сообщения с коэф-ами и информации об очередности хода.
     *
     * @param players
     * @param game
     */
    public static void greetingMessage(Player[] players, Monopoly game) {
        System.out.printf("\t\tWELCOME TO THE MONOPOLY!\n" +
                        "The coefficients and values for this game:\n" +
                        "● Starting money capital: " + 1500 + "$.\n" +
                        "● Credit Coef: %.2f.\n" +
                        "● Debt Coef: %.2f.\n" +
                        "● Penalty Coef: %.2f.\n\n",
                game.getCreditCoef(), game.getDebtCoef(), game.getPenaltyCoef());
        Utils.wait(1000);
        System.out.println("Now we roll the dice: \n" +
                "☺ " + players[0] + " starts the game!\n");
    }

    /**
     * Метод для выбора очередности хода. Если рандом выдал 1, то у dicePlayer будет 0.
     * Аналогично и для нуля. Затем эти числа являются индексами массива.
     *
     * @param size
     * @param money
     * @return
     */
    public static Player[] rollSequence(int size, int money) {
        int diceBot = Utils.randomInt(0, 2);
        int dicePlayer = 1 - diceBot;
        Player[] players = new Player[size];
        players[diceBot] = new BotPlayer(money);
        players[dicePlayer] = new SoulPlayer(money);
        return players;
    }
}
