import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RegisterScreen {
    public RegisterScreen(List<Account> accountList) {
        JSONObject initialJson = Game.getInstance().initialJson;
        JFrame f = new JFrame("World of Marcel");
        JLabel label1 = new JLabel("Email");
        label1.setBounds(50, 50, 150, 20);
        JTextField tf1 =new JTextField();
        tf1.setBounds(50,70,150,20);
        JLabel label2 = new JLabel("Password");
        label2.setBounds(50, 100, 150, 20);
        JTextField tf2 =new JTextField();
        tf2.setBounds(50,120,150,20);
        JLabel label3 = new JLabel("Confirm Password");
        label3.setBounds(50, 150, 150, 20);
        JTextField tf3 =new JTextField();
        tf3.setBounds(50,170,150,20);
        JLabel label4 = new JLabel("Name");
        label4.setBounds(50, 200, 150, 20);
        JTextField tf4 =new JTextField();
        tf4.setBounds(50,220,150,20);
        JLabel label5 = new JLabel("Country");
        label5.setBounds(50, 250, 150, 20);
        JTextField tf5 =new JTextField();
        tf5.setBounds(50,270,150,20);
        JLabel label6 = new JLabel("Favorite Games");
        label6.setBounds(50, 300, 150, 20);
        JTextField tf6 =new JTextField("");
        tf6.setBounds(50,320,150,20);
        JButton b1 = new JButton("Register");
        b1.setBounds(50,360,150,50);
        JButton b2 = new JButton("Sign In");
        b2.setBounds(50,415,150,50);
        JLabel label7 = new JLabel("Invalid Account");
        label7.setBounds(50, 470, 250, 20);
        label7.setVisible(true);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(tf2.getText().isEmpty() || tf1.getText().isEmpty() || tf4.getText().isEmpty())) {
                    if (tf2.getText().equals(tf3.getText())) {
                        boolean exists = false;
                        for (Account account : accountList) {
                            if(account.information.getCredentials().getEmail().equals(tf1.getText())) {
                                exists = true;
                            }
                        }
                        if(!exists) {
                        List<String> gamesList = new ArrayList<String>();
                        if (!tf6.getText().equals("")) {
                            String[] games = tf6.getText().split(", *");
                            gamesList.addAll(Arrays.asList(games));
                        }
                        JSONObject newAccount = new JSONObject();
                        List<Character> charactersList = new ArrayList<Character>();
                        JSONArray charactersListJSON = new JSONArray(charactersList);
                        JSONArray gameListJSON = new JSONArray(gamesList);
                        JSONObject credentialsJSON = new JSONObject();
                        credentialsJSON.put("email", tf1.getText());
                        credentialsJSON.put("password", tf2.getText());
                        newAccount.put("credentials", credentialsJSON);
                        newAccount.put("favorite_games", gameListJSON);
                        newAccount.put("maps_completed", 0);
                        newAccount.put("characters", charactersListJSON);
                        newAccount.put("name", tf4.getText());
                        newAccount.put("country", tf5.getText());
                        JSONArray initialJsonArray = (JSONArray) initialJson.get("accounts");
                        initialJsonArray.add(newAccount);
                        initialJson.put("accounts", initialJsonArray);
                        FileWriter fileWriter = null;
                        try {
                            fileWriter = new FileWriter("src/accounts.json");
                            fileWriter.write(initialJson.toJSONString());
                            fileWriter.flush();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                        Credentials credentials = new Credentials(tf1.getText(), tf2.getText());
                        TreeSet<String> favGames = new TreeSet<String>(new MyComparator());
                        favGames.addAll(gamesList);
                        Account.Information information = new Account.Information.InformationBuilder(credentials, tf4.getText())
                                .country(tf5.getText()).games(favGames).build();
                        accountList.add(new Account(information, charactersList, 0));
                        f.dispose();
                        new AuthScreen(accountList);
                        } else {
                            label7.setText("This email is already used");
                        }
                    } else {
                        label7.setText("Passwords does not match");
                    }
                } else {
                    label7.setText("Information incomplete");
                }
            }
        });
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                new AuthScreen(accountList);
            }
        });
        f.add(label1);
        f.add(tf1);
        f.add(label2);
        f.add(tf2);
        f.add(label3);
        f.add(tf3);
        f.add(label4);
        f.add(tf4);
        f.add(label5);
        f.add(tf5);
        f.add(label6);
        f.add(tf6);
        f.add(b1);
        f.add(b2);
        f.add(label7);
        f.setSize(350,600);
        f.setLayout(null);
        f.setVisible(true);
    }
}
