package org.example;

import java.util.List;
import java.util.Random;

public class ProverbProtocol {
    private static int playerNumber = 0;
    private final Random random = new Random();
    public static final String EXIT_MESSAGE = "Bye!";

    public enum Status {
        INITIAL_STATE,
        SENT_GREETING,
        SENT_CLUE,
        MAYBE_KEEP_PLAYING,
    }

    private Status state = Status.INITIAL_STATE;

    private final List<String> CLUE_LIST = List.of(
            "At night all the cats",
            "Together",
            "Better late",
            "Tell me and I forget. Teach me and I remember.",
            "Live long"
    );

    private final List<String> ANSWER_LIST = List.of(
            "are gray.",
            "we`re strong.",
            "than sorry.",
            "Involve me and I learn.",
            "and prosper."
    );

    private int currentClue;

    public ProverbProtocol() {
        playerNumber++;
    }

    public String processInput(String theInput) {
        if (theInput == null && state != Status.INITIAL_STATE)
            return processExit();

        switch (state) {
            default:
            case INITIAL_STATE:
                state = Status.SENT_GREETING;
                return String.format("Are you ready player %d?", playerNumber);

            case SENT_GREETING:
                return processGreeting(theInput);

            case SENT_CLUE:
                return processGuess(theInput);

            case MAYBE_KEEP_PLAYING:
                if (theInput.equalsIgnoreCase("y"))
                    return processClue();
                return processExit();
        }
    }

    private String processExit() {
        state = Status.INITIAL_STATE;
        return EXIT_MESSAGE;
    }

    private String processGuess(String theInput) {
        if (theInput.equalsIgnoreCase(ANSWER_LIST.get(currentClue))) {
            state = Status.MAYBE_KEEP_PLAYING;
            return "EXCELENT!" +
                    CLUE_LIST.get(currentClue) + " " + ANSWER_LIST.get(currentClue) + "! " +
                    "Keep playing? (y/n)";
        } else {
            state = Status.SENT_CLUE;
            return "It is supposed to say \"" + ANSWER_LIST.get(currentClue) + "\". " +
                    "Try again! " +
                    CLUE_LIST.get(currentClue);
        }
    }

    private String processGreeting(String theInput) {
        if(theInput.equalsIgnoreCase("Ready")) {
            return processClue();
        } else {
            return "You`re supposed to say \"Ready\"! " +
                    "Try again. " +
                    "Are you ready?";
        }
    }

    private String processClue() {
        currentClue = random.nextInt(CLUE_LIST.size());
        state = Status.SENT_CLUE;
        return CLUE_LIST.get(currentClue);
    }

    public static boolean isEndGame(String fromServer) {
        return fromServer.equals(EXIT_MESSAGE);
    }
}