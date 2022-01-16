import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class FightScreen extends JFrame {

    private final JFrame frame;


    public FightScreen(JFrame frame, Enemy enemy, Character currentCharacter) {
        super("World of Marcel");
        this.frame = frame;
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

        JLabel championHeart = new JLabel(String.format("%d", currentCharacter.currentHealth), getLogo("src/Imagini/heart.png", 40, 30), JLabel.CENTER);
        championHeart.setBounds(200, 250, 100, 30);
        championHeart.setForeground(Color.WHITE);
        background.add(championHeart);
        JComponent parent1 = (JComponent) championHeart.getParent();
        if(parent1 != null) {
            parent1.revalidate();
        }

        JLabel championMana = new JLabel(String.format("%d", currentCharacter.currentMana), getLogo("src/Imagini/mana.png", 40, 30), JLabel.CENTER);
        championMana.setBounds(200, 290, 100, 30);
        championMana.setForeground(Color.WHITE);
        background.add(championMana);
        JComponent parent2 = (JComponent) championMana.getParent();
        if(parent2 != null) {
            parent2.revalidate();
        }

        JLabel enemyHeart = new JLabel(String.format("%d", enemy.currentHealth), getLogo("src/Imagini/heart.png", 40, 30), JLabel.CENTER);
        enemyHeart.setBounds(900, 250, 100, 30);
        enemyHeart.setForeground(Color.WHITE);
        background.add(enemyHeart);
        JComponent parent3 = (JComponent) enemyHeart.getParent();
        if(parent3 != null) {
            parent3.revalidate();
        }

        JLabel enemyMana = new JLabel(String.format("%d", enemy.currentMana), getLogo("src/Imagini/mana.png", 40, 30), JLabel.CENTER);
        enemyMana.setBounds(900, 290, 100, 30);
        enemyMana.setForeground(Color.WHITE);
        background.add(enemyMana);
        JComponent parent4 = (JComponent) enemyMana.getParent();
        if(parent4 != null) {
            parent4.revalidate();
        }

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
        attackButton.setEnabled(false);
        attackButton.setOpaque(false);
        attackButton.setContentAreaFilled(false);
        attackButton.setBorderPainted(false);
        attackButton.setForeground(Color.white);

        commands.add(attackButton);
        JButton spellButton = new JButton("2. Use Spell");
        spellButton.setEnabled(false);
        spellButton.setOpaque(false);
        spellButton.setContentAreaFilled(false);
        spellButton.setBorderPainted(false);
        spellButton.setForeground(Color.white);
        commands.add(spellButton);
        JButton potionButton = new JButton("3. Use potion");
        potionButton.setEnabled(false);
        potionButton.setOpaque(false);
        potionButton.setContentAreaFilled(false);
        potionButton.setBorderPainted(false);
        potionButton.setForeground(Color.white);
        commands.add(potionButton);
        background.add(commands);
        JButton startButton = new JButton("Start");
        startButton.setBackground(Color.RED);
        startButton.setForeground(Color.WHITE);
        commands.add(startButton);
        startButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean turn = true;
                while(currentCharacter.currentHealth > 0 && enemy.currentHealth > 0) {
                    potionButton.setEnabled(false);
                    spellButton.setEnabled(false);
                    attackButton.setEnabled(false);
                    if(turn) {
                        potionButton.setEnabled(true);
                        spellButton.setEnabled(true);
                        attackButton.setEnabled(true);
                        potionButton.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {
                                System.out.println("Multa mue");
                            }
                        });
                    } else {
                        Random random = new Random();
                        if (random.nextBoolean()) {
                            int indexSpell = random.nextInt(enemy.spells.size());
                            enemy.useSpell(enemy.spells.get(indexSpell), currentCharacter);
                        } else {
                            enemy.getDamage(null, currentCharacter);
                        }
                        championHeart.setText(String.format("%d", currentCharacter.currentHealth));
                        enemyMana.setText(String.format("%d", enemy.currentMana));
                    }
                    turn = !turn;
                }
                Random random = new Random();
                if (currentCharacter.currentHealth > 0) {
                    int experienceGained = 80;
                    if ((random.nextInt(5 - 1) + 1) != 1) {
                        currentCharacter.inventory.addCash(80);
                    }
                    currentCharacter.exp += experienceGained;
                    if (currentCharacter.exp >= currentCharacter.upgradeExp) {
                        currentCharacter.levelUp(currentCharacter.exp - currentCharacter.upgradeExp);
                    }
                }
            }
        });
    }

    public ImageIcon getLogo(String imageName, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(imageName);
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(width, height,  Image.SCALE_SMOOTH);
        return new ImageIcon(newimg);
    }

    public void exit(ActionEvent e) {
        frame.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }
}
