import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

public class CharacterScreen {
    static int indexCharacter;

    JTextField tf1;
    JFormattedTextField tf2, tf3;
    JList<Character> list;
    JButton button;
    public CharacterScreen(Account currentAccount) {
        DefaultListModel<Character> characterDefaultListModel = new DefaultListModel<>();
        characterDefaultListModel.addAll(currentAccount.characters);
        list = new JList<>(characterDefaultListModel);
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(list.isSelectionEmpty()){
                    tf1.setText("");
                    button.setEnabled(false);
                    return;
                }
                Character character = list.getSelectedValue();
                button.setEnabled(true);
                tf1.setText(character.characterName);
            }
        });
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(list);
        list.setLayoutOrientation(JList.VERTICAL);
        JFrame f = new JFrame("World of Marcel");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setMinimumSize(new Dimension(300, 200));
        f.setLayout(new FlowLayout());
        f.add(scrollPane);
        JPanel p = new JPanel(new BorderLayout());
        JPanel p2 = new JPanel(new BorderLayout());
        JPanel p3 = new JPanel(new BorderLayout());
        JPanel p4 = new JPanel(new BorderLayout());
        JPanel p5 = new JPanel(new BorderLayout());
        JLabel label1 = new JLabel("Chosen Character");
        JLabel label2 = new JLabel("X:");
        JLabel label3 = new JLabel("Y:");
        tf1 = new JTextField();
        tf1.setColumns(50);
        tf1.setEditable(false);
        tf2 = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
        tf2.setColumns(2);
        tf3 = new JFormattedTextField(NumberFormat.INTEGER_FIELD);
        tf3.setColumns(2);
        p5.add(label2, BorderLayout.LINE_START);
        p5.add(tf2, BorderLayout.LINE_END);
        p4.add(label3, BorderLayout.LINE_START);
        p4.add(tf3, BorderLayout.LINE_END);
        p3.add(p5, BorderLayout.LINE_START);
        p3.add(p4, BorderLayout.LINE_END);
        p2.add(label1, BorderLayout.LINE_START);
        p2.add(tf1, BorderLayout.LINE_END);
        p.add(p2, BorderLayout.PAGE_START);
        p.add(p3, BorderLayout.PAGE_END);
        f.add(p);
        button = new JButton("Choose");
        button.setEnabled(false);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                indexCharacter = list.getSelectedIndex();
                new HomeScreen(currentAccount ,list.getSelectedValue(), Integer.parseInt(tf2.getText()), Integer.parseInt(tf3.getText()));
            }
        });
        JButton createButton = new JButton("Create new character");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                new CreateCharacter(currentAccount);
            }
        });
        f.add(button);
        f.add(createButton);
        f.pack();
        f.setVisible(true);
    }
}
