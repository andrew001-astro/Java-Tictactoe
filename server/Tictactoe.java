package server;

import java.util.Arrays;

public class Tictactoe {
    private int[][] tictactoe = new int[3][3];

    public Tictactoe() {
    }

    public void setMark(int row, int col) {
        tictactoe[row][col] = 1;
    }

    public int checkMarks() {
        if (horizontalCheck() >= 3 || verticalCheck() >= 3 || diagonalCheck() >= 3)
            return 3;
        else
            return 0;
    }

    private int horizontalCheck() {
        int complete = 0;
        for (int row = 0; row < 3; row++) {
            complete = 0;
            for (int col = 0; col < 3; col++) {
                if (tictactoe[row][col] > 0)
                    complete++;
            }
            if (complete >= 3)
                return complete;
        }

        return complete;
    }

    private int verticalCheck() {
        int complete = 0;
        for (int col = 0; col < 3; col++) {
            complete = 0;
            for (int row = 0; row < 3; row++) {
                if (tictactoe[row][col] > 0)
                    complete++;
            }
            if (complete >= 3)
                return complete;
        }
        return complete;
    }

    private int diagonalCheck() {
        int complete = 0;
        for (int i = 0; i < 3; i++) {
            if (tictactoe[i][i] <= 0)
                break;
            if (tictactoe[i][i] > 0)
                complete++;
        }

        if (complete >= 3)
            return complete;

        complete = 0;
        int col = 3;
        for (int row = 0; row < 3; row++) {
            col--;
            if (tictactoe[row][col] <= 0)
                break;
            if (tictactoe[row][col] > 0)
                complete++;
        }

        return complete;
    }

    @Override
    public String toString() {
        return Arrays.toString(tictactoe);
    }
}