import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class HomeScreen {
    JFrame f;
    JPanel panelBoard;
    GridCell[][] cells;
    GridCell currentCell;
    int xLimit, yLimit;
    JTextArea textArea;
    Character currentCharacter;
    JButton shop;
    JLabel cashNotificationLabel;
    static int expGained = 0;
    static int levelsGained = 0;
    static int killedEnemies = 0;
    Account currentAccount;
    public HomeScreen(Account currentAccount, Character character, int x, int y) {
        this.currentAccount = currentAccount;
        xLimit = x - 1;
        yLimit = y - 1;
        currentCharacter = character;
        panelBoard = new JPanel();
        panelBoard.setLayout(new GridLayout(x, y));
        panelBoard.setBounds(0, 0, 1000, 800);
        cells = new GridCell[x][y];
        Grid board = Grid.generateMap(x, y, currentCharacter, true);
        System.out.println(x + " " + y);
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                cells[i][j] = new GridCell(board.get(i).get(j));
                cells[i][j].setOpaque(true);
                cells[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                cells[i][j].getCell().x = i;
                cells[i][j].getCell().y = j;
                panelBoard.add(cells[i][j]);
            }
        }
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if(cells[i][j].getCell().equals(board.currentCell)) {
                    currentCell = cells[i][j];
                }
            }
        }
        currentCell.setBackground(Color.CYAN);
        currentCell.setIcon(getLogo(currentCell.getImageName()));
        f = new JFrame("World of Marcel");
        f.setLayout(null);
        f.setSize(1600, 850);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panelInformation = new JPanel();
        panelInformation.setBounds(1000, 0, 600, 800);
        JPanel panelStory = new JPanel();
        JLabel labelStory = new JLabel("Story:");
        textArea = new JTextArea("Welcome to the game");
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        labelStory.setBounds(900, 30, 50, 50);
        textArea.setBounds(900, 90, 400, 100);
        panelStory.add(labelStory);
        panelStory.add(textArea);
        panelInformation.add(panelStory);
        JPanel commands = new JPanel(new GridLayout(3, 3));
        commands.add(new JPanel());
        JButton topButton = new JButton("Top");
        commands.add(topButton);
        topButton.addActionListener(this::goNorth);
        commands.add(new JPanel());
        JButton leftButton = new JButton("Left");
        commands.add(leftButton);
        leftButton.addActionListener(this::goWest);
        commands.add(new JPanel());
        JButton rightButton = new JButton("Right");
        commands.add(rightButton);
        rightButton.addActionListener(this::goEast);
        commands.add(new JPanel());
        JButton bottomButton = new JButton("Bottom");
        commands.add(bottomButton);
        bottomButton.addActionListener(this::goSouth);
        commands.add(new JPanel());
        commands.setBounds(1050 , 200, 400, 200);
        shop = new JButton("Shop");
        shop.setBounds(1200, 75, 100, 50);
        cashNotificationLabel = new JLabel("You found cash on your way!");
        cashNotificationLabel.setVisible(false);
        cashNotificationLabel.setBounds(1250, 700, 200, 50);
        shop.setEnabled(false);
        shop.addActionListener(this::shopPAGE);
        panelBoard.revalidate();
        panelBoard.repaint();
        f.add(panelBoard);
        f.add(commands);
        f.add(cashNotificationLabel);
        f.add(shop);
        f.add(panelInformation);
    }

    public void goNorth(ActionEvent e) {
        if (currentCell.getCell().x == 0) {
            System.out.println("Out of the board");
        } else {
            currentCell.setBackground(Color.GREEN);
            currentCell = cells[currentCell.getCell().x - 1][currentCell.getCell().y];
            currentCell.setBackground(Color.CYAN);
            textArea.setText(Game.getInstance().displayStory(currentCell.getCell(), true));
            displayOptions();
        }
    }

    public void goSouth(ActionEvent e) {
        if (currentCell.getCell().x == xLimit) {
            System.out.println("Out of the board");
        } else {
            currentCell.setBackground(Color.GREEN);
            currentCell = cells[currentCell.getCell().x + 1][currentCell.getCell().y];
            currentCell.setBackground(Color.CYAN);
            textArea.setText(Game.getInstance().displayStory(currentCell.getCell(), true));
            displayOptions();
        }
    }

    public void goEast(ActionEvent e) {
        if (currentCell.getCell().y == yLimit) {
            System.out.println("Out of the board");
        } else {
            currentCell.setBackground(Color.GREEN);
            currentCell = cells[currentCell.getCell().x][currentCell.getCell().y + 1];
            currentCell.setBackground(Color.CYAN);
            textArea.setText(Game.getInstance().displayStory(currentCell.getCell(), true));
            displayOptions();
        }
    }

    public void goWest(ActionEvent e) {
        if (currentCell.getCell().y == 0) {
            System.out.println("Out of the board");
        } else {
            currentCell.setBackground(Color.GREEN);
            currentCell = cells[currentCell.getCell().x][currentCell.getCell().y - 1];
            currentCell.setBackground(Color.CYAN);
            textArea.setText(Game.getInstance().displayStory(currentCell.getCell(), true));
            displayOptions();
        }
    }

    public void shopPAGE(ActionEvent e) {
        ShopPage f2 = new ShopPage(currentCell.getCell(), f, currentCharacter);
        f2.setVisible(true);
        f.setVisible(false);
    }

    public void displayOptions() {
        if(currentCell.getCell().cellType != CellType.EMPTY) {
            currentCell.setIcon(getLogo(currentCell.getImageName()));
        } else {
            currentCell.setIcon(null);
        }
        shop.setEnabled(false);
        cashNotificationLabel.setVisible(false);
        Random random = new Random();
        switch(currentCell.getCell().cellType) {
            case EMPTY:
                if ((random.nextInt(5 - 1) + 1) == 1 && !currentCell.getCell().visited) {
                    currentCharacter.inventory.addCash(random.nextInt(11) + 15);
                    cashNotificationLabel.setVisible(true);
                }
                break;
            case SHOP:
                shop.setEnabled(true);
                break;
            case FINISH:
                f.dispose();
                new FinishScreen(currentAccount ,currentCharacter);
                break;
            case ENEMY:
                if(!currentCell.getCell().visited) {
                    FightScreen f3 = new FightScreen(f, (Enemy) currentCell.getCell().cellElement, currentCharacter, currentAccount);
                    f3.setVisible(true);
                    f.setVisible(false);
                    if (currentCharacter.exp < 80) {
                        levelsGained++;
                    }
                    expGained += 80;
                    killedEnemies++;
                }
                break;
        }
        currentCell.getCell().visited = true;
    }

    public ImageIcon getLogo(String imageName) {
        ImageIcon imageIcon = new ImageIcon(imageName);
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(50, 50,  Image.SCALE_SMOOTH);
        return new ImageIcon(newimg);
    }
}