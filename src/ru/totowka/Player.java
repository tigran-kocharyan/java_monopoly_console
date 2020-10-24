package ru.totowka;

/**
 * @author <a href="mailto:tskocharyan@edu.hse.ru"> Tigran Kocharyan</a>
 */
public abstract class Player {
    private int money;
    private int worth;
    private int debt;
    private int position;

    public int getMoney() {
        return this.money;
    }

    public int getDebt() {
        return this.debt;
    }

    public int getWorth() {
        return this.worth;
    }

    public int getPosition() {
        return position;
    }

    public void setDebt(int debt) {
        this.debt = debt;
    }

    public Player(int money, int position) {
        this.money = money;
        this.position = position;
        this.debt = 0;
        this.worth = 0;
    }

    /**
     * Метод возвращает, достаточно ли игрока денег на покупку.
     *
     * @param payment
     * @return
     */
    public boolean isEnough(int payment) {
        return this.money - payment >= 0;
    }

    /**
     * Метод проверки игрока на банкротство.
     *
     * @return
     */
    public boolean isBankrupt() {
        return this.money < 0;
    }

    /**
     * Метод сдвигает позицию игрока на <code>step</code> клеток.
     * Для предотвращения выхода за границы берем текущую позицию по модулю <code>cellsCount</code>.
     *
     * @param step
     * @param cellsCount
     */
    public void move(int step, int cellsCount) {
        position = (position + step) % cellsCount;
    }

    /**
     * Взимание с игрока платы в размере <code>price</code>.
     *
     * @param price
     */
    public void pay(int price) {
        this.money -= price;
    }

    /**
     * Выплата долга игрока банку.
     */
    public void payDebt() {
        this.money -= this.debt;
        this.debt = 0;
    }

    /**
     * Получение пользователем выплат (в первую очередь компенсации)
     *
     * @param profit
     */
    public void profit(int profit) {
        this.money += profit;
    }

    /**
     * Покупка магазина на сумму <code>price</code>.
     * Уменьшает текущие деньги и увеличивает ценность.
     *
     * @param price
     */
    public void buy(int price) {
        this.money -= price;
        this.worth += price;
    }


}
