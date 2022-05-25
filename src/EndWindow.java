import javax.swing.*;
import java.awt.*;
public class EndWindow extends JFrame {
    EndWindow endWindow = this;
    public EndWindow(String message) {
        setTitle("Конец игры");
        setBounds(450, 450, 80, 80);
        setBackground(Color.red);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        JLabel jLabelMode = new JLabel(message);
        add(jLabelMode);
        setVisible(true);

    }
}
