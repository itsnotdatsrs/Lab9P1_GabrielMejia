package Gusanito;

import javax.swing.JOptionPane;
import java.util.*;

public class Gusanito {
    private ArrayList<String> instructions;
    private char[][] board;
    private int snakeX, snakeY, appleX, appleY;

    public Gusanito(int rows, int cols) {
        if (rows < 4 || rows > 10 || cols < 4 || cols > 10) {
            JOptionPane.showMessageDialog(null, "Dimensiones Invalidas");
            System.exit(1);
        }

        instructions = new ArrayList<>();
        board = new char[rows][cols];

        initializeBoard(rows, cols);
        placeObstacles(rows, cols);
        placeSnakeAndApple(rows, cols);
        getUserInstructions();
        playGame();
    }

    private void initializeBoard(int rows, int cols) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = ' ';
            }
        }
    }

    private void placeObstacles(int rows, int cols) {
        Random random = new Random();
        int numObstacles = random.nextInt(cols - rows + 1) + rows;

        for (int i = 0; i < numObstacles; i++) {
            int obstacleX, obstacleY;

            do {
                obstacleX = random.nextInt(rows);
                obstacleY = random.nextInt(cols);
            } while (obstacleX == snakeX && obstacleY == snakeY || obstacleX == appleX && obstacleY == appleY);

            board[obstacleX][obstacleY] = '#';
        }
    }

    private void placeSnakeAndApple(int rows, int cols) {
        Random random = new Random();
        snakeX = random.nextInt(rows);
        snakeY = random.nextInt(cols);
        appleX = random.nextInt(rows);
        appleY = random.nextInt(cols);

        while (appleX == snakeX && appleY == snakeY) {
            appleX = random.nextInt(rows);
            appleY = random.nextInt(cols);
        }

        board[snakeX][snakeY] = 'S';
        board[appleX][appleY] = 'O';
    }

    private void getUserInstructions() {
        StringBuilder boardString = new StringBuilder();

        for (char[] row : board) {
            for (char cell : row) {
                boardString.append("[").append(cell).append("] ");
            }
            boardString.append("\n");
        }

        String input = JOptionPane.showInputDialog(null, "Pasos no seran reflejados hasta el final. Pasos se podran ver despues\n"+boardString+"Ingresar pasos y direccion (Ex: #UP, #DN, #RT, #LT). 'done' para terminar\n",
                "Snake Game", JOptionPane.PLAIN_MESSAGE);

        while (input != null && !input.equalsIgnoreCase("done")) {
            instructions.add(input);
            input = JOptionPane.showInputDialog(null, "Pasos no seran reflejados hasta el final. Pasos se podran ver despues\n"+boardString+"Ingresar pasos y direccion (Ex: #UP, #DN, #RT, #LT). 'done' para terminar\n",
                    "Snake Game", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void playGame() {
        int step = 0;
        while (step < instructions.size()) {
            String result = showStep(step);
            int option = JOptionPane.showOptionDialog(null, result, "Snake",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                    new Object[]{"Siguiente Paso", "Ultimo Paso", "Paso #", "Exit"}, 0);

            if (option == 0) {
                step++;
            } else if (option == 1) {
                step = Math.max(0, step - 1);
            } else if (option == 2) {
                try {
                    step = Integer.parseInt(JOptionPane.showInputDialog("Enter step number:")) - 1;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid step number.");
                }
            } else {
                break;
            }
        }
    }

    private String showStep(int step) {
        if (step < 0 || step >= instructions.size()) {
            return "Invalid step number.";
        }

        String instruction = instructions.get(step);
        int magnitude = Integer.parseInt(instruction.substring(0, instruction.length() - 2));
        char direction = instruction.charAt(instruction.length() - 2);

        // Update snake position based on the instruction
        switch (direction) {
            case 'U':
                moveSnake(-magnitude, 0);
                break;
            case 'D':
                moveSnake(magnitude, 0);
                break;
            case 'R':
                moveSnake(0, magnitude);
                break;
            case 'L':
                moveSnake(0, -magnitude);
                break;
        }

        // Check if the snake goes out of bounds
        if (snakeX < 0 || snakeX >= board.length || snakeY < 0 || snakeY >= board[0].length) {
            return "Te pasaste en paso " + (step + 1) + ".";
        }

        // Check if the snake collides with an obstacle
        if (board[snakeX][snakeY] == '#') {
            return "Chocaste en paso " + (step + 1) + ".";
        }

        // Check if the snake eats the apple
        if (snakeX == appleX && snakeY == appleY) {
            return "Llegaste a la manzana en paso " + (step + 1) + ".";
        }

        // Update the board with the new snake position
        updateBoard();

        // Return the current state of the board
        return "Paso " + (step + 1) + " ejecutado.\n" + getBoardString();
    }

    private void moveSnake(int deltaX, int deltaY) {
        board[snakeX][snakeY] = ' ';

        snakeX += deltaX;
        snakeY += deltaY;
    }

    private void updateBoard() {
        board[snakeX][snakeY] = 'S';
        board[appleX][appleY] = 'O';
    }

    private String getBoardString() {
        StringBuilder boardString = new StringBuilder();

        for (char[] row : board) {
            for (char cell : row) {
                boardString.append("[").append(cell).append("] ");
            }
            boardString.append("\n");
        }

        return boardString.toString();
    }

    public static void main(String[] args) {
        int rows = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of rows (4-10):"));
        int cols = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of columns (4-10):"));

        new Gusanito(rows, cols);
    }
}