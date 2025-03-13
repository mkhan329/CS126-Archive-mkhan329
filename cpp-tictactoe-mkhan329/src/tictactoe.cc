#include "tictactoe/tictactoe.h"

#include <algorithm>
#include <cctype>
#include <stdexcept>

namespace tictactoe {

using std::string;

Board::Board(const string& board) {
  if (board.empty()) {
    throw std::invalid_argument("The string provided is not a valid board.");
  }

  nBoardDimension = sqrt(board.length());

  if (nBoardDimension != floor(nBoardDimension)) {
    throw std::invalid_argument("The string provided is not a valid board.");
  }

  TicTacToeBoard = board;
  std::transform(TicTacToeBoard.begin(), TicTacToeBoard.end(), TicTacToeBoard.begin(),
                          [](unsigned char c){ return std::tolower(c); }); // https://stackoverflow.com/questions/313970/how-to-convert-stdstring-to-lower-case/24063783
}

BoardState Board::EvaluateBoard() const {
  int xCount = std::count(TicTacToeBoard.begin(), TicTacToeBoard.end(), 'x');
  int oCount = std::count(TicTacToeBoard.begin(), TicTacToeBoard.end(), 'o');
  int xWins = 0;
  int oWins = 0;
  /*
   * First UnreachableState check, make sure number of X's and O's on board is valid
   */
  if (oCount > xCount || xCount > oCount + 1) {
    return BoardState::UnreachableState;
  }

  /*
   * Check the Rows and Columns for wins.
   * The for-loops check that each letter in the rows and columns are all the same.
   * i represents the rows/columns from 0 to n, and j iterates over each i'th row/column
   */
  int xColWins = 0;
  int xRowWins = 0;
  int oColWins = 0;
  int oRowWins = 0;
  for (int i = 0; i < nBoardDimension; i++) {
    bool rowSame = true;
    bool columnSame = true;
    for (int j = 0; j < nBoardDimension; j++) {
      if (!CheckEqualSquare((nBoardDimension*i) + j,nBoardDimension*i)) {
        rowSame = false;
      }
      if (!CheckEqualSquare(i + (nBoardDimension*j), i)) {
        columnSame = false;
      }
    }
    //Increment xWins or oWins if a row or column is won.
    xColWins += (columnSame & TicTacToeBoard.at(i) == 'x') ? 1 : 0;
    xRowWins += (rowSame & TicTacToeBoard.at(nBoardDimension*i) == 'x') ? 1 : 0;
    oColWins += (columnSame & TicTacToeBoard.at(i) == 'o') ? 1 : 0;
    oRowWins += (rowSame & TicTacToeBoard.at(nBoardDimension*i) == 'o') ? 1 : 0;
  }
  xWins = (xColWins<xRowWins)?xRowWins:xColWins;
  oWins = (oColWins<oRowWins)?oRowWins:oColWins;

  /*
   * Check the 2 Diagonals for wins, leftDiagonal starts in top left and rightDiagonal in top right
   * The for-loops check that each letter in the diagonals are all the same.
   */
  bool leftDiagonalSame = true;
  bool rightDiagonalSame = true;
  for (int i = 0; i < (nBoardDimension * nBoardDimension); i += nBoardDimension + 1) {
    if (!CheckEqualSquare(i, 0)) {
      leftDiagonalSame = false;
    }
  }
  for (int i = nBoardDimension - 1; i < (nBoardDimension * nBoardDimension) - (nBoardDimension - 1); i += nBoardDimension - 1) {
    if (!CheckEqualSquare(i, nBoardDimension - 1)) {
      rightDiagonalSame = false;
    }
  }
  //Increment X or O wins if a diagonal is won, but only when X or O wins is currently 0.
  xWins += (leftDiagonalSame & TicTacToeBoard.at(0) == 'x') || (rightDiagonalSame & TicTacToeBoard.at(nBoardDimension - 1) == 'x') ? (xWins < 1 ? 1 : 0) : 0;
  oWins += (leftDiagonalSame & TicTacToeBoard.at(0) == 'o') || (rightDiagonalSame & TicTacToeBoard.at(nBoardDimension - 1) == 'o') ? (oWins < 1 ? 1 : 0) : 0;

  /*
   * Second UnreachableState Check
   * Used to make sure only one person has won, and hasn't won more than once.
   */
  if (xWins + oWins > 1) {
    return BoardState::UnreachableState;
  }

  /*
   * Check who wins, unless tie
   */
  if (xWins == 1) {
    return BoardState::Xwins;
  } else if (oWins == 1) {
    return BoardState::Owins;
  }
  return BoardState::NoWinner;
}

bool Board::CheckEqualSquare(int i, int j) const {
  return (TicTacToeBoard.at(i) == TicTacToeBoard.at(j));
}

}  // namespace tictactoe
