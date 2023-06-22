package pppp.group14project.controller;

import pppp.group14project.controller.exceptions.InvalidPositionException;
import pppp.group14project.model.Tile;
import pppp.group14project.model.exceptions.FullException;
import pppp.group14project.model.exceptions.WrongTileException;

import java.util.List;

public interface Mediator {

  void moveTileToWall(Tile tile, int rowIndex) throws WrongTileException, FullException;
  void moveTilesToFloor(List<Tile> tiles);

  void moveTilesToTable(List<Tile> tiles);

  void moveTileToTileContainer(Tile tile);

  void moveTilesToTileContainer(List<Tile> tile);

  void updateScore();

  List<Tile> removeTilesFromFloor();


}
