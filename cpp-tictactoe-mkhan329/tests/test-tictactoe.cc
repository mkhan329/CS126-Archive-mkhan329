#include <string>

#include <catch2/catch.hpp>
#include <tictactoe/tictactoe.h>

using tictactoe::Board;
using tictactoe::BoardState;

TEST_CASE("Invalid string provided to constructor") {
  SECTION("String is not square") {
    REQUIRE_THROWS_AS(Board("xxooo"), std::invalid_argument);
  }
}

TEST_CASE("Boards with no winner") {
  SECTION("Full board with no winner") {
    REQUIRE(Board("xxoooxxxo").EvaluateBoard() == BoardState::NoWinner);
  }
  SECTION("Not full board with no winner") {
    REQUIRE(Board("O!%2X.Xc?").EvaluateBoard() == BoardState::NoWinner);
  }
}

TEST_CASE("Boards with unreachable state") {
  SECTION("More O's than X's") {
    REQUIRE(Board("OO!OX_X..").EvaluateBoard() == BoardState::UnreachableState);
  }
  SECTION("2 More X's than O's") {
    REQUIRE(Board("O!%XX.Xc?").EvaluateBoard() == BoardState::UnreachableState);
  }
  SECTION("Only X") {
    REQUIRE(Board("X!%XX.Xc?").EvaluateBoard() == BoardState::UnreachableState);
  }
  SECTION("Only O") {
    REQUIRE(Board("O!%OO.Oc?").EvaluateBoard() == BoardState::UnreachableState);
  }
  SECTION("2 Winners") {
    REQUIRE(Board("OOO-X-XXX").EvaluateBoard() == BoardState::UnreachableState);
  }
  SECTION("2 X Wins") {
    REQUIRE(Board("XXXoooXXX").EvaluateBoard() == BoardState::UnreachableState);
  }
  SECTION("2 O Wins") {
    REQUIRE(Board("OOOXXXOOO").EvaluateBoard() == BoardState::UnreachableState);
  }
}

TEST_CASE("Boards with X wins") {
  SECTION("X wins top row") {
    REQUIRE(Board("XXXXO..OO").EvaluateBoard() == BoardState::Xwins);
  }
  SECTION("X wins middle row") {
    REQUIRE(Board("X-OXXX-OO").EvaluateBoard() == BoardState::Xwins);
  }
  SECTION("X wins bottom row") {
    REQUIRE(Board("-OO-XOXXX").EvaluateBoard() == BoardState::Xwins);
  }
  SECTION("X wins left column") {
    REQUIRE(Board("X-OXOOX-X").EvaluateBoard() == BoardState::Xwins);
  }
  SECTION("X wins middle column") {
    REQUIRE(Board("-X--XO-XO").EvaluateBoard() == BoardState::Xwins);
  }
  SECTION("X wins right column") {
    REQUIRE(Board("-OXOXXO-X").EvaluateBoard() == BoardState::Xwins);
  }
  SECTION("X wins left diagonal") {
    REQUIRE(Board("XX-OXOO-X").EvaluateBoard() == BoardState::Xwins);
  }
  SECTION("X wins right diagonal") {
    REQUIRE(Board("O-XOX-X--").EvaluateBoard() == BoardState::Xwins);
  }
  SECTION("X wins both diagonals") {
    REQUIRE(Board("XOXOXOXOX").EvaluateBoard() == BoardState::Xwins);
  }
  SECTION("X wins row and diagonal") {
    REQUIRE(Board("XOOOXOXXX").EvaluateBoard() == BoardState::Xwins);
  }
  SECTION("X wins column and diagonal") {
    REQUIRE(Board("XOXXXOXOO").EvaluateBoard() == BoardState::Xwins);
  }
  SECTION("X wins row and column") {
    REQUIRE(Board("XXXXOOXOO").EvaluateBoard() == BoardState::Xwins);
  }
  SECTION("X wins bottom row and first column") {
    REQUIRE(Board("XOOXOOXXX").EvaluateBoard() == BoardState::Xwins);
  }
}

TEST_CASE("Boards with O wins") {
  SECTION("O wins top row") {
    REQUIRE(Board("ooooxx.xx").EvaluateBoard() == BoardState::Owins);
  }
  SECTION("O wins middle row") {
    REQUIRE(Board("OXXOOOXX-").EvaluateBoard() == BoardState::Owins);
  }
  SECTION("O wins bottom row") {
    REQUIRE(Board("X--XX-OOO").EvaluateBoard() == BoardState::Owins);
  }
  SECTION("O wins left column") {
    REQUIRE(Board("OX-OX-O-X").EvaluateBoard() == BoardState::Owins);
  }
  SECTION("O wins middle column") {
    REQUIRE(Board("XOX-O-XO-").EvaluateBoard() == BoardState::Owins);
  }
  SECTION("O wins right column") {
    REQUIRE(Board("-XO-XOX-O").EvaluateBoard() == BoardState::Owins);
  }
  SECTION("O wins left diagonal") {
    REQUIRE(Board("OXX-OX--O").EvaluateBoard() == BoardState::Owins);
  }
  SECTION("O wins right diagonal") {
    REQUIRE(Board("XXOXO-O--").EvaluateBoard() == BoardState::Owins);
  }
}

