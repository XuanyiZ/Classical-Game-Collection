package com.zxs.games.SudokuModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

/**
 * This class represents a Sudoku game. It contains the solution, the user
 * input, the selected number and methods to check the validation of the user
 * input.
 */
public class Game extends Observable {
    private int[][] solution;       // Generated solution.
    private int[][] game;           // Generated game with user input.
    private boolean[][] check;      // Holder for checking validity of game.
    private int selectedNumber;     // Selected number by user.

    /**
     * Constructor
     */
    public Game() {
        newGame();
        check = new boolean[9][9];
    }

    /**
     * Generates a new Sudoku game.
     * All observers will be notified, update action: new game.
     */
    public void newGame() {
        solution = generateSolution(new int[9][9], 0);
        game = generateGame(copy(solution));
        setChanged();
        notifyObservers(UpdateAction.NEW_GAME);
    }

    /**
     * Checks user input against the solution and puts it into a check matrix.
     * All observers will be notified, update action: check.
     */
    public void checkGame() {
        selectedNumber = 0;
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++)
                check[y][x] = game[y][x] == solution[y][x];
        }
        setChanged();
        notifyObservers(UpdateAction.CHECK);
    }


    /**
     * Sets selected number to user input.
     * All observers will be notified, update action: selected number.
     */
    public void setSelectedNumber(int selectedNumber) {
        this.selectedNumber = selectedNumber;
        setChanged();
        notifyObservers(UpdateAction.SELECTED_NUMBER);
    }

    /**
     * Returns number selected user.
     */
    public int getSelectedNumber() {
        return selectedNumber;
    }

    /**
     * Returns whether selected number is candidate at given position.
     */
    public boolean isSelectedNumberCandidate(int x, int y) {
        return game[y][x] == 0 && isPossibleX(game, y, selectedNumber)
                && isPossibleY(game, x, selectedNumber) && isPossibleBlock(game, x, y, selectedNumber);
    }

    /**
     * Sets given number on given position in the game.
     */
    public void setNumber(int x, int y, int number) {
        game[y][x] = number;
    }

    /**
     * Returns number of given position.
     */
    public int getNumber(int x, int y) {
        return game[y][x];
    }

    /**
     * Returns whether user input is valid of given position.
     *
     */
    public boolean isCheckValid(int x, int y) {
        return check[y][x];
    }

    /**
     * Returns whether given number is candidate on x axis for given game.
     */
    private boolean isPossibleX(int[][] game, int y, int number) {
        for (int x = 0; x < 9; x++) {
            if (game[y][x] == number)
                return false;
        }
        return true;
    }

    /**
     * Returns whether given number is candidate on y axis for given game.
     */
    private boolean isPossibleY(int[][] game, int x, int number) {
        for (int y = 0; y < 9; y++) {
            if (game[y][x] == number)
                return false;
        }
        return true;
    }

    /**
     * Returns whether given number is candidate in block for given game.
     *
     */
    private boolean isPossibleBlock(int[][] game, int x, int y, int number) {
        int x1;
        int y1;
        x1 = x < 3? 0:x<6?3:6;
        y1 = y < 3? 0:y<6?3:6;
        for (int yy = y1; yy < y1 + 3; yy++) {
            for (int xx = x1; xx < x1 + 3; xx++) {
                if (game[yy][xx] == number)
                    return false;
            }
        }
        return true;
    }

    /**
     * Returns next posible number from list for given position or -1 when list
     * is empty.
     */
    private int getNextPossibleNumber(int[][] game, int x, int y, List<Integer> numbers) {
        while (numbers.size() > 0) {
            int number = numbers.remove(0);
            if (isPossibleX(game, y, number) && isPossibleY(game, x, number) && isPossibleBlock(game, x, y, number))
                return number;
        }
        return -1;
    }

    /**
     * Generates Sudoku game solution.
     */
    private int[][] generateSolution(int[][] game, int index) {
        if (index > 80)
            return game;

        int x = index % 9;
        int y = index / 9;

        List<Integer> numbers = new ArrayList<Integer>();
        for (int i = 1; i <= 9; i++) {numbers.add(i);}
        Collections.shuffle(numbers);

        while (numbers.size() > 0) {
            int number = getNextPossibleNumber(game, x, y, numbers);
            if (number == -1)
                return null;

            game[y][x] = number;
            int[][] tmpGame = generateSolution(game, index + 1);
            if (tmpGame != null)
                return tmpGame;
            game[y][x] = 0;
        }

        return null;
    }

    /**
     * Generates Sudoku game from solution.
     */
    private int[][] generateGame(int[][] game) {
        List<Integer> positions = new ArrayList<Integer>();
        for (int i = 0; i < 81; i++)
            positions.add(i);
        Collections.shuffle(positions);
        return generateGame(game, positions);
    }

    /**
     * Generates Sudoku game from solution, user should use the other
     * generateGame method. This method simply removes a number at a position.
     * If the game isn't anymore valid after this action, the game will be
     * brought back to previous state.
     */
    private int[][] generateGame(int[][] game, List<Integer> positions) {
        while (positions.size() > 0) {
            int position = positions.remove(0);
            int x = position % 9;
            int y = position / 9;
            int temp = game[y][x];
            game[y][x] = 0;

            if (!isValid(game))
                game[y][x] = temp;
        }
        return game;
    }

    /**
     * Checks whether given game is valid.
     *
     */
    private boolean isValid(int[][] game) {
        return isValid(game, 0, new int[] { 0 });
    }

    /**
     * Checks whether given game is valid, user should use the other isValid
     * method. There may only be one solution.
     */
    private boolean isValid(int[][] game, int index, int[] numberOfSolutions) {
        if (index > 80)
            return ++numberOfSolutions[0] == 1;

        int x = index % 9;
        int y = index / 9;

        if (game[y][x] == 0) {
            List<Integer> numbers = new ArrayList<Integer>();
            for (int i = 1; i <= 9; i++)
                numbers.add(i);

            while (numbers.size() > 0) {
                int number = getNextPossibleNumber(game, x, y, numbers);
                if (number == -1)
                    break;
                game[y][x] = number;

                if (!isValid(game, index + 1, numberOfSolutions)) {
                    game[y][x] = 0;
                    return false;
                }
                game[y][x] = 0;
            }
        } else if (!isValid(game, index + 1, numberOfSolutions))
            return false;

        return true;
    }

    /**
     * Copies a game.
     */
    private int[][] copy(int[][] game) {
        int[][] copy = new int[9][9];
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++)
                copy[y][x] = game[y][x];
        }
        return copy;
    }
}