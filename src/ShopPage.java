import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ShopPage extends JFrame{
    private final JFrame frame;
    JList<Potion> list;
    Character currentCharacter;
    Cell currentCell;
    DefaultListModel<Potion> defaultListModel;
    JButton buyButton;
    JLabel cash;
    JList<Potion> list2;
    DefaultListModel<Potion> defaultListModel2;
    public ShopPage(Cell currentCell, JFrame frame, Character currentCharacter) {
        super("World of Marcel");
        this.currentCharacter = currentCharacter;
        this.currentCell = currentCell;
        defaultListModel = new DefaultListModel<Potion>();
        defaultListModel.addAll(((Shop)(currentCell.cellElement)).potionList);
        list = new JList<Potion>(defaultListModel);
        this.frame = frame;
        setLayout(null);
        setSize(1600, 850);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        list.addListSelectionListener(this::valueChanged);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 20, 300, 300);
        scrollPane.setViewportView(list);
        list.setLayoutOrientation(JList.VERTICAL);
        add(scrollPane);
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBounds(900, 20, 500, 700);
        JPanel cashPanel = new JPanel(new BorderLayout());
        cash = new JLabel(String.valueOf(currentCharacter.inventory.getCash()));
        JLabel cashLabel = new JLabel("Cash:");
        cashPanel.add(cashLabel, BorderLayout.LINE_START);
        cashPanel.add(cash, BorderLayout.LINE_END);
        JPanel buttonsPanel = new JPanel(new BorderLayout());
        buttonsPanel.setSize(500, 200);
        buyButton = new JButton("BUY");
        buyButton.addActionListener(this::buy);
        buyButton.setEnabled(false);
        JButton exitButton = new JButton("EXIT");
        exitButton.addActionListener(this::exit);

        defaultListModel2 = new DefaultListModel<Potion>();
        defaultListModel2.addAll(currentCharacter.inventory.potions);
        list2 = new JList<Potion>(defaultListModel2);
        JScrollPane scrollPane2 = new JScrollPane();
        scrollPane2.setSize(300, 300);
        scrollPane2.setViewportView(list2);
        list2.setLayoutOrientation(JList.VERTICAL);

        buttonsPanel.add(buyButton, BorderLayout.PAGE_START);
        buttonsPanel.add(exitButton, BorderLayout.PAGE_END);
        infoPanel.add(cashPanel, BorderLayout.PAGE_START);
        infoPanel.add(buttonsPanel, BorderLayout.PAGE_END);
        infoPanel.add(scrollPane2, BorderLayout.CENTER);
        add(infoPanel);
    }

    public void exit(ActionEvent e) {
        frame.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }

    public void buy(ActionEvent e) {
        int index = list.getSelectedIndex();
        currentCharacter.buy(((Shop)(currentCell.cellElement)).getPotion(index));
        defaultListModel2.addElement(defaultListModel.getElementAt(index));
        defaultListModel.removeElementAt(index);
        cash.setText(String.valueOf(currentCharacter.inventory.getCash()));
        System.out.println(currentCharacter.inventory.potions);
    }

    public void  valueChanged(ListSelectionEvent e) {
        if(list.isSelectionEmpty()) {
            buyButton.setEnabled(false);
            return;
        }

        buyButton.setEnabled(true);
    }
}
