import junit.framework.TestCase
import x74R45.cfpro_randomizer.RandomComboSelector

class CFPRandomizerSuite extends TestCase {

  val rand = RandomComboSelector.rand
  val tree = RandomComboSelector.SelTree

  def testHundredThousandSelects(): Unit = {
    val nRuns = 100000
    val gens = new Array[Int](30)
    for (_ <- 0 until nRuns) {
      val p = tree.select(rand)
      gens(p) += 1
    }
    gens.zipWithIndex.foreach {
      case (n, i) => println(s"${RandomComboSelector.PowerNames(i)} - $n - ${n.toDouble/nRuns*100}%")
    }
    assert(!gens.contains(0))
  }
}
