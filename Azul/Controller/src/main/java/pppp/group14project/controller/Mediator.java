package pppp.group14project.controller;

import pppp.group14project.controller.exceptions.InvalidPositionException;
import pppp.group14project.model.Tile;

import java.util.List;

public interface Mediator {

  List<Tile> moveTilesToWall();
  void moveTilesToFloor(List<Tile> tiles);
  void moveTilesToPattern(List<Tile> tiles) throws InvalidPositionException;

  void moveTilesToTable(List<Tile> tiles);

  void removeTilesFromTable();

}
