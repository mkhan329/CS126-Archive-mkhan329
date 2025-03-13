package mineopoly_three;

import mineopoly_three.action.TurnAction;
import mineopoly_three.game.GameEngine;
import mineopoly_three.strategy.MinePlayerStrategy;
import mineopoly_three.strategy.MoizKhanStrategy;
import mineopoly_three.strategy.RandomStrategy;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MineopolyTest {

    MoizKhanStrategy strategy;
    @Before
    public void setUp() {
        // This is run before every test
        strategy = new MoizKhanStrategy();
    }

    @Test
    public void testClosestPointNull() {
        strategy.initialize(0, 0, 0, 0, null, new Point(0, 0), false, null);
        assertEquals(null, strategy.getClosestPoint(null));
    }

    @Test
    public void testMoveToNull() {
        strategy.initialize(0, 0, 0, 0, null, new Point(0, 0), false, null);
        assertEquals(null, strategy.moveToLocation(null));
    }

    @Test
    public void testClosestPointEmpty() {
        strategy.initialize(0, 0, 0, 0, null, new Point(0, 0), false, null);
        assertEquals(null, strategy.getClosestPoint(new ArrayList<Point>()));
    }

    @Test
    public void testClosestPoint() {
        strategy.initialize(0, 0, 0, 0, null, new Point(100, -20), false, null);
        List<Point> points = new ArrayList<>();
        points.add(new Point(100, 50000));
        assertEquals(new Point(100, 50000), strategy.getClosestPoint(points));
    }

    @Test
    public void testClosestPointMany() {
        strategy.initialize(0, 0, 0, 0, null, new Point(6, 6), false, null);
        List<Point> points = new ArrayList<>();
        points.add(new Point(5, 5));
        points.add(new Point(10, 10));
        points.add(new Point(7, 9));
        points.add(new Point(-20, -10));
        points.add(new Point(100, 0));
        points.add(new Point(0, -120));
        assertEquals(new Point(5, 5), strategy.getClosestPoint(points));
    }

    @Test
    public void testMoveToCurrentLocation() {
        strategy.initialize(0, 0, 0, 0, null, new Point(5, 5), false, null);
        assertEquals(null, strategy.moveToLocation(new Point(5, 5)));
    }

    @Test
    public void testMoveUp() {
        strategy.initialize(0, 0, 0, 0, null, new Point(5, 5), false, null);
        assertEquals(TurnAction.MOVE_UP, strategy.moveToLocation(new Point(10, 10)));
    }

    @Test
    public void testMoveDown() {
        strategy.initialize(0, 0, 0, 0, null, new Point(5, 5), false, null);
        assertEquals(TurnAction.MOVE_DOWN, strategy.moveToLocation(new Point(10, -10)));
    }

    @Test
    public void testMoveLeft() {
        strategy.initialize(0, 0, 0, 0, null, new Point(5, 5), false, null);
        assertEquals(TurnAction.MOVE_LEFT, strategy.moveToLocation(new Point(-10, 5)));
    }

    @Test
    public void testMoveRight() {
        strategy.initialize(0, 0, 0, 0, null, new Point(5, 5), false, null);
        assertEquals(TurnAction.MOVE_RIGHT, strategy.moveToLocation(new Point(10, 5)));
    }

}
