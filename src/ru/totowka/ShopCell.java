package ru.totowka;

/**
 * @author <a href="mailto:tskocharyan@edu.hse.ru"> Tigran Kocharyan</a>
 */
public class ShopCell extends Cell {
    private final double improvementCoef;
    private final double compensationCoef;
    private int currentPrice;
    private int currentProfit;
    private int currentUpgrade;

    private Player holder = null;

    public ShopCell() {
        super("S");
        currentPrice = Utils.randomInt(50, 501);
        currentProfit = Utils.round(Utils.randomDouble(0.5, 0.9) * currentPrice);
        improvementCoef = Utils.randomDouble(0.1, 2);
        compensationCoef = Utils.randomDouble(0.1, 1);
        currentUpgrade = Utils.round(improvementCoef * currentPrice);
    }

    /**
     * Если живого игрок владеет магазином, то он его улучшает.
     * Если игрок попадает на купленным другим игроком магазин, то он оплачивает компенсацию.
     * Если магазином никто не владеет, то игра предлагает игроку купить его.
     *
     * @param player
     * @param monopoly
     */
    public void enter(Player player, Monopoly monopoly) {
        monopoly.showCoordinates(player);
        if (holder == null) {
            if (player instanceof BotPlayer) {
                purchaseBot(player);
            } else {
                purchasePlayer(player);
            }
        } else if (holder == player) {
            if (player instanceof BotPlayer) {
                upgradeBot(player);
            } else {
                upgradePlayer(player);
            }
        } else {
            if (player instanceof BotPlayer) {
                compensateBot(player);
            } else {
                compensatePlayer(player);
            }
        }
        if (player.isBankrupt()) {
            monopoly.finishGame();
        }
    }

    /**
     * Метод улучшения магазина. Увеличивает текущую прибыль, стоимость и цену улучшения.
     */
    private void upgrade() {
        holder.buy(currentUpgrade);
        currentPrice = currentPrice + Utils.round(improvementCoef * currentPrice);
        currentProfit = currentProfit + Utils.round(compensationCoef * currentProfit);
        currentUpgrade = Utils.round(improvementCoef * currentPrice);
    }

    /**
     * Предоставляет игроку выбрать - улучшать магазин или нет.
     * Если да, то вызывает upgrade().
     * Если нет, то выводит соот-ее сообщение.
     *
     * @param player
     */
    public void upgradePlayer(Player player) {
        if (player.isEnough(currentUpgrade)) {
            System.out.printf("⌂ Shop is already yours. Upgrade cost is %d$ (^_~).\n\n",
                    currentUpgrade);
            Utils.wait(1000);
            boolean choice =
                    Utils.choice("Would you like to upgrade it? (yes/no)", "yes", "no");
            if (choice) {
                System.out.printf("⌂ Great! __ф(. . ) " +
                        "We upgraded it for %d$\n\n", currentPrice);
                upgrade();
            } else {
                System.out.println("⌂ Up to you :(\n");
            }
        } else {
            System.out.println("⌂ You have no money :( You better skip it!\n");
        }
    }

    /**
     * Бот на основе рандома бот делает решение
     * Если да, то улучшает магазин.
     * Если нет, то выводит соот-ее сообщение.
     *
     * @param player
     */
    public void upgradeBot(Player player) {
        if (player.isEnough(currentUpgrade)) {
            if (Utils.randomBoolean()) {
                System.out.printf("⌂ Nice! __ф(. . ) " +
                        "Bot upgraded the shop for only %d$\n\n", currentPrice);
                upgrade();
            } else {
                System.out.println("⌂ Bot rejected to upgrade the shop :(\n");
            }
        } else {
            System.out.println("⌂ Bot has no money for it :(\n");
        }
    }

    /**
     * В случае покупки магазина, делает текущего игрока владельцем.
     * Также вызывает метод <code>buy()</code> класса <code>PLayer</code>.
     *
     * @param player
     */
    private void purchase(Player player) {
        holder = player;
        holder.buy(currentPrice);
    }

    /**
     * Выбор живого игрока по покупке магазина.
     *
     * @param player
     */
    public void purchasePlayer(Player player) {
        if (player.isEnough(currentPrice)) {
            System.out.println("⌂ The shop has no owner (o_O)\n");
            Utils.wait(1000);
            System.out.printf("Current shop price is %d$ (^_~).\n" +
                            "Its Improvement Coef is %.3f and Compensation Coef is %.3f.\n",
                    currentPrice, improvementCoef, compensationCoef);
            boolean choice = Utils.choice("Would you like to buy it? (yes/no)",
                    "yes", "no");
            if (choice) {
                purchase(player);
                System.out.printf("⌂ Great! __ф(. . ) Shop is yours for only %d$.\n" +
                                "Now your worth is %d$ and your cash is %d$.\n\n",
                        currentPrice, player.getWorth(), player.getMoney());
            } else {
                System.out.println("⌂ Up to you :(\n");
            }
        } else {
            System.out.println("⌂ You have no money :( You better skip it!\n");
        }
    }

    /**
     * Выбор бота по покупке магазина.
     *
     * @param player
     */
    public void purchaseBot(Player player) {
        if (player.isEnough(currentPrice)) {
            if (Utils.randomBoolean()) {
                purchase(player);
                System.out.printf("⌂ Nice! __ф(. . ) Bot purchased the shop for only %d$\n\n" +
                                "Now Bots worth is %d$ and Bots cash is %d$.\n",
                        currentPrice, player.getWorth(), player.getMoney());
            } else {
                System.out.println("⌂ Bot rejected to purchase the shop :(\n");
            }
        } else {
            System.out.println("⌂ Bot has no money on it :(\n");
        }
    }

    /**
     * Компенсация живого игрока в случае попадания на клетку-магазин бота.
     *
     * @param opponent
     */
    public void compensatePlayer(Player opponent) {
        compensate(opponent);
        System.out.println("⌂ Your opponent owns this store. \n" +
                "You pay the attending compensation of " + currentProfit + "$ (T_T)\n");
    }

    /**
     * Компенсация бота в случае попадания на клетку-магазин живого игрока.
     *
     * @param opponent
     */
    public void compensateBot(Player opponent) {
        compensate(opponent);
        System.out.println("⌂ Bot pays the attending compensation of " + currentProfit + "$ (T_T)\n");
    }

    /**
     * В случае компенсации, владелец получает <code>CurrentProfit</code>,
     * а наступивший на клетку теряет соот-юю сумму денег.
     *
     * @param opponent
     */
    private void compensate(Player opponent) {
        opponent.pay(currentProfit);
        holder.profit(currentProfit);
    }

    /**
     * Возврат полного названия магазина.
     *
     * @return
     */
    @Override
    public String getName() {
        return "Shop Cell";
    }

    /**
     * Возврат S - если магазин не имеет владельца.
     * Возврат O - если магазин принадлежит боту.
     * Возврат M - если магазин принадлежит живому игроку.
     *
     * @return
     */
    @Override
    public String toString() {
        if (holder == null) {
            return "S";
        } else if (holder instanceof BotPlayer) {
            return "O";
        } else {
            return "M";
        }
    }
}
