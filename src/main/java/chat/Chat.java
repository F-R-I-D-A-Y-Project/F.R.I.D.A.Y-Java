package chat;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Model;

public class Chat extends JFrame{
    private final JTextArea chatArea = new JTextArea();
    private final JTextField chatBox = new JTextField();
    private final Model model = new Model("");

    public Chat() {
        JFrame frame = new JFrame();
        frame.setTitle("F.R.I.D.A.Y");
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setSize(620, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(chatArea);
        frame.add(chatBox);

        chatArea.setSize(620, 700);
        chatArea.setLocation(2, 2);
        chatArea.append("F.R.I.D.A.Y: Hello, I am F.R.I.D.A.Y, your personal assistant. How may I help you?\n\n");
        Font font = new Font("Arial", Font.BOLD, 16);
        chatArea.setFont(font);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setEditable(false);

        chatBox.setSize(500, 30);
        chatBox.setLocation(0, 702);
        int thickness = 2;
        Border customBorder = BorderFactory.createMatteBorder(thickness, thickness, thickness, thickness, Color.BLACK);
        chatBox.setBorder(customBorder);
        chatBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = chatBox.getText();
                if (text.isEmpty()) {
                    return;
                }
                chatArea.append("YOU: " + text + "\n\n");
                chatArea.append("F.R.I.D.A.Y: " + botResponse(text) + "\n\n");
                chatBox.setText("");
            }
        });

        JButton send = sendButton();
        send.setLocation(500, 700);
        frame.add(send);

        int scrollBarThickness = 20;
        JScrollPane scroll = new JScrollPane(chatArea);
        scroll.setSize(620, 700);
        scroll.setLocation(2,2);
        frame.add(scroll);

    }

    private JButton sendButton() {
        JButton send = new JButton("Send");
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = chatBox.getText();
                if (text.isEmpty()) {
                    return;
                }
                chatArea.append("YOU: " + text + "\n\n");
                chatArea.append("F.R.I.D.A.Y: " + botResponse(text) + "\n\n");
                chatBox.setText("");
            }
        });

        send.setSize(120, 32);
        return send;
    }

    private String botResponse(String input){
        return model.answerTo(input);
    }
}
