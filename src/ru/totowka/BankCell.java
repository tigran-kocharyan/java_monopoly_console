package ru.totowka;

/**
 * @author <a href="mailto:tskocharyan@edu.hse.ru"> Tigran Kocharyan</a>
 * Класс Банка. В дальнейшем, все офисы банка на игровой поле будут заполняться
 * объектами этого класса.
 */
public class BankCell extends Cell {
    public BankCell() {
        super("$");
    }

    /**
     * Метод для активации действия игровой клетки.
     *
     * @param player
     * @param monopoly
     */
    public void enter(Player player, Monopoly monopoly) {
        // Вывод координат пользователя. Проверка на наличие долга и проверка на банкрота.
        monopoly.showCoordinates(player);
        if (player instanceof SoulPlayer) {
            if (player.getDebt() > 0) {
                System.out.println("$ Time to pay the debt. " +
                        "Bank took " + player.getDebt() + "$ from the player.\n");
                player.payDebt();
                if (player.isBankrupt()) {
                    monopoly.finishGame();
                }
            } else if (player.getWorth() == 0) {
                System.out.println("$ You have no worth yet, Bank can offer you nothing :(\n");
            } else {
                borrow(player, monopoly);
            }
        } else {
            System.out.println("Unfortunately, Monopoly Bank doesn't cooperate with Bot Players yet :(\n");
        }
    }

    /**
     * Метод позволяет пользователю взять в долг деньги.
     *
     * @param player
     * @param monopoly
     */
    private void borrow(Player player, Monopoly monopoly) {
        boolean choice =
                Utils.choice("Would you like to borrow money (yes/no)",
                        "yes", "no");
        if (choice) {
            int maximumLoan = Utils.round(player.getWorth() * monopoly.getCreditCoef());
            System.out.println("How much $ would you like to borrow from Bank?");
            int loan = Utils.getValue("The maximum we can lend you is " +
                    maximumLoan, 1, maximumLoan);
            player.profit(loan);
            player.setDebt(Utils.round(loan * monopoly.getDebtCoef()));
            System.out.printf("$ OK. You borrow %d$.\n" +
                    "You will have to pay off a debt of %d$.\n\n", loan, player.getDebt());
        } else {
            System.out.println("$ OK. You decided to skip this alluring place...\n");
        }
    }

    /**
     * Переопределенный метод для возврата полного имени банка.
     *
     * @return
     */
    @Override
    public String getName() {
        return "Bank Cell";
    }
}
