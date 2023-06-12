package pppp.group14project.controller;

import pppp.group14project.model.Tile;

import java.util.List;

public interface Mediator {

  void moveTilesToWall(Tile tile);
  void moveTilesToFloor(List<Tile> tiles);
  void moveTilesToPattern(List<Tile> tiles);

  void moveTilesToTable(List<Tile> tiles);
}
