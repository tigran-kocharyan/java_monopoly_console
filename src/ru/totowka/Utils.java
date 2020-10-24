package ru.totowka;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author <a href="mailto:tskocharyan@edu.hse.ru"> Tigran Kocharyan</a>
 */
public final class Utils {
    private Utils() {

    }

    /**
     * Статический объект рандома.
     */
    public static final Scanner scanner = new Scanner(System.in);

    /**
     * Генерирует рандомное int число в диапазоне от min до max-1 включительно.
     *
     * @param min
     * @param max
     * @return
     */
    public static int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    /**
     * Генерирует рандомное double число в диапазоне от min до max невключительно.
     *
     * @param min
     * @param max
     * @return
     */
    public static double randomDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    /**
     * Генерирует рандомный boolean. Метод создан для того, чтобы не добавлять каждый раз библиотеку
     * java.util.concurrent.ThreadLocalRandom. Может показаться бесполезным, но это ошибочно.
     *
     * @return
     */
    public static boolean randomBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    /**
     * Округление по правилам арифметики.
     *
     * @param number
     * @return
     */
    public static int round(double number) {
        return (int) Math.round(number);
    }

    /**
     * Вывод сообщения об ошибке и выход из программы.
     *
     * @param message
     */
    public static void exit(String message) {
        System.err.println(message);
        System.exit(1);
    }

    /**
     * Проверка числа на соответствие заданным границам.
     *
     * @param value
     * @param leftBorder
     * @param rightBorder
     * @return
     */
    public static boolean checkBorders(int value, int leftBorder, int rightBorder) {
        return (value >= leftBorder && value <= rightBorder);
    }

    /**
     * Запрос пользователя ввести данные до тех пор,
     * пока они не будут валидными.
     *
     * @param message
     * @param leftBorder
     * @param rightBorder
     * @return
     */
    public static int getValue(String message, int leftBorder, int rightBorder) {
        String input;
        do {
            System.out.println(message);
            input = scanner.nextLine();
        } while (!tryParseNumber(input, leftBorder, rightBorder));

        return Integer.parseInt(input);
    }

    /**
     * Попытка перевести <code>String</code> в <code>int</code>.
     *
     * @param value
     * @param leftBorder
     * @param rightBorder
     * @return
     */
    public static boolean tryParseNumber(String value, int leftBorder, int rightBorder) {
        try {
            int result = Integer.parseInt(value);
            return checkBorders(result, leftBorder, rightBorder);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Предоставление пользователю выбора между <code>optionYes</code> <code>optionNo</code>,
     * до тех пор, пока введенный пользователем ответ не будет соответствовать одному из них.
     *
     * @param message
     * @param optionYes
     * @param optionNo
     * @return
     */
    public static boolean choice(String message, String optionYes, String optionNo) {
        String input;
        do {
            System.out.println(message);
            input = scanner.nextLine();
        } while (!input.equalsIgnoreCase(optionNo) && !input.equalsIgnoreCase(optionYes));
        return input.equalsIgnoreCase(optionYes);
    }

    /**
     * Вызов метода <code>Thread.sleep()</code> и отлов возможного исключения.
     *
     * @param millis - количество миллисекунд ожидания
     */
    public static void wait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
