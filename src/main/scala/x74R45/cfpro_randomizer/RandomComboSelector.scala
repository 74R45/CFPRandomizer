package x74R45.cfpro_randomizer

import java.awt.Robot
import java.awt.event.InputEvent
import scala.util.Random

object RandomComboSelector {
  val robot = new Robot()
  val rand = new Random()

  // Locations of each power on the screen, relative to CFPro window's width and height
  final val PowersRelativeLocations = IndexedSeq(
    (0.372,0.168), (0.442,0.168), (0.511,0.168), (0.578,0.168), (0.645,0.168),
    (0.372,0.305), (0.442,0.305), (0.511,0.305), (0.578,0.305), (0.645,0.305),
    (0.372,0.438), (0.442,0.438), (0.511,0.438), (0.578,0.438), (0.645,0.438),
    (0.372,0.570), (0.442,0.570), (0.511,0.570), (0.578,0.570), (0.645,0.570),
    (0.372,0.707), (0.442,0.707), (0.511,0.707), (0.578,0.707), (0.645,0.707),
    (0.372,0.840), (0.442,0.840), (0.511,0.840), (0.578,0.840), (0.645,0.840)
  )
  final val PowerSelectionLocations = IndexedSeq((0.811, 0.277), (0.811, 0.414))

  // PowerSelectionTree containing all power ids and their weights during the selection
  final val SelTree: PowerSelectionTree = Node(1, IndexedSeq(
    // Purple
    Node(2, IndexedSeq(Leaf(1, 4), Leaf(1, 15), Leaf(1, 23), Leaf(1, 26))), // Shots
    Leaf(1, 0), // Homing
    Leaf(1, 9), // Scatter
    Leaf(1, 6), // Zap
    Leaf(1, 28), // Laser
    // Green
    Node(1.5, IndexedSeq(Leaf(1, 16), Leaf(1, 27))), // Speedy & Reverse
    Node(2.5, IndexedSeq( // Other fevers
      Leaf(1, 3), Leaf(1, 7), Leaf(1, 18),
      Leaf(1, 21), Leaf(1, 10), Leaf(1, 13))),
    // Red
    Node(1, IndexedSeq(Leaf(1, 5), Leaf(1, 12))), // Mines
    Leaf(1, 19), Leaf(1, 24), // Time Bomb & Trigger Bomb
    // Blue
    Leaf(1, 1), Leaf(1, 2), Leaf(1, 8), Leaf(1, 11), Leaf(1, 14),
    Leaf(1, 17), Leaf(1, 20), Leaf(1, 22), Leaf(1, 25), Leaf(1, 29)
  ))

  final val PowerNames = IndexedSeq("Homing", "Jump", "Brake", "Thick", "One Shot", "Mine", "Zap", "Low-res", "Thin", "Scatter Shot", "Steer-less", "Speed Burst", "Stealth Mine", "Trippy", "Power Dash", "Side Shot", "Speedy", "Shield", "Clock Block", "Time Bomb", "Hide Self", "Curve-blind", "Angle Turns", "Multi Shot", "Trigger Bomb", "180", "Double Shot", "Reverse", "Laser", "Teleport")

  def run(): Unit = {
    val sizes = CFPWindowFinder.findCFPWindow()
    val powers = generateCombo()
    println(s"${PowerNames(powers._1)} + ${PowerNames(powers._2)}")
    executeMacro(powers, sizes)
  }

  /**
   * Generates two indices for a combo to select.
   * @return (power1: Int, power2: Int); 0 &le; power1, power2 &lt; 30; power1 &ne; power2
   */
  def generateCombo(): (Int, Int) = {
    val power1 = SelTree.select(rand)
    var power2 = SelTree.select(rand)
    while (power1 == power2) {
      power2 = SelTree.select(rand)
    }
    (power1, power2)
  }

  /**
   * For each of the two powers, goes to its location,
   * holds LMB, goes to selection location and releases.
   * @param powers indices of the chosen powers
   * @param sizes parameters of the window: (offsetWidth: Int, offsetHeight: Int, width: Int, height: Int)
   */
  def executeMacro(powers: (Int, Int), sizes: (Int, Int, Int, Int)): Unit = {
    // Get global location of a relative location
    def getLocation(relativeLocation: (Double, Double)): (Int, Int) =
      relativeLocation match { case (x, y) =>
        ((x * sizes._3 + sizes._1).toInt,
         (y * sizes._4 + sizes._2).toInt)
      }

    // Calculating needed locations
    val power1Loc = getLocation(PowersRelativeLocations(powers._1))
    val power2Loc = getLocation(PowersRelativeLocations(powers._2))
    val selection1Loc = getLocation(PowerSelectionLocations(0))
    val selection2Loc = getLocation(PowerSelectionLocations(1))

    // Run macro to select a power
    def selectPower(powerLoc: (Int, Int), selectLoc: (Int, Int)): Unit = {
      robot.mouseMove(powerLoc._1, powerLoc._2)
      Thread.sleep(50)
      robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
      Thread.sleep(50)
      robot.mouseMove((selectLoc._1 + powerLoc._1) / 2, (selectLoc._2 + powerLoc._2) / 2)
      Thread.sleep(250)
      robot.mouseMove(selectLoc._1, selectLoc._2)
      Thread.sleep(500)
      robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
    }

    // Running the macros
    selectPower(power1Loc, selection1Loc)
    selectPower(power2Loc, selection2Loc)
  }
}
