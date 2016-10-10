package tetris

import tetris.types._
/**
  * @param grid grid(y) is the y-th row.
  */
case class GameState (grid: Vector[Vector[Color]],
                      activePiece: Piece,
                      lines: Int,
                      gameEnded: Boolean,
                      nextPiece: Piece) {
  def width = grid(0).length
  def height = grid.length

  def insideGrid(point: Point): Boolean = {
    val x = point.x
    val y = point.y
    0 <= y && y < height && 0 <= x && x < width
  }

  def insideGrid(piece: Piece): Boolean = piece.blocks.forall(pt => insideGrid(pt))

  def intersectsGrid(piece: Piece): Boolean =
    piece.blocks.exists {case Point(x,y) =>
      insideGrid(Point(x,y)) && grid(y)(x) != Color(0, 0, 0)
    }


  def updatedGrid(points: Vector[Point], color: Color): Vector[Vector[Color]] =
    points.foldRight(grid) {
      case (Point(x, y), g) => {
        if (insideGrid(Point(x,y))) {
          val row = g(y) updated (x, color)
          g updated (y, row)
        } else {
          g
        }
      }
    }

  def randomActivePiece(): Piece = {
    Piece.randomPiece(Point(width/2 - 1, 0))
  }

  def eliminateLines(): GameState = {
    grid.zipWithIndex.find{ case (row, i) => row.forall(_ != Color(0, 0, 0))} match {
      case Some((row, i)) => {
        // delete row i
        val rowDeleted =
          grid.slice(0, i) ++ grid.slice(i + 1, grid.length + 1)
        // add an empty row
        val newGrid = Vector.fill[Color](width)(Color(0,0,0)) +: rowDeleted
        GameState(newGrid, activePiece, lines + 1, gameEnded, nextPiece)
          .eliminateLines()
      }
      case None => this
    }
  }

  def endGame: GameState = GameState(grid, activePiece, lines, true, nextPiece)

  def updatedActivePiece(piece: Piece): GameState =
    GameState(grid, piece, lines, gameEnded, nextPiece)

  def pieceHitsFloor(piece: Piece): Boolean =
    piece.blocks.exists {
      case Point(x, y) => {
        y >= height || (insideGrid(Point(x, y)) && grid(y)(x) != Color(0,0,0))
      }
    }

  def rotate(): GameState = {
    val rotatedPiece = activePiece.rotate
    if (insideGrid(rotatedPiece) && ! intersectsGrid(rotatedPiece))
      updatedActivePiece(rotatedPiece) else this
  }

  def moveLeft(): GameState = {
    val movedPiece = activePiece.move(-1, 0)
    if (insideGrid(movedPiece) && ! intersectsGrid(movedPiece))
      updatedActivePiece(movedPiece) else this
  }

  def moveRight(): GameState = {
    val movedPiece = activePiece.move(1, 0)
    if (insideGrid(movedPiece) && ! intersectsGrid(movedPiece))
      updatedActivePiece(movedPiece) else this
  }


  def moveDown(): GameState = {
    if (gameEnded) {
      this
    } else {
      val shiftedPiece = activePiece.move(0,1)
      if (intersectsGrid(shiftedPiece) || pieceHitsFloor(shiftedPiece)) {
        // Add original piece to the grid.
        val nextGrid = updatedGrid(activePiece.blocks, activePiece.color)
        // Add a new active piece, and eliminate all made lines.
        val gs = GameState(nextGrid, nextPiece, lines, gameEnded, randomActivePiece())
                 .eliminateLines()
        // Check to see if the new active piece collides with the grid
        if (gs.intersectsGrid(randomActivePiece())) gs.endGame else gs
      } else {
        updatedActivePiece(shiftedPiece)
      }
    }
  }
                        
}

object GameState {
  def initGameState(width: Int, height: Int): GameState = {
    val activePiece = Piece.randomPiece(Point(width/2 - 1, 0))
    val grid = Vector.fill[Vector[Color]](height) (Vector.fill[Color](width)(Color(0,0,0)))
    val nextPiece = Piece.randomPiece(Point(width/2 - 1, 0))
    GameState(grid, activePiece, 0, false, nextPiece)
  }
}
