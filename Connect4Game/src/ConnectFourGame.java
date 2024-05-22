public class ConnectFourGame {
    public static final int ROWS = 6;
    public static final int COLS = 7;
    private char[][] board;
    private char currentPlayer;
    private boolean gameOver;

    public ConnectFourGame() {
        board = new char[ROWS][COLS];
        currentPlayer = 'R';  // Change initial player to 'M'
        gameOver = false;
        initializeBoard();
    }

    public void initializeBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = '-';
            }
        }
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean makeMove(int col) {
        if (col < 0 || col >= COLS || gameOver) {
            return false;
        }
        for (int row = ROWS - 1; row >= 0; row--) {
            if (board[row][col] == '-') {
                board[row][col] = currentPlayer;
                gameOver = checkWin(row, col) || checkDraw();
                if (!gameOver) {
                    currentPlayer = (currentPlayer == 'R') ? 'Y' : 'R';  // Switch between 'M' and 'U'
                }
                return true;
            }
        }
        return false;
    }

    public boolean checkWin(int row, int col) {
        char disc = board[row][col];
        return checkDirection(row, col, 1, 0, disc) || // Vertical
                checkDirection(row, col, 0, 1, disc) || // Horizontal
                checkDirection(row, col, 1, 1, disc) || // Diagonal \
                checkDirection(row, col, 1, -1, disc);  // Diagonal /
    }

    private boolean checkDirection(int row, int col, int dRow, int dCol, char disc) {
        int count = 0;
        for (int i = -3; i <= 3; i++) {
            int r = row + i * dRow;
            int c = col + i * dCol;
            if (r >= 0 && r < ROWS && c >= 0 && c < COLS && board[r][c] == disc) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }

    public boolean checkDraw() {
        for (int i = 0; i < COLS; i++) {
            if (board[0][i] == '-') {
                return false;
            }
        }
        return true;
    }

    public char[][] getBoard() {
        return board;
    }
}
