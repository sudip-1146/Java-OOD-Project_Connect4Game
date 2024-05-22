import java.util.Random;

public class ConnectFourAI {
    private Random random;

    public ConnectFourAI() {
        random = new Random();
    }

    public int getNextMove(char[][] board) {
        int col;
        do {
            col = random.nextInt(ConnectFourGame.COLS);
        } while (board[0][col] != '-');
        return col;
    }
}
