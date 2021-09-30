import java.util.*;

public class TicTacToeVsComp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String[][] gameField = new String[3][3];
    private static final HashSet<String> possibleMovesList = new HashSet<>();
    private static final String PLAYER_O = "o";
    private static final String COMP_X = "x";
    private static final String NOT_FIND = "not find";

    public static void main(String[] args) {
        boolean winnerStatus = false;
        boolean playerTurn = Math.random() > 0.5;
        initGame();
        gameTableView();
        while (!winnerStatus) {
            gameController(playerMove(playerTurn), playerTurn);
            gameTableView();
            playerTurn = !playerTurn;
            winnerStatus = winCheck();
            if (isPossibleMovesListEmpty()) {
                break;
            }
        }
        winAnnouncer(playerTurn, winnerStatus);
    }

    private static void gameController(String playerMove, boolean playerTurn) {
        String playerType;
        if (playerTurn) {
            playerType = PLAYER_O;
        } else {
            playerType = COMP_X;
        }
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[0].length; j++) {
                if (gameField[i][j].equals(playerMove)) {
                    gameField[i][j] = playerType;
                }
            }
        }
    }

    private static String playerMove(boolean playerTurn) {
        String move;
        if (playerTurn) {
            System.out.println("Player 'O'\nChoose number of your move:");
            move = numberAsk();
        } else {
            move = compMove();
            System.out.println("Computer 'X'\nComp move is: " + move);
        }
        while (!isPossibleMove(move)) {
            System.out.println("Bad move, bro! Choose another one");
            move = numberAsk();
        }
        return move;
    }

    private static boolean isPossibleMove(String playerMove) {
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

    private static String compMove() {
        String move;
        if (possibleMovesList.contains("5")) {
            move = "5";
        } else {
            if (!compMoveHorizontalAnalyzer().equals(NOT_FIND)) {
                return compMoveHorizontalAnalyzer();
            }
            if (!compMoveVerticalAnalyzer().equals(NOT_FIND)) {
                return compMoveVerticalAnalyzer();
            }
            if (!compMoveDiagonalAnalyzer().equals(NOT_FIND)) {
                return compMoveDiagonalAnalyzer();
            } else {
                return compRandomMove();
            }
        }
        return move;
    }

    private static String compRandomMove() {
        List<String> moves = new ArrayList<>(possibleMovesList);
        return moves.get((int) (Math.random() * (possibleMovesList.size() - 1)));
    }

    private static String compMoveHorizontalAnalyzer() {
        String bestMove = NOT_FIND;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 1; j++) {
                if (gameField[i][j].equals(gameField[i][j + 1]) && isEmpty(gameField[i][j + 2])) {
                    bestMove = gameField[i][j + 2];
                    if (gameField[i][j].equals(COMP_X)) {
                        return bestMove;
                    }
                }
                if (gameField[i][j + 1].equals(gameField[i][j + 2]) && isEmpty(gameField[i][j])) {
                    bestMove = gameField[i][j];
                    if (gameField[i][j + 1].equals(COMP_X)) {
                        return bestMove;
                    }
                }
                if (gameField[i][j].equals(gameField[i][j + 2]) && isEmpty(gameField[i][j + 1])) {
                    bestMove = gameField[i][j + 1];
                    if (gameField[i][j].equals(COMP_X)) {
                        return bestMove;
                    }
                }
            }
        }
        return bestMove;
    }

    private static String compMoveVerticalAnalyzer() {
        String bestMove = NOT_FIND;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameField[i][j].equals(gameField[i + 1][j]) && isEmpty(gameField[i + 2][j])) {
                    bestMove = gameField[i + 2][j];
                    if (gameField[i][j].equals(COMP_X)) {
                        return bestMove;
                    }
                }
                if (gameField[i][j].equals(gameField[i + 2][j]) && isEmpty(gameField[i + 1][j])) {
                    bestMove = gameField[i + 1][j];
                    if (gameField[i][j].equals(COMP_X)) {
                        return bestMove;
                    }
                }
                if (gameField[i + 1][j].equals(gameField[i + 2][j]) && isEmpty(gameField[i][j])) {
                    bestMove = gameField[i][j];
                    if (gameField[i + 1][j].equals(COMP_X)) {
                        return bestMove;
                    }
                }
            }
        }
        return bestMove;
    }

    private static String compMoveDiagonalAnalyzer() {
        String bestMove = NOT_FIND;
        for (int i = 0; i < 1; i++) {
            if (gameField[i][0].equals(gameField[i + 1][1]) && isEmpty(gameField[i + 2][2])) {
                bestMove = gameField[i + 2][2];
            }
            if (gameField[i][0].equals(gameField[i + 2][2]) && isEmpty(gameField[i + 1][1])) {
                bestMove = gameField[i + 1][1];
            }
            if (gameField[i + 1][1].equals(gameField[i + 2][2]) && isEmpty(gameField[i][0])) {
                bestMove = gameField[i][0];
            }
            if (gameField[i + 2][0].equals(gameField[i + 1][1]) && isEmpty(gameField[i][2])) {
                bestMove = gameField[i][2];
            }
            if (gameField[i + 2][0].equals(gameField[i][2]) && !isEmpty(gameField[i + 1][1])) {
                bestMove = gameField[i + 1][1];
            }
            if (gameField[i + 1][1].equals(gameField[i][2]) && isEmpty(gameField[i + 2][0])) {
                bestMove = gameField[i + 2][0];
            }
        }
        return bestMove;
    }

    private static boolean isEmpty(String field) {
        return !field.equals("o") && !field.equals("x");
    }

    private static void winAnnouncer(boolean playerTurn, boolean winnerStatus) {
        if (winnerStatus) {
            if (playerTurn) {
                System.out.println("Game Over!\nComputer 'X' won!");
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

    private static void initGame() {
        String fields = "123456789";
        int counter = 0;
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[0].length; j++) {
                gameField[i][j] = Character.toString(fields.charAt(counter));
                counter++;
            }
        }
        possibleMovesList.addAll(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9"));
        System.out.println("GAME IS STARTED!\n");
    }

    private static void gameTableView() {
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[0].length; j++) {
                System.out.print(gameField[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static String numberAsk() {
        return scanner.next();
    }
}
