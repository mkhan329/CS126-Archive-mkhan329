package com.example;

/**
 * Takes in and evaluates a string representing a tic tac toe board.
 */
public class TicTacToeBoard {

  private String TicTacToeBoard;
  private int nBoardDimension; // for n x n board, nBoardDimension describes the board length n

  /**
   * This method should load a string into your TicTacToeBoard class.
   * @param TicTacToeBoard The string representing the board
   */
  public TicTacToeBoard(String TicTacToeBoard) {
    if (TicTacToeBoard == null) {
      throw new IllegalArgumentException();
    } else if (TicTacToeBoard.length() == 0) {
      throw new IllegalArgumentException();
    }

    //Get the Board Dimensions, and check if square
    double nBoardDimension = Math.sqrt(TicTacToeBoard.length());
    if (nBoardDimension != Math.floor(nBoardDimension)) {
      throw new IllegalArgumentException();
    }

    this.nBoardDimension = (int) nBoardDimension;
    this.TicTacToeBoard = TicTacToeBoard.toLowerCase();
  }

  /**
   * Checks the state of the board (unreachable, no winner, X wins, or O wins)
   * @return an enum value corresponding to the board evaluation
   */
  public Evaluation evaluate() {
    int xCount = (int) TicTacToeBoard.chars().filter(ch -> ch == 'x').count(); //Count how many X's and O's in board
    int oCount = (int) TicTacToeBoard.chars().filter(ch -> ch == 'o').count();
    int xWins = 0; //Keep track of how many times X and O have won
    int oWins = 0;

    /*
     * First UnreachableState check, make sure number of X's and O's on board is valid
     */
    if (oCount > xCount || xCount > oCount + 1) {
      return Evaluation.UnreachableState;
    }

    //Temporary variables to make code more readable
    String t = TicTacToeBoard;
    int n = nBoardDimension;

    /*
     * Check the Rows and Columns for wins.
     * The for-loops check that each letter in the rows and columns are all the same.
     * i represents the rows/columns from 0 to n, and j iterates over each i'th row/column
     */
    for (int i = 0; i < n; i++) {
      //int sumRow = 0;
      //int sumCol = 0;
      boolean rowSame = true;
      boolean columnSame = true;
      for (int j = 0; j < n; j++) {
        if (t.charAt((n*i) + j) != t.charAt(n*i)) {
          rowSame = false;
        }
        if (t.charAt(i + (n*j)) != t.charAt(i)) {
          columnSame = false;
        }
      }
      //Increment xWins or oWins if a row or column is won.
      xWins += (rowSame & t.charAt(n*i) == 'x') || (columnSame & t.charAt(i) == 'x') ? 1 : 0;
      oWins += (rowSame & t.charAt(n*i) == 'o') || (columnSame & t.charAt(i) == 'o') ? 1 : 0;
    }

    /*
     * Check the 2 Diagonals for wins, leftDiagonal starts in top left and rightDiagonal in top right
     * The for-loops check that each letter in the diagonals are all the same.
     */
    boolean leftDiagonalSame = true;
    boolean rightDiagonalSame = true;
    for (int i = 0; i < (n * n); i += n + 1) {
      if (t.charAt(i) != t.charAt(0)) {
        leftDiagonalSame = false;
      }
    }
    for (int i = n - 1; i < (n * n) - (n - 1); i += n - 1) {
      if (t.charAt(i) != t.charAt(n - 1)) {
        rightDiagonalSame = false;
      }
    }
    //Increment X or O wins if a diagonal is won, but only when X or O wins is currently 0.
    xWins += (leftDiagonalSame & t.charAt(0) == 'x') || (rightDiagonalSame & t.charAt(n - 1) == 'x') ? (xWins < 1 ? 1 : 0) : 0;
    oWins += (leftDiagonalSame & t.charAt(0) == 'o') || (rightDiagonalSame & t.charAt(n - 1) == 'o') ? (oWins < 1 ? 1 : 0) : 0;

    /*
     * Second UnreachableState Check
     * Used to make sure only one person has won, and hasn't won more than once.
     */
    if (xWins + oWins > 1) {
      return Evaluation.UnreachableState;
    }

    /*
     * Check who wins, unless tie
     */
    if (xWins == 1) {
      return Evaluation.Xwins;
    } else if (oWins == 1) {
      return Evaluation.Owins;
    } else {
      return Evaluation.NoWinner;
    }
  }
}
