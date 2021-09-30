import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class TicTacToePvsP {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String[][] gameField = new String[3][3];
    private static final HashSet<Integer> possibleMovesList = new HashSet<>();

    public static void main(String[] args) {
        boolean playerTurn = Math.random() > 0.5;
        boolean winnerStatus = false;

        initGame();
        gameStatusView();
        while (!winnerStatus) {
            gameController(playerMove(playerTurn), playerTurn);
            gameStatusView();
            winnerStatus = winCheck();
            playerTurn = !playerTurn;
            if (isPossibleMovesListEmpty()) {
                break;
            }
        }
        winAnnouncer(playerTurn, winnerStatus);
    }

    private static void gameController(int playerMove, boolean playerTurn) {
        String playerType;
        if (playerTurn) {
            playerType = "o";
        } else {
            playerType = "x";
        }
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[0].length; j++) {
                if (gameField[i][j].equals(String.valueOf(playerMove))) {
                    gameField[i][j] = playerType;
                }
            }
        }
    }

    private static int playerMove(boolean playerTurn) {
        if (playerTurn) {
            System.out.println("Player 'O' choose number of your move.");
        } else {
            System.out.println("Player 'X' choose number of your move.");
        }
        int playerMove = digitAsker();
        while (!isPossibleMove(playerMove)) {
            System.out.println("Bad move, bro! Choose another one");
            playerMove = digitAsker();
        }
        return playerMove;
    }

    private static void initGame() {
        String fields = "123456789";
        int counter = 0;
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[0].length; j++) {
                gameField[i][j] = Character.toString(fields.charAt(counter));
                counter++;
            }
        }
        possibleMovesList.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        System.out.println("GAME IS STARTED!");
    }

    private static void gameStatusView() {
        System.out.println();
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[0].length; j++) {
                System.out.print(gameField[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static boolean isPossibleMove(int playerMove) {
        if (possibleMovesList.contains(playerMove)) {
            possibleMovesList.remove(playerMove);
            return true;
        } else {
            return false;
        }
    }

    private static boolean isPossibleMovesListEmpty() {
        return possibleMovesList.isEmpty();
    }

    private static void winAnnouncer(boolean playerTurn, boolean winnerStatus) {
        if (winnerStatus) {
            if (playerTurn) {
                System.out.println("Congratulations! Player 'X' won!");
            } else {
                System.out.println("Congratulations! Player 'O' won!");
            }
        } else {
            System.out.println("Gentlemen, its a draw!");
        }
    }

    private static boolean winCheck() {
        for (int i = 0; i < 3; i++) {
            if (gameField[i][0].equals(gameField[i][1]) && gameField[i][0].equals(gameField[i][2]) ||
                    (gameField[0][i].equals(gameField[1][i]) && (gameField[0][i].equals(gameField[2][i])))) {
                return true;
            }
        }
        return gameField[0][0].equals(gameField[1][1]) && gameField[1][1].equals(gameField[2][2]) ||
                gameField[2][0].equals(gameField[1][1]) && gameField[1][1].equals(gameField[0][2]);
    }

    private static int digitAsker() {
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.println("Its not a number. Try again");
        }
        return scanner.nextInt();
    }
}