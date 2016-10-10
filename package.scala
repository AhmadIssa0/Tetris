package tetris

package object types {
  case class Color(r: Int, g: Int, b: Int)
  case class Point(x: Int, y: Int) {
    def +(p: Point): Point = Point(x + p.x, y + p.y)
  }
}
