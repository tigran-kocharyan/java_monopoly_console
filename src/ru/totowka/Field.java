package ru.totowka;

import java.util.function.Supplier;

/**
 * @author <a href="mailto:tskocharyan@edu.hse.ru"> Tigran Kocharyan</a>
 */
public class Field {
    private int width;
    private int height;
    private int cellsAmount;
    private Cell[] cells;
    private Cell[][] field;

    public Cell[] getCells() {
        return cells;
    }

    public int getCellsAmount() {
        return cellsAmount;
    }

    public Field(int width, int height) {
        this.width = width;
        this.height = height;
        this.cellsAmount = (width + height - 2) * 2;
        cells = new Cell[cellsAmount];
        field = new Cell[height][width];
        generateField();
    }

    /**
     * Генерация игрового поля.
     */
    protected void generateField() {
        cells[0] = new EmptyCell();
        cells[width - 1] = new EmptyCell();
        cells[width + height - 2] = new EmptyCell();
        cells[cellsAmount - height + 1] = new EmptyCell();

        /*
        Так как на каждой линии должно быть от 0 до 1,2
        определенных клеток, данный способ может накладывать клетки друг на друга
        что создает эффект рандома.
         */
        randomCell(TaxiCell::new, 2);
        randomCell(PenaltyCell::new, 2);
        randomCell(BankCell::new, 1);

        // Добавление полей магазинов.
        for (int i = 0; i < cellsAmount; i++) {
            if (cells[i] == null) {
                cells[i] = new ShopCell();
            }
        }

        // В самом конце заполняется поле клеток для вывода.
        for (int i = 0; i < cellsAmount; i++) {
            int[] coordinates = toDoubleDimension(i);
            field[coordinates[1]][coordinates[0]] = cells[i];
        }
    }

    /**
     * Перевод индекса клетки одномерного массива в индекс двумерного массива.
     *
     * @param index
     * @return
     */
    protected int[] toDoubleDimension(int index) {
        int[] indexes = new int[2];
        if (index < this.width) {
            indexes[0] = index;
            indexes[1] = 0;
        } else if (index < this.width + this.height - 1) {
            indexes[0] = this.width - 1;
            indexes[1] = index - this.width + 1;
        } else if (index < this.cellsAmount - this.height + 1) {
            indexes[0] = this.cellsAmount - this.height - index + 1;
            indexes[1] = height - 1;
        } else {
            indexes[0] = 0;
            indexes[1] = this.cellsAmount - index;
        }
        return indexes;
    }

    /**
     * Вывод игрового поля.
     * Хочется отметить, что вывод достаточно костыльный.
     * Кодер честно пытался сделать всё красиво, но консоль
     * не поддалась. Если ширина >= 10, вывод немного меняется.
     */
    public void showField() {
        /*
         Так как двуразрядные индексы могут немного сломать вывод,
         созданы опциональные пробелы.
         */
        String plus = "+", space = "";
        if (width >= 11) {
            space = plus = " ";
            System.out.print("   ");
        } else {
            System.out.print("    ");
        }
        for (int i = 0; i < width; i++) {
            if (width >= 11) {
                System.out.printf(" %2d ", i);
            } else {
                System.out.print(i + " ");
            }
        }
        System.out.println();
        for (int i = 0; i < height; i++) {
            System.out.printf("%2d |", i);
            for (int j = 0; j < width; j++) {
                if (field[i][j] == null && j == width - 2) {
                    System.out.print(space + space + " |");
                } else if (field[i][j] == null) {
                    System.out.print(" " + plus + space + space);
                } else {
                    System.out.print(space + field[i][j].toString() + space + "|");
                }
            }
            System.out.println();
        }
    }

    /**
     * Рандомное заполнение клеток в одном ряду.
     * Использует Supplier для получения конструктора класса.
     *
     * @param cellType
     * @param amount
     */
    protected void randomCell(Supplier<Cell> cellType, int amount) {
        for (int i = 0; i < amount; i++) {
            cells[Utils.randomInt(1, width - 1)] = cellType.get();
        }
        for (int i = 0; i < amount; i++) {
            cells[Utils.randomInt(width, width + height - 2)] = cellType.get();
        }
        for (int i = 0; i < amount; i++) {
            cells[Utils.randomInt(width + height - 1, cellsAmount - height + 1)] = cellType.get();
        }
        for (int i = 0; i < amount; i++) {
            cells[Utils.randomInt(cellsAmount - height + 2, cellsAmount)] = cellType.get();
        }
    }
}
