package pppp.group14project.controller;

import pppp.group14project.controller.exceptions.InvalidPositionException;
import pppp.group14project.model.Tile;
import pppp.group14project.model.exceptions.FullException;
import pppp.group14project.model.exceptions.WrongTileException;

import java.util.List;

public interface Mediator {

  void moveTileToWall(Tile tile, int rowIndex) throws WrongTileException, FullException;
  void moveTilesToFloor(List<Tile> tiles);
  void moveTilesToPattern(List<Tile> tiles) throws InvalidPositionException;

  void moveTilesToTable(List<Tile> tiles);

  void moveTilesToTileContainer(Tile tile);

  void updateScore();

  List<Tile> removeTilesFromFloor();


}
