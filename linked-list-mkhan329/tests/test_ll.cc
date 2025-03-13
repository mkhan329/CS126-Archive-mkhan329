// Copyright (c) 2020 [Your Name]. All rights reserved.

#define CATCH_CONFIG_MAIN

#include <catch2/catch.hpp>
#include <cs126linkedlist/ll.h>


using cs126linkedlist::LinkedList;

// Read more on SECTIONs here:
// `https://github.com/catchorg/Catch2/tree/master/docs`
// in the "Test Cases and Sections" file.

TEST_CASE("Initialization") {
  LinkedList<int> list;
  REQUIRE(list.size() == 0);
  REQUIRE(list.empty());
}

TEST_CASE("Push Back") {
  LinkedList<int> list;

  SECTION("Push back one element") {
    list.push_back(1);
    REQUIRE(list.size() == 1);
  }

  SECTION("Push back two elements") {
    list.push_back(-1);
    list.push_back(10000);
    REQUIRE(list.size() == 2);
  }
}

TEST_CASE("Push Front") {
  LinkedList<int> list;

  SECTION("Push front one element") {
    list.push_front(1);
    REQUIRE(list.size() == 1);
  }

  SECTION("Push front four elements") {
    list.push_front(-1);
    list.push_front(10000);
    list.push_front(-123);
    list.push_front(-1);
    REQUIRE(list.size() == 4);
  }
}

TEST_CASE("Copy") {
  LinkedList<int> list2;
  std::vector<int> sample = {4, 5, 6};
  const LinkedList<int> list1 = LinkedList(sample);

  SECTION("Copy method") {
    LinkedList<int> list2 = LinkedList(list1);
    REQUIRE(list2.size() == 3);
    REQUIRE(list1.size() == 3);
  }

  SECTION("Copy operator") {
    LinkedList<int> list2 = list1;
    REQUIRE(list2.size() == 3);
    REQUIRE(list1.size() == 3);

  }
}

TEST_CASE("Move") {
  LinkedList<int> list2;
  LinkedList<int> list1;

  SECTION("Move method") {
    list1.push_front(10000);
    list1.push_front(-123);
    list1.push_front(-1);
    LinkedList<int> list2 = LinkedList(list1);
    REQUIRE(list2.size() == 3);
    REQUIRE(list1.empty());
  }

  SECTION("Move operator") {
    list1.push_front(10000);
    list1.push_front(-123);
    list1.push_front(-1);
    LinkedList<int> list2 = list1;
    REQUIRE(list2.size() == 3);
    REQUIRE(list1.empty());
  }
}

TEST_CASE("Get Front and Back") {
  LinkedList<int> list;
  list.push_front(-1);
  list.push_front(10000);
  list.push_front(-123);
  SECTION("Get Front") {
    REQUIRE(*list.begin() == -1);
  }
  SECTION("Get Back") {
    REQUIRE(*list.end() == -123);
  }
}

TEST_CASE("Pop Front") {
  LinkedList<int> list;
  list.push_front(-1);
  list.push_front(100);
  list.push_front(-123);
  list.pop_front();
  REQUIRE(list.size() == 2);
  REQUIRE(*list.begin() == 100);
}

TEST_CASE("Pop Back") {
  LinkedList<int> list;
  list.push_front(-1);
  list.push_front(100);
  list.push_front(-123);
  list.pop_back();
  REQUIRE(list.size() == 2);
  REQUIRE(*list.end() == 100);
}

TEST_CASE("Clear") {
  LinkedList<int> list;
  list.push_front(-10000);
  list.push_front(10);
  list.push_front(-14423);
  list.clear();
  REQUIRE(list.empty());
}

TEST_CASE("Stream") {
  LinkedList<int> list;
  list.push_front(1);
  list.push_front(2);
  list.push_front(3);
  std::stringstream streamer;
  streamer << list;
  REQUIRE(streamer.str() == "1, 2, 3");
}

TEST_CASE("Remove") {
  LinkedList<int> list;
  list.push_front(1);
  list.push_front(2);
  list.push_front(3);
  list.push_front(333);
  list.push_front(34);
  SECTION("Invalid removal") {
    list.RemoveNth(40);
    REQUIRE(list.size() == 5);
  }
  SECTION("Remove random middle") {
    list.RemoveNth(4);
    REQUIRE(list.size() == 4);
  }
}

TEST_CASE("Equality") {
  SECTION("Equal lists") {
    LinkedList<int> list1 = LinkedList(std::vector<int>{4, 5, 6});
    LinkedList<int> list2 = LinkedList(std::vector<int>{4, 5, 6});
    REQUIRE(list1 == list2);
  }
  SECTION("Unequal lists") {
    LinkedList<int> list1 = LinkedList(std::vector<int>{4, 55, 6});
    LinkedList<int> list2 = LinkedList(std::vector<int>{4, 5, 6});
    REQUIRE(list1 != list2);
  }

  SECTION("Unequal lists, different sizes") {
    LinkedList<int> list1 = LinkedList(std::vector<int>{4, 5, 6, 7});
    LinkedList<int> list2 = LinkedList(std::vector<int>{4, 5, 6});
    REQUIRE(list1 != list2);
  }
}

// TODO(you): Add more tests below.
