package chat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Model;

public class Chat extends JFrame{
    private JTextArea chatArea = new JTextArea();
    private JTextField chatBox = new JTextField();
    private Model model = new Model();
    public Chat() {
        JFrame frame = new JFrame();
        frame.setTitle("F.R.I.D.A.Y");
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(chatArea);
        frame.add(chatBox);

        chatArea.setSize(500, 400);
        chatArea.setLocation(2, 2);

        chatBox.setSize(500, 30);
        chatBox.setLocation(2, 420);

        chatBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String gtext = chatBox.getText();
                chatArea.append("YOU: " + gtext + "\n");
                chatArea.append("F.R.I.D.A.Y: " + botResponse(gtext) + "\n");
                chatBox.setText("");
            }
        });

    }
    private String botResponse(String input){
//        model
        return "Huh?";
    }
}
