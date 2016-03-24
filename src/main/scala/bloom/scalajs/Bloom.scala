package bloom.scalajs

import scala.annotation.tailrec
import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport

class Bloom(numberOfBuckets: Int) {
  import Bloom._

  /**
    * The internal representation of the bit array.
    *
    * It's kind of sloppy to expose this directly, but simplifies the d3 code. If the d3 code were written in
    * scala-js this could possibly be avoided, but I've had just about enough scala-js.
    */
  @JSExport
  val bloom = new js.Array[Int](numberOfBuckets)

  @tailrec // Pretty sure this doesn't do anything on scalajs, but let's be pedantic
  private def bucketsForHash(i: BigInt, bs: List[Int] = Nil):Seq[Int] = i match {
    case BigZero => bs
    case x =>
      val q = i / numberOfBuckets
      val r = i % numberOfBuckets
      bucketsForHash(q, r.intValue :: bs)
  }

  private def bucketsForWord(word: String):Seq[Int] = {
    val hash = saxHash(word)
    bucketsForHash(hash max -hash) // keep things simple for now and ignore the sign bit
  }

  def addWord(word: String) = {
    for { i <- bucketsForWord(word) } { bloom.update(i, bloom(i) | SET) }
  }

  def containsWord(word: String) = {
    val wordBuckets = bucketsForWord(word)

    // Set the checked bit (for visualization), another artifact of exposing the bloom directly to the visualization code
    clearCheckedBits()
    wordBuckets.foreach(i => bloom.update(i, bloom(i) | CHECKED))

    // Return true only if all bits are set
    wordBuckets.forall(i => (bloom(i) & SET) != 0 )
  }

  def clearSetBits() = {
    // Clear all the set bits. This is an artifact of the not-super-great bitmasking approach.
    for { i <- 0 until bloom.size } { bloom.update(i, bloom(i) & ~SET) }
  }

  def clearCheckedBits() = {
    // Clear all the checked bits. This is an artifact of the not-super-great bitmasking approach.
    for { i <- 0 until bloom.size } { bloom.update(i, bloom(i) & ~CHECKED) }
  }

  def clearAllBits() = for { i <- 0 until bloom.size } { bloom.update(i, 0) }

  // Initialize the javascript-based storage
  clearAllBits()
}

@JSExport
object Bloom {
  // Bitmasking feels a little silly here, but solves figuring out how to bind d3 to a more complex data structure for now
  @JSExport val SET = 1      // Mask for things that have been stored in the bloom filter
  @JSExport val CHECKED = 2  // Mask for things that were checked in the last word check

  // Getting an md5 hash as a typed value in scalajs is proving to be kind of a PITA
  def saxHash(s: String): Int = s.getBytes.foldLeft(0){case (h, b) => h ^ ((h << 5) + (h >> 2) + b)}

  // TODO doesn't need to be a BigInt if we're using the sax hash
  private val BigZero = BigInt(0)
}