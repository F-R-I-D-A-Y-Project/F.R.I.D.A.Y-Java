package chat;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

import model.Model;

public class Chat extends JFrame{
    private final JTextArea chatArea = new JTextArea();
    private final JTextField chatBox = new JTextField();
    private final Model model = new Model("opennlp-en-ud-ewt-sentence-1.0-1.9.3.bin");
    private final ActionListener AL = e -> {
        String text = chatBox.getText();
        if (text.isEmpty()) {
            return;
        }
        chatArea.append("YOU: " + text + "\n\n");
        chatArea.append("F.R.I.D.A.Y: " + botResponse(text) + "\n\n");
        chatBox.setText("");
    };

    /**
     * Creates a chat window.
     */
    public Chat() throws IOException {
        JFrame frame = createFrame();

        editChatArea(frame);

        int thickness = 2;
        editChatBox(frame, thickness);

        JButton send = sendButton();
        send.setLocation(500, 700);
        frame.add(send);

        JScrollPane scroll = createScrollPanel();
        frame.add(scroll);
    }

    /**
     * Creates a new scroll panel.
     * @return JScrollPane
     */
    private JScrollPane createScrollPanel() {
        JScrollPane scroll = new JScrollPane(chatArea);
        scroll.setSize(620, 700);
        scroll.setLocation(2,2);
        return scroll;
    }

    /**
     * Edits the chat box.
     * @param frame: JFrame
     * @param thickness: int
     */
    private void editChatBox(JFrame frame, int thickness) {
        chatBox.setSize(500, 30);
        chatBox.setLocation(0, 702);
        Border customBorder = BorderFactory.createMatteBorder(thickness, thickness, thickness, thickness, Color.BLACK);
        chatBox.setBorder(customBorder);
        chatBox.addActionListener(AL);
        frame.add(chatBox);

    }

    /**
     * Edits the chat area.
     * @param frame: JFrame
     */
    private void editChatArea(JFrame frame) {
        frame.add(chatArea);
        chatArea.setSize(620, 700);
        chatArea.setLocation(2, 2);
        chatArea.append("F.R.I.D.A.Y: Hello, I am F.R.I.D.A.Y, your personal assistant. How may I help you?\n\n");
        Font font = new Font("Arial", Font.BOLD, 16);
        chatArea.setFont(font);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setEditable(false);
    }

    /**
     * Creates a new JFrame.
     * @return JFrame
     */
    private JFrame createFrame() {
        JFrame frame = new JFrame();
        frame.setTitle("F.R.I.D.A.Y");
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setSize(620, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        return frame;
    }

    /**
     * Creates a button that sends the text in the chat box to the chat area.
     * @return JButton
     */
    private JButton sendButton() {
        JButton send = new JButton("Send");
        send.addActionListener(AL);

        send.setSize(120, 32);
        return send;
    }

    /**
     * Returns the bot's response to the user's input.
     * @param input: String
     * @return String
     */
    private String botResponse(String input){
        return model.answerTo(input);
    }
}
