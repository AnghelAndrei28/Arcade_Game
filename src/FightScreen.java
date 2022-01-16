import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class FightScreen extends JFrame {

    private final JFrame frame;
    Enemy enemy;
    Character currentCharacter;

    JLabel championHeart;
    JLabel championMana;
    JLabel enemyHeart;
    JLabel enemyMana;

    DefaultListModel<Potion> potionDefaultListModel;
    DefaultListModel<Spell> spellDefaultListModel;
    JList<Potion> potionList;
    JList<Spell> spellList;
    JButton selectButton;
    JScrollPane scrollPane;

    Account currentAccount;

    public FightScreen(JFrame frame, Enemy enemy, Character currentCharacter, Account currentAccount) {
        super("World of Marcel");
        this.enemy = enemy;
        this.currentCharacter = currentCharacter;
        this.frame = frame;
        this.currentAccount = currentAccount;
        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);

        JLabel background = new JLabel("", getLogo("src/Imagini/background.png", 1200, 700), JLabel.CENTER);
        background.setBounds(0, 0, 1200, 700);
        add(background);

        JLabel char1 = new JLabel("", getLogo("src/Imagini/character1.png", 300, 350), JLabel.CENTER);
        char1.setBounds(100, 320, 300, 350);
        background.add(char1);

        JLabel char2 = new JLabel("", getLogo("src/Imagini/character2.png", 300, 350), JLabel.CENTER);
        char2.setBounds(800, 320, 300, 350);
        background.add(char2);

        JLabel earth = new JLabel("Earth protection", getLogo("src/Imagini/earth.png", 50, 30), JLabel.CENTER);
        if(enemy.earth) {
            earth.setForeground(Color.GREEN);
        } else {
            earth.setForeground(Color.RED);
        }
        earth.setBounds(1000, 320, 200, 30);
        background.add(earth);

        JLabel fire = new JLabel("Fire protection", getLogo("src/Imagini/flame.png", 50, 30), JLabel.CENTER);
        if(enemy.fire) {
            fire.setForeground(Color.GREEN);
        } else {
            fire.setForeground(Color.RED);
        }
        fire.setBounds(1000, 370, 200, 30);
        background.add(fire);

        JLabel ice = new JLabel("Ice protection", getLogo("src/Imagini/snow.png", 50, 30), JLabel.CENTER);
        if(enemy.ice) {
            ice.setForeground(Color.GREEN);
        } else {
            ice.setForeground(Color.RED);
        }
        ice.setBounds(1000, 420, 200, 30);
        background.add(ice);

        championHeart = new JLabel(String.format("%d", currentCharacter.currentHealth), getLogo("src/Imagini/heart.png", 40, 30), JLabel.CENTER);
        championHeart.setBounds(200, 250, 100, 30);
        championHeart.setForeground(Color.WHITE);
        background.add(championHeart);

        championMana = new JLabel(String.format("%d", currentCharacter.currentMana), getLogo("src/Imagini/mana.png", 40, 30), JLabel.CENTER);
        championMana.setBounds(200, 290, 100, 30);
        championMana.setForeground(Color.WHITE);
        background.add(championMana);

        enemyHeart = new JLabel(String.format("%d", enemy.currentHealth), getLogo("src/Imagini/heart.png", 40, 30), JLabel.CENTER);
        enemyHeart.setBounds(900, 250, 100, 30);
        enemyHeart.setForeground(Color.WHITE);
        background.add(enemyHeart);

        enemyMana = new JLabel(String.format("%d", enemy.currentMana), getLogo("src/Imagini/mana.png", 40, 30), JLabel.CENTER);
        enemyMana.setBounds(900, 290, 100, 30);
        enemyMana.setForeground(Color.WHITE);
        background.add(enemyMana);

        JLabel championLevel = new JLabel(String.format("Level: %d", currentCharacter.level), JLabel.CENTER);
        championLevel.setBounds(10, 320, 100, 30);
        championLevel.setForeground(Color.WHITE);
        background.add(championLevel);

        JLabel championExp = new JLabel(String.format("EXP: %d", currentCharacter.exp), JLabel.CENTER);
        championExp.setBounds(10, 360, 100, 30);
        championExp.setForeground(Color.WHITE);
        background.add(championExp);

        JLabel actionListLabel = new JLabel("Pick one of these");
        actionListLabel.setHorizontalAlignment(JLabel.CENTER);
        actionListLabel.setForeground(Color.white);
        JPanel commands = new JPanel(new GridLayout(5, 1));
        commands.setBounds(500, 320 , 200, 300);
        commands.setOpaque(false);
        commands.add(actionListLabel);
        JButton attackButton = new JButton("1. Basic attack");
        attackButton.setOpaque(false);
        attackButton.setContentAreaFilled(false);
        attackButton.setBorderPainted(false);
        attackButton.setForeground(Color.white);
        attackButton.addActionListener(this::basicAttack);
        commands.add(attackButton);

        JButton spellButton = new JButton("2. Use Spell");
        spellButton.setOpaque(false);
        spellButton.setContentAreaFilled(false);
        spellButton.setBorderPainted(false);
        spellButton.setForeground(Color.white);
        spellButton.addActionListener(this::spellAttack);
        commands.add(spellButton);

        JButton potionButton = new JButton("3. Use potion");
        potionButton.setOpaque(false);
        potionButton.setContentAreaFilled(false);
        potionButton.setBorderPainted(false);
        potionButton.setForeground(Color.white);
        potionButton.addActionListener(this::potionUse);
        commands.add(potionButton);
        background.add(commands);

        spellDefaultListModel = new DefaultListModel<>();
        spellDefaultListModel.addAll(currentCharacter.spells);
        potionDefaultListModel = new DefaultListModel<>();
        potionDefaultListModel.addAll(currentCharacter.inventory.potions);
        spellList = new JList<>(spellDefaultListModel);
        potionList = new JList<>(potionDefaultListModel);
        spellList.addListSelectionListener(this::valueChanged);
        potionList.addListSelectionListener(this::valueChanged);
        spellList.setLayoutOrientation(JList.VERTICAL);
        potionList.setLayoutOrientation(JList.VERTICAL);
        spellList.setOpaque(false);
        potionList.setOpaque(false);
        scrollPane = new JScrollPane();
        scrollPane.setBounds(450, 20, 300, 180);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setForeground(Color.white);
        selectButton = new JButton("Select");
        selectButton.setEnabled(false);
        selectButton.setBounds(550, 200 , 100, 30);
        selectButton.addActionListener(this::selectButtonClicked);
        selectButton.setOpaque(false);
        selectButton.setContentAreaFilled(false);
        selectButton.setBorderPainted(false);
        selectButton.setForeground(Color.white);
        background.add(scrollPane);
        background.add(selectButton);
    }

    public ImageIcon getLogo(String imageName, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(imageName);
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(width, height,  Image.SCALE_SMOOTH);
        return new ImageIcon(newimg);
    }

    public void exit() {
        currentCharacter.exp += 80;
        if(currentCharacter.exp >= currentCharacter.upgradeExp) {
            currentCharacter.levelUp();
        }
        frame.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }

    public void basicAttack(ActionEvent e) {
        scrollPane.setViewportView(null);
        currentCharacter.getDamage(null, enemy);
        if (enemy.currentHealth != 0) {
            enemyAttack();
        } else {
            exit();
        }
        enemyHeart.setText(String.format("%d", enemy.currentHealth));
        championMana.setText(String.format("%d", currentCharacter.currentMana));
    }

    public void spellAttack(ActionEvent e) {
        potionList.clearSelection();
        scrollPane.setViewportView(spellList);
    }

    public void potionUse(ActionEvent e) {
        spellList.clearSelection();
        scrollPane.setViewportView(potionList);
    }

    public void enemyAttack() {
        Random random = new Random();
        if (random.nextBoolean()) {
            int indexSpell = random.nextInt(enemy.spells.size());
            enemy.useSpell(enemy.spells.get(indexSpell), currentCharacter);
        } else {
            enemy.getDamage(null, currentCharacter);
        }
        championHeart.setText(String.format("%d", currentCharacter.currentHealth));
        enemyMana.setText(String.format("%d", enemy.currentMana));
        if(currentCharacter.currentHealth == 0) {
            frame.dispose();
            dispose();
            new FinishScreen(currentAccount, currentCharacter);
        }
    }

    public void  valueChanged(ListSelectionEvent e) {
        if(spellList.isSelectionEmpty() && potionList.isSelectionEmpty()) {
            selectButton.setEnabled(false);
            return;
        }
        selectButton.setEnabled(true);
    }

    public void selectButtonClicked(ActionEvent e) {
        if(spellList.isSelectionEmpty()) {
            int index = potionList.getSelectedIndex();
            currentCharacter.inventory.usePotion(currentCharacter, currentCharacter.inventory.potions.get(index));
            potionDefaultListModel.removeElementAt(index);
            enemyAttack();
        } else if(potionList.isSelectionEmpty()){
            int index = spellList.getSelectedIndex();
            currentCharacter.useSpell(currentCharacter.spells.get(index), enemy);
            if(enemy.currentHealth != 0) {
                enemyAttack();
            } else {
                exit();
            }
        }
        championHeart.setText(String.format("%d", currentCharacter.currentHealth));
        enemyHeart.setText(String.format("%d", enemy.currentHealth));
        championMana.setText(String.format("%d", currentCharacter.currentMana));
        scrollPane.setViewportView(null);
    }
}
