package tetris

import tetris.types._

/**
  * @param orientedBlocks Vector of model blocks in 4 orientation states
  *                       relative to the center.
  * 
  */
case class Piece (orientedBlocks: Vector[Vector[Point]], 
                  center: Point,
                  color: Color,
                  orientation: Int) {
  def blocks: Vector[Point] = orientedBlocks(orientation).map(_ + center)
  def rotate: Piece = Piece(orientedBlocks, center, color, (orientation + 1) % 4)
  def move(right: Int, down: Int): Piece = 
    Piece(orientedBlocks, center + Point(right, down), color, orientation)
}

object Piece {
  def randomPiece(center: Point): Piece = {
    var pieces = Vector(squarePiece _, linePiece _, 
                        LleftPiece _, LrightPiece _, 
                        SPiece _, ZPiece _, TPiece _)
    pieces(scala.util.Random.nextInt(pieces.length))(center)
  } 
  
  def squarePiece(center: Point): Piece = {
    var stdBlocks = Vector(Point(0, 0), Point(1, 0), Point(0, 1), Point(1, 1))
    Piece(Vector(stdBlocks, stdBlocks, stdBlocks, stdBlocks),
          center, Color(255, 0, 0), 0)
  }

  def linePiece(center: Point): Piece = {
    var stdBlocks1 = Vector(Point(1, 1), Point(0, 1), Point(-1, 1), Point(-2, 1))
    var stdBlocks2 = Vector(Point(0, 2), Point(0, 1), Point(0, 0), Point(0, -1))
    var stdBlocks3 = Vector(Point(-1, 1), Point(0, 1), Point(1, 1), Point(2, 1))
    var stdBlocks4 = Vector(Point(0, 0), Point(0, 1), Point(0, 2), Point(0, 3))
    Piece(Vector(stdBlocks1, stdBlocks2, stdBlocks3, stdBlocks4),
          center, Color(0, 255, 0), 0)
  }
  
  def LrightPiece(center: Point): Piece = {
    var stdBlocks1 = Vector(Point(-2, 2), Point(-1, 2), Point(0, 2), Point(0, 1))
    var stdBlocks2 = Vector(Point(0, 0), Point(0, 1), Point(0, 2), Point(1, 2))
    var stdBlocks3 = Vector(Point(2, 2), Point(1, 2), Point(0, 2), Point(0, 3))
    var stdBlocks4 = Vector(Point(0, 4), Point(0, 3), Point(0, 2), Point(-1, 2))
    Piece(Vector(stdBlocks1, stdBlocks2, stdBlocks3, stdBlocks4),
          center, Color(0, 0, 255), 0)
  }
  
  def LleftPiece(center: Point): Piece = {
    var stdBlocks1 = Vector(Point(2, 2), Point(1, 2), Point(0, 2), Point(0, 1))
    var stdBlocks2 = Vector(Point(0, 4), Point(0, 3), Point(0, 2), Point(1, 2))
    var stdBlocks3 = Vector(Point(-2, 2), Point(-1, 2), Point(0, 2), Point(0, 3))
    var stdBlocks4 = Vector(Point(0, 0), Point(0, 1), Point(0, 2), Point(-1, 2))
    Piece(Vector(stdBlocks1, stdBlocks2, stdBlocks3, stdBlocks4),
          center, Color(0, 255, 255), 0)
  }
  
  def SPiece(center: Point): Piece = {
    var stdBlocks1 = Vector(Point(0, 0), Point(1, 0), Point(0, 1), Point(-1, 1))
    var stdBlocks2 = Vector(Point(1, 1), Point(1, 2), Point(0, 1), Point(0, 0))
    var stdBlocks3 = Vector(Point(0, 2), Point(-1, 2), Point(0, 1), Point(1, 1))
    var stdBlocks4 = Vector(Point(-1, 1), Point(-1, 0), Point(0, 1), Point(0, 2))
    Piece(Vector(stdBlocks1, stdBlocks2, stdBlocks3, stdBlocks4),
          center, Color(255, 255, 0), 0)
  }
  
  def ZPiece(center: Point): Piece = {
    var stdBlocks1 = Vector(Point(0, 0), Point(1, 0), Point(1, 1), Point(2, 1))
    var stdBlocks2 = Vector(Point(2, 0), Point(2, 1), Point(1, 1), Point(1, 2))
    var stdBlocks3 = Vector(Point(2, 2), Point(1, 2), Point(1, 1), Point(0, 1))
    var stdBlocks4 = Vector(Point(0, 2), Point(0, 1), Point(1, 1), Point(1, 0))
    Piece(Vector(stdBlocks1, stdBlocks2, stdBlocks3, stdBlocks4),
          center, Color(255, 0, 255), 0)
  }
  
  def TPiece(center: Point): Piece = {
    var stdBlocks1 = Vector(Point(0, 0), Point(1, 0), Point(2, 0), Point(1, -1))
    var stdBlocks2 = Vector(Point(1, -1), Point(1, 0), Point(1, 1), Point(2, 0))
    var stdBlocks3 = Vector(Point(0, 0), Point(1, 0), Point(2, 0), Point(1, 1))
    var stdBlocks4 = Vector(Point(1, -1), Point(1, 0), Point(1, 1), Point(0, 0))
    Piece(Vector(stdBlocks1, stdBlocks2, stdBlocks3, stdBlocks4),
          center, Color(0, 100, 100), 0)
  }
}


