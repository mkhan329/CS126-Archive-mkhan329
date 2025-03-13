package com.example;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TicTacToeBoardTest {
  @Test
  public void testValidBoardNoWinner() {
    TicTacToeBoard board = new TicTacToeBoard("O...X.X.."); //not enough moves made
    assertEquals(Evaluation.NoWinner, board.evaluate());

    board = new TicTacToeBoard("OOXXXOOXX"); //board filled up with no winner
    assertEquals(Evaluation.NoWinner, board.evaluate());
  }
  @Test
  public void testValidBoardUnreachableState() {
    TicTacToeBoard board = new TicTacToeBoard("OO.OX.X.."); // more O than x
    assertEquals(Evaluation.UnreachableState, board.evaluate());

    board = new TicTacToeBoard(".X.-XX-O-"); //2 more x than o.
    assertEquals(Evaluation.UnreachableState, board.evaluate());

  }
  @Test
  public void testValidBoardXWins() {
    TicTacToeBoard board = new TicTacToeBoard("XXXXO..OO"); //x wins row
    assertEquals(Evaluation.Xwins, board.evaluate());

    board = new TicTacToeBoard("-XO-XXOXO"); //x wins column
    assertEquals(Evaluation.Xwins, board.evaluate());

    board = new TicTacToeBoard("X--OXXOOX"); //x wins diagonal
    assertEquals(Evaluation.Xwins, board.evaluate());

  }
  @Test
  public void testValidBoardOWins() {
    TicTacToeBoard board = new TicTacToeBoard("ooooxx.xx"); //o wins row
    assertEquals(Evaluation.Owins, board.evaluate());

    board = new TicTacToeBoard("O--OXXO-X"); //o wins column
    assertEquals(Evaluation.Owins, board.evaluate());

    board = new TicTacToeBoard("OXXOO-XXO"); //o wins diagonal
    assertEquals(Evaluation.Owins, board.evaluate());
  }
}
