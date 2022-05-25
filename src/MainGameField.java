import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MainGameField extends JPanel {
    private static MainGameField instance = null;
    public static final int FIELD_SIZE = 450;
    public final String NOT_SIGN = "*";
    boolean gameOver = false;
    String gameOverMessage = "";
    static int linesCount = 3;
    int cellSize;
    int x;
    int y;
    boolean nextTurn = false;
    Player player1;
    Player player2;
    int gameMode = 1;
    public String[][] cell;

    public static synchronized MainGameField getInstance() {
        if (instance == null)
            instance = new MainGameField();
        return instance;
    }

    void startNewGame() {
        gameOver = false;
        gameOverMessage = "";
        cellSize = FIELD_SIZE / linesCount;
        cell = new String[linesCount][linesCount];
        repaint();
        for (int i = 0; i < linesCount; i++) {
            for (int j = 0; j < linesCount; j++) {
                cell[i][j] = NOT_SIGN;
            }
        }
        setVisible(true);
    }

    private MainGameField() {
        setVisible(false);
        player1 = new Player("X");
        player2 = new Player("O");


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                x = e.getX() / cellSize;
                y = e.getY() / cellSize;

                if (!gameOver) {
                    switch (gameMode) {
                        case 1: {
                            twoPlayersMode();
                            break;
                        }
                    }
                }
            }
        });
    }

    void twoPlayersMode() {
        if (player1.isShotReady == 1) {
            nextTurn = true;
            player2.isShotReady = 0;
            player1.shot(x,y);
        }
        if (player1.win()) {
            gameOver = true;
            gameOverMessage = "Player 1 WIN!!!";
        }
        repaint();
        if (isFieldFull() && !player1.win() && !player2.win()) {
            gameOver = true;
            gameOverMessage = "DRAW!!!";
        }
        if (player2.isShotReady == 1) {
            nextTurn = false;
            player1.isShotReady = 0;
            player2.shot(x,y);
        }
        if (!gameOver) {
            player2.shot(x, y);
        }
        if (player2.win()) {
            gameOver = true;
            gameOverMessage = "Player 2 WIN!!!";
        }
        repaint();
        if (isFieldFull() && !player2.win() && !player1.win()) {
            gameOver = true;
            gameOverMessage = "DRAW!!!";
        }
        if (nextTurn) {
            player1.isShotReady = 0;
            player2.isShotReady = 1;
        }
        else {
            player1.isShotReady = 1;
            player2.isShotReady = 0;
        }
    }

    boolean isCellBusy(int x, int y) {
        if (x < 0 || y < 0 || x > linesCount - 1 || y > linesCount - 1) {
            return false;
        }
        return cell[x][y] != NOT_SIGN;
    }

    public boolean isFieldFull() {
        for (int i = 0; i < linesCount; i++) {
            for (int j = 0; j < linesCount; j++) {
                if (cell[i][j] == NOT_SIGN)
                    return false;
            }
        }
        return true;
    }

    public boolean checkLine(int start_x, int start_y, int dx, int dy, String sign) {
        for (int i = 0; i < linesCount; i++) {
            if (cell[start_x + i * dx][start_y + i * dy] != sign)
                return false;
        }
        return true;
    }

    public boolean checkWin(String sign) {
        for (int i = 0; i < linesCount; i++) {
            // проверяем строки
            if (checkLine(i, 0, 0, 1, sign))
                return true;
            // проверяем столбцы
            if (checkLine(0, i, 1, 0, sign))
                return true;
        }
        // проверяем диагонали
        if (checkLine(0, 0, 1, 1, sign))
            return true;
        if (checkLine(0, linesCount - 1, 1, -1, sign))
            return true;
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i <= this.linesCount; i++) {
            g.drawLine(0, i * this.cellSize, FIELD_SIZE, i * this.cellSize);
            g.drawLine(i * this.cellSize, 0, i * this.cellSize, FIELD_SIZE);
        }
        for (int i = 0; i < linesCount; i++) {
            for (int j = 0; j < linesCount; j++) {
                if (cell[i][j] != NOT_SIGN) {
                    if (cell[i][j] == "X") {
                        g.setColor(Color.BLACK);
                        g.drawLine((i * cellSize), (j * cellSize), (i + 1) * cellSize, (j + 1) * cellSize);
                        g.drawLine((i + 1) * cellSize, (j * cellSize), (i * cellSize), (j + 1) * cellSize);
                    }
                    if (cell[i][j] == "O") {
                        g.setColor(Color.BLACK);
                        g.drawOval((i * cellSize), (j * cellSize), cellSize, cellSize);
                    }
                }
            }
        }

        if (gameOver) {
            EndWindow endWindow = new EndWindow(gameOverMessage);
        }
    }
}
