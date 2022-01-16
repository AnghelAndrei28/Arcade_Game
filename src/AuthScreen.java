import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class AuthScreen {

    static int indexAccount;

    public AuthScreen(List<Account> accountList) {
        JFrame f = new JFrame("World of Marcel");
        JLabel label1 = new JLabel("Email");
        label1.setBounds(50, 50, 150, 20);
        JTextField tf1=new JTextField();
        tf1.setBounds(50,70,150,20);
        JLabel label2 = new JLabel("Password");
        label2.setBounds(50, 100, 150, 20);
        JTextField tf2=new JTextField();
        tf2.setBounds(50,120,150,20);
        JButton b1 = new JButton("Sign in");
        b1.setBounds(50,170,150,50);
        JLabel label3 = new JLabel("Invalid Account");
        label3.setBounds(50, 285, 150, 20);
        label3.setVisible(false);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label3.setVisible(false);
                String email = tf1.getText();
                String password = tf2.getText();
                Account currentAccount = null;
                boolean loggedIn = false;
                int index = 0;
                for (Account account : accountList) {
                    if (account.information.getCredentials().getEmail().equals(email) && account.information.getCredentials()
                            .getPassword().equals(password)) {
                        loggedIn = true;
                        currentAccount = account;
                        indexAccount = index;
                        break;
                    }
                    index++;
                }
                if(!loggedIn) {
                    label3.setVisible(true);
                } else {
                    f.dispose();
                    new CharacterScreen(currentAccount);
                }

            }
        });
        JButton b2 = new JButton("Sign Up");
        b2.setBounds(50,225,150,50);
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                new RegisterScreen(accountList);
            }
        });
        f.add(label1);
        f.add(tf1);
        f.add(label2);
        f.add(tf2);
        f.add(b1);
        f.add(b2);
        f.add(label3);
        f.setSize(350,350);
        f.setLayout(null);
        f.setVisible(true);
    }
}
