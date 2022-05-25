import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class GameSettingsForm extends JFrame {
    GameSettingsForm gameSettingsForm = this;
    public GameSettingsForm() {
        setTitle("Настройки игры");
        setBounds(450, 450, 240, 190);
        setResizable(false);
        setBackground(Color.red);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        JLabel jLabelMode = new JLabel("Выберете размер поля:");
        add(jLabelMode);
        JLabel def = new JLabel("По умолчанию 3Х3");
        add(def);
        JTextField jTextFieldLinesCount = new JTextField();
        jTextFieldLinesCount.setMaximumSize(new Dimension(400, 40));
        add(jTextFieldLinesCount);
        JButton jButtonSetSettings = new JButton("Начать игру!");
        jButtonSetSettings.setSize(600,40);
        add(jButtonSetSettings);
        setVisible(true);


        jButtonSetSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainGameField gameField = MainGameField.getInstance();
                if (jTextFieldLinesCount.getText().isEmpty()) {
                    gameField.linesCount = 3;
                }
                else {
                    try {
                        gameField.linesCount = Integer.parseInt(jTextFieldLinesCount.getText());
                    }
                    catch (NumberFormatException ex) {
                        System.out.println("Нерпавильный ввод данных");
                    }
                }
                gameField.startNewGame();
                gameSettingsForm.setVisible(false);
            }
        });
    }
}
