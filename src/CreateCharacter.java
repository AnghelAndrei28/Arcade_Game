import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CreateCharacter {
    public CreateCharacter(Account currentAccount) {
        JSONObject initialJson = Game.getInstance().initialJson;
        String[] optionsToChoose = {"Warrior", "Rogue", "Mage"};

        JFrame f = new JFrame();

        JComboBox<String> jComboBox = new JComboBox<>(optionsToChoose);
        jComboBox.setBounds(80, 50, 140, 20);

        JLabel label = new JLabel("Name");
        label.setBounds(80, 100, 140, 20);

        JTextField tf = new JTextField();
        tf.setBounds(80, 120, 140, 20);

        JButton button = new JButton("Create");
        button.setBounds(100, 160, 90, 20);

        JLabel label1 = new JLabel("");
        label1.setBounds(90, 200, 400, 100);

        f.add(button);
        f.add(jComboBox);
        f.add(label);
        f.add(tf);
        f.add(label1);
        f.setLayout(null);
        f.setSize(350, 250);
        f.setVisible(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CharacterFactory characterFactory = new CharacterFactory();
                if (!tf.getText().isEmpty()) {
                    Character character = characterFactory.getCharacter(jComboBox.getItemAt(jComboBox.getSelectedIndex()), tf.getText(), 0, 0);
                    currentAccount.characters.add(character);
                    JSONArray jsonArray = (JSONArray) initialJson.get("accounts");
                    int index = 0;
                    for(Object o : jsonArray) {
                        if(((String)((JSONObject)((JSONObject)o).get("credentials")).get("email")).equals(currentAccount.information.getCredentials().getEmail())) {
                            break;
                        }
                        index++;
                    }
                    JSONObject newCharacter = new JSONObject();
                    newCharacter.put("profession", jComboBox.getItemAt(jComboBox.getSelectedIndex()));
                    newCharacter.put("name", tf.getText());
                    newCharacter.put("level", String.valueOf(0));
                    newCharacter.put("experience", String.valueOf(0));
                    ((JSONArray)((JSONObject)jsonArray.get(index)).get("characters")).add(newCharacter);
                    initialJson.put("accounts", jsonArray);
                    FileWriter fileWriter = null;
                    try {
                        fileWriter = new FileWriter("src/accounts.json");
                        fileWriter.write(initialJson.toJSONString());
                        fileWriter.flush();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    f.dispose();
                    new CharacterScreen(currentAccount);
                } else {
                    label1.setText("Please enter a name");
                }
            }
        });
    }
}
