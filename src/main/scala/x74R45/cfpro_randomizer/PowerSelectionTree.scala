package x74R45.cfpro_randomizer

import scala.util.Random

trait PowerSelectionTree {
  val weight: Double
  def select(rand: Random): Int
}

case class Node(weight: Double, children: IndexedSeq[PowerSelectionTree]) extends PowerSelectionTree {
  def select(rand: Random): Int = {
    val weightSum = children.foldLeft(0d)(_ + _.weight)
    var gen = rand.nextFloat() * weightSum

    for (ch <- children) {
      gen -= ch.weight
      if (gen <= 0) return ch.select(rand)
    }
    children.last.select(rand)
  }
}

case class Leaf(weight: Double, idx: Int) extends PowerSelectionTree {
  def select(rand: Random): Int = idx
}
