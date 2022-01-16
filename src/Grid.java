import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grid extends ArrayList<ArrayList<Cell>> {
    int length, width;
    Character character;
    Cell currentCell;

    private Grid (int length, int width, Character character) {
        this.length = length;
        this.width = width;
        this.character = character;
    }

    static Grid generateMap(int length, int width, Character character, boolean mode) {
        Grid board = new Grid(length, width, character);
        for (int i = 0; i < length; i++) {
            board.add(new ArrayList<Cell>());
        }
        int j = 0;
        for(ArrayList<Cell> columns : board) {
            for (int i = 0; i < width; i++) {
                columns.add(new Cell(i, j, CellType.EMPTY));
            }
            j++;
        }
        if(!mode) {
            board.get(0).remove(0);
            board.get(0).add(0, new Cell(0, 0, CellType.START));
            board.currentCell = board.get(0).get(0);
            board.get(0).remove(2);
            board.get(0).add(2, new Cell(2, 0, CellType.SHOP));
            board.get(3).remove(0);
            board.get(3).add(0, new Cell(0, 3, CellType.SHOP));
            board.get(3).remove(1);
            board.get(3).add(1, new Cell(1, 3, CellType.SHOP));
            board.get(4).remove(3);
            board.get(4).add(3, new Cell(3, 4, CellType.ENEMY));
            board.get(4).remove(4);
            board.get(4).add(4, new Cell(4, 4, CellType.FINISH));
            board.character.x = 0;
            board.character.y = 0;
        } else {
            List<Integer> cellNumbers = new ArrayList<Integer>();
            for (int i = 0; i < length * width; i++) {
                cellNumbers.add(i);
            }
            Random random = new Random();
            int numberOfShops = random.nextInt(4) + 2;
            System.out.println(numberOfShops);
            for (j = 1; j <= numberOfShops; j++) {
                int cellNumber = random.nextInt(cellNumbers.size());
                int row = cellNumbers.get(cellNumber) / width;
                int col = cellNumbers.get(cellNumber) - (row * width);
                board.get(row).remove(col);
                board.get(row).add(col, new Cell(row, col, CellType.SHOP));
                cellNumbers.remove(cellNumber);
            }
            int numberOfEnemies = random.nextInt(4) + 4;
            System.out.println(numberOfEnemies);
            for (j = 1; j <= numberOfEnemies; j++) {
                int cellNumber = random.nextInt(cellNumbers.size());
                int row = cellNumbers.get(cellNumber) / width;
                int col = cellNumbers.get(cellNumber) - (row * width);
                board.get(row).remove(col);
                board.get(row).add(col, new Cell(row, col, CellType.ENEMY));
                cellNumbers.remove(cellNumber);
            }
            int cellNumber = random.nextInt(cellNumbers.size());
            int row = cellNumbers.get(cellNumber) / width;
            int col = cellNumbers.get(cellNumber) - (row * width);
            board.get(row).remove(col);
            board.get(row).add(col, new Cell(row, col, CellType.FINISH));
            cellNumbers.remove(cellNumber);

            cellNumber = random.nextInt(cellNumbers.size());
            row = cellNumbers.get(cellNumber) / width;
            col = cellNumbers.get(cellNumber) - (row * width);
            board.get(row).remove(col);
            board.get(row).add(col, new Cell(row, col, CellType.START));
            cellNumbers.remove(cellNumber);
            board.character.x = row;
            board.character.y = col;
            board.currentCell = board.get(row).get(col);
        }
        return board;
    }

    void printMap() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(this.get(j).get(i).visited ? this.get(j).get(i).cellElement.toCharacter() : "? ");
            }
            System.out.print("\n");
        }
        System.out.println();
    }

    void printEndMap() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(this.get(j).get(i).cellElement.toCharacter());
            }
            System.out.print("\n");
        }
        System.out.println();
    }

    void goNorth() {
        if(currentCell.x == 0) {
            System.out.println("Out of bounds");
        } else {
            currentCell = get(currentCell.y).get(currentCell.x - 1);
            character.x = currentCell.x - 1;
        }
    }
    void goSouth() {
        if(currentCell.x == width - 1) {
            System.out.println("Out of bounds");
        } else {
            currentCell = get(currentCell.y).get(currentCell.x + 1);
            character.x = currentCell.x + 1;
        }
    }
    void goWest() {
        if(currentCell.y == 0) {
            System.out.println("Out of bounds");
        } else {
            currentCell = get(currentCell.y - 1).get(currentCell.x);
            character.y = currentCell.y - 1;
        }
    }
    void goEast() {
        if(currentCell.y == length - 1) {
            System.out.println("Out of bounds");
        } else {
            currentCell = get(currentCell.y + 1).get(currentCell.x);
            character.y = currentCell.y + 1;
        }
    }
}
