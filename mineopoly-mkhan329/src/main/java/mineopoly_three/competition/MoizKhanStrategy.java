package mineopoly_three.competition;

import mineopoly_three.action.TurnAction;
import mineopoly_three.game.Economy;
import mineopoly_three.item.InventoryItem;
import mineopoly_three.strategy.MinePlayerStrategy;
import mineopoly_three.strategy.PlayerBoardView;
import mineopoly_three.tiles.TileType;
import mineopoly_three.util.DistanceUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MoizKhanStrategy implements MinePlayerStrategy {

    private int maxInventorySize;
    private int maxCharge;
    private int winningScore;
    private PlayerBoardView board;
    private Point tileLocation;
    private int boardSize;

    private List<InventoryItem> itemList;
    private List<Point> chargeTiles;
    private List<Point> resourceTiles;
    private List<Point> marketTiles;

    private int pointsScored;

    public MoizKhanStrategy() {}

    /**
     * Called at the start of every round
     *
     * @param boardSize         The length and width of the square game board
     * @param maxInventorySize  The maximum number of items that your player can carry at one time
     * @param maxCharge         The amount of charge your robot starts with (number of tile moves before needing to recharge)
     * @param winningScore      The first player to reach this score wins the round
     * @param startingBoard     A view of the GameBoard at the start of the game. You can use this to pre-compute fixed
     *                          information, like the locations of market or recharge tiles
     * @param startTileLocation A Point representing your starting location in (x, y) coordinates
     *                          (0, 0) is the bottom left and (boardSize - 1, boardSize - 1) is the top right
     * @param isRedPlayer       True if this strategy is the red player, false otherwise
     * @param random            A random number generator, if your strategy needs random numbers you should use this.
     */
    @Override
    public void initialize(int boardSize, int maxInventorySize, int maxCharge, int winningScore, PlayerBoardView startingBoard, Point startTileLocation, boolean isRedPlayer, Random random) {
        this.maxInventorySize = maxInventorySize;
        this.maxCharge = maxCharge;
        this.winningScore = winningScore;
        board = startingBoard;
        tileLocation = startTileLocation;
        pointsScored = 0;
        this.boardSize = boardSize;

        itemList = new ArrayList<>();
        resourceTiles = new ArrayList<>();
        chargeTiles = new ArrayList<>();
        marketTiles = new ArrayList<>();
        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                switch(board.getTileTypeAtLocation(x, y)) {
                    case RED_MARKET:
                        if(isRedPlayer) {
                            marketTiles.add(new Point(x, y));
                        }
                        break;
                    case BLUE_MARKET:
                        if(!isRedPlayer) {
                            marketTiles.add(new Point(x, y));
                        }
                        break;
                    case RECHARGE :
                        chargeTiles.add(new Point(x, y));
                        break;
                    case RESOURCE_DIAMOND:
                    case RESOURCE_EMERALD:
                    case RESOURCE_RUBY:
                        resourceTiles.add(new Point(x, y));
                        break;
                    default:
                        break;
                }
            }

        }
    }

    /**
     * The main part of your strategy, this method returns what action your player should do on this turn
     *
     * @param boardView     A PlayerBoardView object representing all the information about the board and the other player
     *                      that your strategy is allowed to access
     * @param economy       The GameEngine's economy object which holds current prices for resources
     * @param currentCharge The amount of charge your robot has (number of tile moves before needing to recharge)
     * @param isRedTurn     For use when two players attempt to move to the same spot on the same turn
     *                      If true: The red player will move to the spot, and the blue player will do nothing
     *                      If false: The blue player will move to the spot, and the red player will do nothing
     * @return The TurnAction enum for the action that this strategy wants to perform on this game turn
     */
    @Override
    public TurnAction getTurnAction(PlayerBoardView boardView, Economy economy, int currentCharge, boolean isRedTurn) {
        board = boardView;
        Map<Point, List<InventoryItem>> map = board.getItemsOnGround();
        tileLocation = board.getYourLocation();

        resourceTiles = new ArrayList<>();
        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                switch(board.getTileTypeAtLocation(x, y)) {
                    case RESOURCE_DIAMOND:
                    case RESOURCE_EMERALD:
                    case RESOURCE_RUBY:
                        resourceTiles.add(new Point(x, y));
                        break;
                    default:
                        break;
                }
            }
        }

        if (currentCharge < maxCharge && board.getTileTypeAtLocation(tileLocation) == TileType.RECHARGE) {
                return null;
        }

        if (map.get(tileLocation) != null && itemList.size() != maxInventorySize) {
            for (InventoryItem item : map.get(tileLocation)) {
                switch (item.getItemType()) {
                    case DIAMOND:
                    case EMERALD:
                    case RUBY:
                        return TurnAction.PICK_UP_RESOURCE;
                    default :
                        break;
                }
            }
        }

        if (currentCharge < DistanceUtil.getManhattanDistance(getClosestPoint(chargeTiles), tileLocation) + 2) {
            return moveToLocation(getClosestPoint(chargeTiles));
        }

        if (itemList.size() == maxInventorySize) {
            return moveToLocation(getClosestPoint(marketTiles));
        }

        switch(board.getTileTypeAtLocation(tileLocation)) {
            case RESOURCE_DIAMOND:
            case RESOURCE_RUBY:
            case RESOURCE_EMERALD:
                return TurnAction.MINE;
            default :
                return moveToLocation(getClosestPoint(resourceTiles));
        }

    }

    /**
     * Move the player robot towards the destination point.
     *
     * @param location The point destination.
     * @return The Turn Action needed to move the player robot to the destination.
     */
    public TurnAction moveToLocation(Point location) {
        if (location == null) {
            return null;
        }
        if (location.y > tileLocation.y) {
            return TurnAction.MOVE_UP;
        }
        if (location.y < tileLocation.y) {
            return TurnAction.MOVE_DOWN;
        }
        if (location.x > tileLocation.x) {
            return TurnAction.MOVE_RIGHT;
        }
        if (location.x < tileLocation.x) {
            return TurnAction.MOVE_LEFT;
        }
        return null;
    }

    /**
     * Given an array of points, find the closest point to the player.
     *
     * @param points The list of points to check from
     * @return The closest point
     */
    public Point getClosestPoint(List<Point> points) {
        if (points == null || points.size() == 0) {
            return null;
        }
        int minDistance = 1000000000;
        Point closestPoint = null;
        for(Point p : points) {
            int dist = DistanceUtil.getManhattanDistance(p, tileLocation);//distanceToLocation(p);
            if(dist < minDistance) {
                closestPoint = p;
                minDistance = dist;
            }
        }
        return closestPoint;
    }

    /**
     * Called when the player receives an item from performing a TurnAction that gives an item.
     * At the moment this is only from using PICK_UP on top of a mined resource
     *
     * @param itemReceived The item received from the player's TurnAction on their last turn
     */
    @Override
    public void onReceiveItem(InventoryItem itemReceived) {
        resourceTiles.remove(tileLocation);
        itemList.add(itemReceived);
    }

    /**
     * Called when the player steps on a market tile with items to sell. Tells your strategy how much all
     * of the items sold for.
     *
     * @param totalSellPrice The combined sell price for all items in your strategy's inventory
     */
    @Override
    public void onSoldInventory(int totalSellPrice) {
        itemList.removeAll(itemList);
    }

    /**
     * Gets the name of this strategy. The amount of characters that can actually be displayed on a screen varies,
     * although by default at screen size 750 it's about 16-20 characters depending on character size
     *
     * @return The name of your strategy for use in the competition and rendering the scoreboard on the GUI
     */
    @Override
    public String getName() {
        return "MoizKhanStrategy";
    }

    /**
     * Called at the end of every round to let players reset, and tell them how they did if the strategy does not
     * track that for itself
     *
     * @param pointsScored         The total number of points this strategy scored
     * @param opponentPointsScored The total number of points the opponent's strategy scored
     */
    @Override
    public void endRound(int pointsScored, int opponentPointsScored) {
        this.pointsScored = pointsScored;
    }

    public boolean gameWon() {
        return pointsScored > winningScore;
    }

}
