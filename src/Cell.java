enum CellType {
    EMPTY,
    ENEMY,
    SHOP,
    FINISH,
    START
}

public class Cell {
    int x, y;
    CellType cellType;
    CellElement cellElement;
    boolean visited;

    Cell(int x, int y, CellType cellType) {
        this.x = x;
        this.y = y;
        this.cellType = cellType;
        switch (cellType) {
            case EMPTY:
                cellElement = new Empty();
                visited = false;
                break;
            case ENEMY:
                cellElement = new Enemy();
                visited = false;
                break;
            case SHOP:
                cellElement = new Shop();
                visited = false;
                break;
            case FINISH:
                cellElement = new Finish();
                visited = false;
                break;
            case START:
                cellElement = new Start();
                visited = true;
        }
    }
}
