import javax.swing.*;
import java.awt.*;

public class GridCell extends JLabel {
    private final Cell cell;
    private String imageName;

    GridCell(Cell cell) {
        this.cell = cell;
        setBackground(Color.GREEN);
            switch (cell.cellType) {
                case EMPTY:
                    imageName = null;
                case SHOP:
                    imageName = "src/Imagini/store.png";
                    break;
                case ENEMY:
                    imageName = "src/Imagini/angry.png";
                    break;
                case START:
                    imageName = "src/Imagini/map-start.png";
                    break;
                case FINISH:
                    imageName = "src/Imagini/map-finish.png";
                    break;
            }
            ImageIcon imageIcon = new ImageIcon("src/Imagini/question.png");
            Image image = imageIcon.getImage();
            Image newimg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(newimg);
            setIcon(imageIcon);
            setHorizontalAlignment(SwingConstants.CENTER);
    }

    public Cell getCell() {
        return cell;
    }

    public String getImageName() {
        return imageName;
    }
}
