
package tetris

import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom
import org.scalajs.dom.html
import scala.util.Random
import scala.scalajs.js
import tetris.types._

@JSExport
object Tetris {

  @JSExport
  //def main(canvas: html.Canvas): Unit = {
  def main(canvas: dom.raw.HTMLCanvasElement): Unit = {
    val ctx = canvas.getContext("2d")
                    .asInstanceOf[dom.CanvasRenderingContext2D]
    canvas.focus()
    val keys = scala.collection.mutable.Set.empty[Int]

    println("Started Tetris")
    ctx.fillStyle = "rgb(255, 255, 255)"
    ctx.fillRect(0, 0, 700, 500)
    var gameState = GameState.initGameState(10, 20)
    //(0 to 20).foreach{_ => gameState = gameState.moveDown()}
    var frame = 0
    def run = {
      if (keys.contains(38)) { // up arrow
        gameState = gameState.rotate()
        keys.remove(38)
      } else if (keys.contains(37)) { // left arrow
        gameState = gameState.moveLeft()
        keys.remove(37)
      } else if (keys.contains(39)) { // right arrow
        gameState = gameState.moveRight()
        keys.remove(39)
      } else if (keys.contains(40)) { // down arrow
        gameState = gameState.moveDown()
        keys.remove(40)
      }
      val level = gameState.lines / 10 // every 10 lines increases speed
      val gameSpeed = Vector(1,
        (20 - 40.0/scala.math.Pi * scala.math.atan(level)).toInt).max
      // gameSpeed between 1 and 20 with 20 being the slowest
      if (frame % gameSpeed == 0)
        gameState = gameState.moveDown()
      //println(gameState)

      render(gameState, ctx, 700, 500)

      frame += 1
    }

    //canvas.useCapture = true
    canvas.onkeydown = {(e: dom.KeyboardEvent) =>
      keys.add(e.keyCode.toInt)
    }

    canvas.onkeyup = {(e: dom.KeyboardEvent) =>
      keys.remove(e.keyCode.toInt)
    }

    js.timers.setInterval(40)(run)
  }

  def drawBlock(x: Int, y: Int, col: Color, ctx: dom.CanvasRenderingContext2D, rectWidth: Int, rectHeight: Int) {
    val r = col.r
    val g = col.g
    val b = col.b
    ctx.fillStyle = s"rgb($r, $g, $b)"
    ctx.fillRect(x, y, rectWidth, rectHeight)

    ctx.fillStyle = "black"
    ctx.beginPath()
    ctx.moveTo(x,y)
    ctx.lineTo(x+rectWidth,y)
    ctx.lineTo(x+rectWidth,y+rectHeight)
    ctx.lineTo(x,y+rectHeight)
    ctx.lineTo(x,y)
    ctx.stroke() 
  }

  def render(gameState: GameState, ctx: dom.CanvasRenderingContext2D, width: Int, height: Int): Unit = {
    val rectHeight: Int = height / gameState.height
    val rectWidth: Int  = rectHeight
    ctx.clearRect(0, 0, width, height) 
    
    for (j <- 0 until gameState.height) {
      for (i <- 0 until gameState.width) {
        val x: Int = rectWidth * i
        val y: Int = rectHeight * j
        drawBlock(x, y, gameState.grid(j)(i), ctx, rectWidth, rectHeight)
      }
    }
    
    for (i <- 0 until 4) {
      val x = gameState.activePiece.blocks(i).x * rectWidth
      val y = gameState.activePiece.blocks(i).y * rectHeight
      drawBlock(x,y,gameState.activePiece.color, ctx, rectWidth, rectHeight)  
    
    } 
  
    ctx.fillStyle = "LightYellow" 
    ctx.font = "bold 25px Century Gothic"
    ctx.fillText( "ROWS: " + gameState.lines, width / 2, height / 6 )
    ctx.fillText( "LEVEL: " + gameState.lines/10, width / 2, height / 4)
    
    ctx.fillText( "NEXT PIECE: ", width / 2, 3*height / 5 )
    for (i <- 0 until 4) {
      val x = 45 * width / 100 + gameState.nextPiece.blocks(i).x*rectWidth
      val y = 7 * height / 10  + gameState.nextPiece.blocks(i).y*rectHeight
      drawBlock(x,y,gameState.nextPiece.color, ctx, rectWidth, rectHeight)   
    } 
    
    if (gameState.gameEnded) {
      ctx.fillStyle = "Maroon" 
      ctx.font = "bold 50px Century Gothic"
      ctx.fillText("GAME OVER", width / 2, 45 * height / 100)
    } 
  }
}
