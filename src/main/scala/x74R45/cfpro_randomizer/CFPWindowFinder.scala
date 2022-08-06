package x74R45.cfpro_randomizer

import x74R45.cfpro_randomizer.RandomComboSelector.robot

import java.awt.{Rectangle, Toolkit}

object CFPWindowFinder {
  // Since the background is a horizontal gradient, there's
  // two RGBs for the leftmost and rightmost pixels' colors.
  final val CornerColors = ((21, 30, 57), (3, 47, 68))
  final val ColorEps = 3
  // Default screen's size, gets updated each time getScreenshotPixels is called.
  var screenSize = new Rectangle(Toolkit.getDefaultToolkit.getScreenSize)

  /**
   * Takes a screenshot, finds corners of CFPro window,
   * and returns its offset from (0,0) and CFPro window's size.
   * @return (offsetWidth: Int, offsetHeight: Int, width: Int, height: Int)
   */
  def findCFPWindow(): (Int, Int, Int, Int) = {
    val pixels = getScreenshotPixels
    val corners = findCorners(pixels)
    cornersToSize(corners)
  }

  /**
   * Takes a screenshot and turns the data into a usable state.
   * @return an IndexedSeq of red, green and blue values of each pixel.
   */
  def getScreenshotPixels: IndexedSeq[(Int, Int, Int)] = {
    // Getting default screen's size
    val screenSize = new Rectangle(Toolkit.getDefaultToolkit.getScreenSize)
    // Taking a screenshot and converting it to an array of pixels
    val image = robot.createScreenCapture(screenSize)
    val pixels = new Array[Int](screenSize.width * screenSize.height * 3)
    image.getRaster.getPixels(0, 0, screenSize.width, screenSize.height, pixels)
    // Combining RGB Ints into tuples
    for (i <- pixels.indices by 3) yield (pixels(i), pixels(i + 1), pixels(i + 2))
  }

  /**
   * Finds the corners of CFPro's main window, which scales
   * without messing up proportions of any inside elements.
   * @param pixels IndexedSeq of RGB of each pixel of the screen.
   * @return (topLeft, bottomRight) corners of the screen,
   *         each being (x: Int, y: Int).
   */
  def findCorners(pixels: IndexedSeq[(Int, Int, Int)]):
      ((Int, Int), (Int, Int)) = {
    // Initial values that are gonna be changed during iteration
    var topLeft = (screenSize.width, screenSize.height)
    var bottomRight = (0, 0)

    // A predicate to be used on a pixel to see whether it qualifies as a corner pixel
    def colorPredicate(color1: (Int, Int, Int), color2: (Int, Int, Int)): Boolean = {
      def smallEnough(x: Int): Boolean = math.abs(x) < ColorEps
      List(color1._1 - color2._1, color1._2 - color2._2, color1._3 - color2._3) forall smallEnough
    }

    // Finding top right and bottom left pixels of CFPro's window
    for (y <- 0 until screenSize.height; x <- 0 until screenSize.width) {
      val pixel = pixels(y * screenSize.width + x)
      if (colorPredicate(pixel, CornerColors._1) &&
          x + y < topLeft._1 + topLeft._2)
        topLeft = (x, y)
      else if (colorPredicate(pixel, CornerColors._2) &&
               -x - y < -bottomRight._1 - bottomRight._2)
        bottomRight = (x, y)
    }
    (topLeft, bottomRight)
  }

  /**
   * Calculates offset to CFPro window and window's size.
   * @param corners positions of CFPro window's corners
   * @return (offsetWidth: Int, offsetHeight: Int, width: Int, height: Int)
   */
  def cornersToSize(corners: ((Int, Int), (Int, Int))):
      (Int, Int, Int, Int) = corners match {
    case (topLeft, bottomRight) =>
      (topLeft._1, topLeft._2, bottomRight._1 - topLeft._1, bottomRight._2 - topLeft._2)
  }
}
