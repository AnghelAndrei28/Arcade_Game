import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GameOver {
    public GameOver(Account currentAccount,Character currentCharacter) {
        JFrame f = new JFrame("World of Marcel");
        f.setLayout(null);
        f.setSize(1600, 850);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel finalLabel = new JLabel("GAME OVER, " + currentCharacter.characterName);
        finalLabel.setBounds(400, 200, 800, 100);
        finalLabel.setFont(new Font("Serif", Font.PLAIN, 45));
        finalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel infoLabel = new JLabel("Experience gained: " + HomeScreen.expGained + "                  " + "Level: " + (currentCharacter.level - HomeScreen.levelsGained) + " + " + HomeScreen.levelsGained);
        infoLabel.setBounds(400, 400, 800, 100);
        infoLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel infoLabel2 = new JLabel("Enemies killed: " + HomeScreen.killedEnemies + "                  " + "Cash: " + currentCharacter.inventory.getCash());
        infoLabel2.setBounds(400, 600, 800, 100);
        infoLabel2.setFont(new Font("Serif", Font.PLAIN, 20));
        infoLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        f.add(finalLabel);
        f.add(infoLabel);
        f.add(infoLabel2);
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(1400, 700, 100, 50);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setBounds(100, 700, 100, 50);
        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentCharacter.inventory.potions = new ArrayList<>();
                f.dispose();
                Game.getInstance().run("GUI");
            }
        });
        f.add(exitButton);
        f.add(playAgainButton);
        currentAccount.playedGames++;
        ((JSONObject)((JSONArray)Game.getInstance().initialJson.get("accounts")).get(AuthScreen.indexAccount)).put("maps_completed", currentAccount.playedGames);
        ((JSONObject)((JSONArray)((JSONObject)((JSONArray)Game.getInstance().initialJson.get("accounts")).get(AuthScreen.indexAccount)).get("characters")).get(CharacterScreen.indexCharacter)).put("level", String.valueOf(currentCharacter.level));
        ((JSONObject)((JSONArray)((JSONObject)((JSONArray)Game.getInstance().initialJson.get("accounts")).get(AuthScreen.indexAccount)).get("characters")).get(CharacterScreen.indexCharacter)).put("experience", String.valueOf(currentCharacter.exp));
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("src/accounts.json");
            fileWriter.write(Game.getInstance().initialJson.toJSONString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
