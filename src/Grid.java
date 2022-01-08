import java.util.ArrayList;

public class Grid extends ArrayList<ArrayList<Cell>> {
    int length, width;
    Character character;
    Cell currentCell;

    private Grid (int length, int width, Character character) {
        this.length = length;
        this.width = width;
        this.character = character;
    }

    static Grid generateMap(int length, int width, Character character) {
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
        return board;
    }

    void printMap() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(this.get(j).get(i).visited ? this.get(j).get(i).cellElement.toCharacter() + " " : "?");
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
            currentCell.visited = true;
            character.x = currentCell.x - 1;
            printMap();
        }
    }
    void goSouth() {
        if(currentCell.x == width - 1) {
            System.out.println("Out of bounds");
        } else {
            currentCell = get(currentCell.y).get(currentCell.x + 1);
            currentCell.visited = true;
            character.x = currentCell.x + 1;
            printMap();
        }
    }
    void goWest() {
        if(currentCell.y == 0) {
            System.out.println("Out of bounds");
        } else {
            currentCell = get(currentCell.y - 1).get(currentCell.x);
            currentCell.visited = true;
            character.y = currentCell.y - 1;
            printMap();
        }
    }
    void goEast() {
        if(currentCell.y == length - 1) {
            System.out.println("Out of bounds");
        } else {

            currentCell = get(currentCell.y + 1).get(currentCell.x);
            currentCell.visited = true;
            character.y = currentCell.y + 1;
            printMap();
        }
    }
}
