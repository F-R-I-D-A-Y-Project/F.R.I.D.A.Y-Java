package chat;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

import model.Model;
import org.jetbrains.annotations.NotNull;

/**
 * Creates a chat window.
 */
public class Chat extends JFrame{

    /**
     * Chat area where the user's prompts and bot's responses are stored
     */
    private final JTextArea chatArea = new JTextArea();
    /**
     * Field where user writes their questions
     */
    private final JTextField chatBox = new JTextField();
    /**
     * NLP Model used for Bot
     */
    private final Model model;

    /**
     * stores the last question made by the user
     */
    private String lastQuestion;

    /**
     * stores the last answer returned by the bot
     */
    private String lastAnswer;

    /**
     * Action Listener that moves prompt from TextBox to TextArea. It also stores the
     * question made by the user and result by the bot
     */
    private final ActionListener AL = e -> {
        String text = chatBox.getText();
        if (text.isEmpty()) {
            return;
        }
        chatArea.append("YOU: " + text + "\n\n");
        lastAnswer = botResponse(text);
        chatArea.append("F.R.I.D.A.Y: " + lastAnswer + "\n\n" + "Please say 'yes' if you are satisfied with your answer\n\n");
        chatBox.setText("");
        lastQuestion = text;
    };


    /**
     * Creates a new chat window.
     * @param pathToBin String
     *                  The path to the auxiliary binary file.
     */
    public Chat(String pathToBin, String pathToDataSet) throws IOException {

        try{
            this.model = new Model(pathToBin, pathToDataSet);
        } catch(IOException e){
            System.out.println("Could not initialize model");
            throw e;
        }
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
    @NotNull
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
    private void editChatBox(@NotNull JFrame frame, int thickness) {

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
    private void editChatArea(@org.jetbrains.annotations.NotNull JFrame frame) {

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
    @NotNull
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
    @NotNull
    private JButton sendButton() {

        JButton send = new JButton("Send");
        send.addActionListener(AL);

        send.setSize(120, 32);
        return send;
    }

    /**
     * Returns the bot's response to the user's input. Stores lastQuestion and lastAnswer to the database
     * if the user so desires (by prompting 'yes')
     * @param input: String
     * @return String
     */
    private String botResponse(String input){
        if(input.equals("yes")){
            try{
                FileWriter fileWriter = new FileWriter("dataBase.csv", true);
                fileWriter.write("\n" + lastQuestion + "; " + lastAnswer);
                fileWriter.close();
            }
            catch (IOException e){

            }
        }
        return model.answerTo(input);
    }
}
