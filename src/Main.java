import java.util.*;

public class Main {
    static final String[] elements = new String[]{"ROCK", "SCISSORS", "PAPER", "LIZARD", "SPOCK"};
    static final ArrayList<String> names = new ArrayList<>(List.of("Chica", "Coco", "Pumpkin",
            "Bubba", "Homie", "Hero", "Dottie", "Bug", "Cutie Pie", "Pancake",
            "Teacup", "Rapunzel", "Cowboy", "Pug", "Clown", "Sherlock", "Egghead",
            "Oldie", "Boo", "Goldie", "Bestie", "Weirdo", "Scary Pumpkin", "Roar Sweetie", "Sky Bully",
            "Titanium", "The Brown Fox", "Tweek", "Cereal Killer"));
    static Random rand = new Random();
    static int[] scores = new int[2];

    static boolean win = false;

    // =========== Playing levels ===========

    static void playLevel1(String userName) {
        System.out.println("Level 1");
        System.out.println("Defeat 1 opponent!");
        String opponent = getOpponents(1)[0];
        playCompUser(opponent, userName, 3);
        int result = returnResult();
        if (result > 0) win = true;
        printResult(userName, opponent, result);
        scores[0] = 0;
        scores[1] = 0;
    }

    static void playLevel2(String userName) {
        String[] opponents = getOpponents(3);
        System.out.println("================================");
        System.out.println("Level 2");
        System.out.println("Defeat 3 opponents!");

        int result;
        int userScores = 0;
        for (String opponent : opponents) {
            System.out.println("================================");
            playCompUser(opponent, userName, 3);
            result = returnResult();
            if (result > 0) userScores += 1;
            printResult(userName, opponent, result);
            scores[0] = 0;
            scores[1] = 0;
        }
        if (userScores > 1) win = true;
    }

    static void playLevel3(String userName) {
        System.out.println("================================");
        System.out.println("Level 3");

        int numberOfOpponents = askInt(1, 9, "Enter the number of opponents (1-9)");
        String[] opponents = getOpponents(numberOfOpponents);

        int numOfRounds = askInt(1, 5, "Enter the number of rounds (1-5)");

        int[] points = new int[opponents.length + 1];

        // =========== Initializing an array of players ===========
        String[] players = new String[opponents.length + 1];
        Arrays.fill(scores, 0);
        players[0] = userName;
        System.arraycopy(opponents, 0, players, 1, players.length - 1);

        // =========== Play User VS Computer opponents ===========
        int result;
        for (int i = 0; i < opponents.length; i++) {
            playCompUser(opponents[i], userName, numOfRounds);
            result = returnResult();
            printResult(userName, opponents[i], result);
            switch (result) {
                case 0 -> {
                    points[0] += 1;
                    points[i + 1] += 1;
                }
                case 1 -> points[0] += 3;
                case -1 -> points[i + 1] += 3;
            }
            scores[0] = 0;
            scores[1] = 0;
        }

        // =========== Play Computer VS Computer ===========
        for (int i = 0; i < opponents.length - 1; i++) {
            for (int j = i + 1; j < opponents.length; j++) {
                playCompComp(opponents[i], opponents[j], numOfRounds);
                result = returnResult();
                printResult(opponents[i], opponents[j], result);
                switch (result) {
                    case 0 -> {
                        points[i + 1] += 1;
                        points[j + 1] += 1;
                    }
                    case 1 -> points[i + 1] += 3;
                    case -1 -> points[j + 1] += 3;
                }
                scores[0] = 0;
                scores[1] = 0;
            }
        }

        printPoints(players, points);

        // =========== Getting the winner ===========
        int winnerPoints = points[0];
        String winner = players[0];
        for (int i = 1; i < points.length; i++) {
            if (points[i] == winnerPoints) {
                winner = "";
            }
            if (points[i] > winnerPoints) {
                winner = players[i];
                winnerPoints = points[i];
            }

        }

        if (winner.length() > 0) System.out.println("The winner is " + winner + "!");
        else System.out.println("There is no winner");

        if (winner.equals(userName)) win = true;
    }

    // =========== Playing the round ===========

    static void playRound(Round round, String player1, String player2) {
        switch (round.result) {
            case -1 -> scores[1]++;
            case 1 -> scores[0]++;
        }
        System.out.printf("%s's choice: %s%n", player1, elements[round.player1Element]);
        System.out.printf("%s's choice: %s%n", player2, elements[round.player2Element]);
        System.out.println("..........................");
        System.out.printf("Scores: %d : %d%n", scores[0], scores[1]);
    }

    static void playCompUser(String opponent, String userName, int numOfRounds) {
        Round round;
        int roundNo = 1;
        System.out.println("Your opponent: " + opponent);
        while (roundNo <= numOfRounds) {
            System.out.println("================================");
            System.out.printf("Round No. %d%n", roundNo);
            System.out.println("--------------------------------");
            printElements();
            round = new Round(askInt(0, 4, "Pick an element"), pickElement());
            playRound(round, userName, opponent);
            roundNo++;
        }
    }

    static void playCompComp(String player1, String player2, int numOfRounds) {
        Round round;
        int roundNo = 1;
        System.out.println("Player 1: " + player1);
        System.out.println("Player 2: " + player2);
        while (roundNo <= numOfRounds) {
            System.out.println("================================");
            System.out.printf("Round No. %d%n", roundNo);
            System.out.println("--------------------------------");

            round = new Round(pickElement(), pickElement());
            playRound(round, player1, player2);
            roundNo++;
        }
    }

    // =========== Helper methods ===========

    static String[] getOpponents(int numOfOpponents) {
        String[] opponents = new String[numOfOpponents];
        for (int i = 0; i < numOfOpponents; i++) {
            int randInd = rand.nextInt(names.size());
            opponents[i] = names.get(randInd);
            names.remove(randInd);
        }
        return opponents;
    }

    static int askInt(int from, int to, String question) {
        Scanner scan = new Scanner(System.in);
        int choice;
        System.out.println(question);

        while (true) {
            try {
                System.out.print("Your choice: ");
                choice = scan.nextInt();
                if (choice >= from && choice <= to) return choice;
                throw new InputMismatchException();
            } catch (InputMismatchException e) {
                System.out.println("Incorrect Input!");
                scan.nextLine();
            }
        }
    }
    static int pickElement() {
        Random rand = new Random();
        return rand.nextInt(5);
    }

    static int returnResult() {
        if (scores[0] == scores[1]) return 0;
        else if (scores[0] > scores[1]) return 1;
        else return -1;
    }

    // =========== Printing information ===========
    static void printElements() {
        System.out.println("0 - ROCK");
        System.out.println("1 - SCISSORS");
        System.out.println("2 - PAPER");
        System.out.println("3 - LIZARD");
        System.out.println("4 - SPOCK");
        System.out.println("..........................");
    }

    static void printPoints(String[] players, int[] points) {
        for (int i = 0; i < players.length; i++) {
            System.out.printf("%-15s %d%n", players[i], points[i]);
        }
    }
    static void printResult(String player1, String player2, int result) {
        switch (result) {
            case 0 -> System.out.println("It's a draw");
            case 1 -> System.out.printf("%s won! %s lost...%n", player1, player2);
            case -1 -> System.out.printf("%s won! %s lost...%n", player2, player1);
        }
        System.out.println("================================");
    }
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = scan.nextLine();

        while (!win) {
            playLevel1(name);
        }

        win = false;
        while (!win) {
            playLevel2(name);
        }

        win = false;
        while (!win) {
            playLevel3(name);
        }

    }
}