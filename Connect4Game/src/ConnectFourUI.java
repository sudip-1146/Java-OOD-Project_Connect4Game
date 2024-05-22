import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectFourUI extends JFrame {
    private ConnectFourGame game;
    private ConnectFourAI ai;
    private JButton[] buttons;
    private JPanel boardPanel;
    private JLabel[][] cells;
    private boolean singlePlayerMode;

    public ConnectFourUI(boolean singlePlayerMode) {
        this.singlePlayerMode = singlePlayerMode;
        game = new ConnectFourGame();
        if (singlePlayerMode) {
            ai = new ConnectFourAI();
        }
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Connect Four");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(1, ConnectFourGame.COLS));
        buttons = new JButton[ConnectFourGame.COLS];
        for (int i = 0; i < ConnectFourGame.COLS; i++) {
            buttons[i] = new JButton("↓");
            int col = i;
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleButtonClick(col);
                }
            });
            topPanel.add(buttons[i]);
        }
        add(topPanel, BorderLayout.NORTH);

        boardPanel = new JPanel(new GridLayout(ConnectFourGame.ROWS, ConnectFourGame.COLS));
        cells = new JLabel[ConnectFourGame.ROWS][ConnectFourGame.COLS];
        for (int i = 0; i < ConnectFourGame.ROWS; i++) {
            for (int j = 0; j < ConnectFourGame.COLS; j++) {
                cells[i][j] = new JLabel("-", SwingConstants.CENTER);
                cells[i][j].setFont(new Font("Arial", Font.BOLD, 40));
                cells[i][j].setOpaque(true);
                cells[i][j].setBackground(Color.WHITE);
                cells[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                boardPanel.add(cells[i][j]);
            }
        }
        add(boardPanel, BorderLayout.CENTER);
    }

    private void handleButtonClick(int col) {
        if (game.isGameOver() || !game.makeMove(col)) {
            return;
        }
        updateBoard();
        if (game.isGameOver()) {
            JOptionPane.showMessageDialog(this, "Game Over! " + (game.checkDraw() ? "It's a draw!" : game.getCurrentPlayer() + " wins!"));
            resetGame();
            return;
        }
        if (singlePlayerMode && game.getCurrentPlayer() == 'Y') {
            int aiMove = ai.getNextMove(game.getBoard());
            game.makeMove(aiMove);
            updateBoard();
            if (game.isGameOver()) {
                JOptionPane.showMessageDialog(this, "Game Over! " + (game.checkDraw() ? "It's a draw!" : game.getCurrentPlayer() + " wins!"));
                resetGame();
            }
        }
    }

    private void updateBoard() {
        char[][] board = game.getBoard();
        for (int i = 0; i < ConnectFourGame.ROWS; i++) {
            for (int j = 0; j < ConnectFourGame.COLS; j++) {
                if (board[i][j] == 'R') {
                    cells[i][j].setText("R");
                    cells[i][j].setBackground(Color.CYAN);
                } else if (board[i][j] == 'Y') {
                    cells[i][j].setText("Y");
                    cells[i][j].setBackground(Color.ORANGE);
                } else {
                    cells[i][j].setText("-");
                    cells[i][j].setBackground(Color.WHITE);
                }
            }
        }
    }

    private void resetGame() {
        game = new ConnectFourGame();
        updateBoard();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                boolean singlePlayerMode = JOptionPane.showConfirmDialog(null, "Do you want to play in single-player mode?", "Game Mode", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
                ConnectFourUI ui = new ConnectFourUI(singlePlayerMode);
                ui.setVisible(true);
            }
        });
    }
}
