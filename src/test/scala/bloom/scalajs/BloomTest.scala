package bloom.scalajs

import utest._

object BloomTest extends TestSuite {

  def tests = TestSuite {
    'addWords {
      val b = new Bloom(10)
      b.addWord("fish")
      assert((b.bloom(0) & Bloom.SET) == 0)
      assert((b.bloom(1) & Bloom.SET) != 0)
      assert((b.bloom(2) & Bloom.SET) == 0)
      assert((b.bloom(3) & Bloom.SET) != 0)
      assert((b.bloom(4) & Bloom.SET) != 0)
      assert((b.bloom(5) & Bloom.SET) != 0)
      assert((b.bloom(6) & Bloom.SET) == 0)
      assert((b.bloom(7) & Bloom.SET) == 0)
      assert((b.bloom(8) & Bloom.SET) != 0)
      assert((b.bloom(9) & Bloom.SET) == 0)
    }
    'checkWord {
      val b = new Bloom(10)
      b.bloom.update(0, ~Bloom.SET)
      b.bloom.update(1, Bloom.SET)
      b.bloom.update(2, ~Bloom.SET)
      b.bloom.update(3, Bloom.SET)
      b.bloom.update(4, Bloom.SET)
      b.bloom.update(5, Bloom.SET)
      b.bloom.update(6, ~Bloom.SET)
      b.bloom.update(7, ~Bloom.SET)
      b.bloom.update(8, Bloom.SET)
      b.bloom.update(9, ~Bloom.SET)

      assert(b.containsWord("fish"))
      assert(! b.containsWord("hsif"))
      assert(! b.containsWord("boat"))
      assert(! b.containsWord("deusexmachina"))
    }
    'checkedFlag {
      val b = new Bloom(10)

      assert(! b.containsWord("fish"))
      assert((b.bloom(0) & Bloom.SET) == 0)
      assert((b.bloom(1) & Bloom.SET) == 0)
      assert((b.bloom(2) & Bloom.SET) == 0)
      assert((b.bloom(3) & Bloom.SET) == 0)
      assert((b.bloom(4) & Bloom.SET) == 0)
      assert((b.bloom(5) & Bloom.SET) == 0)
      assert((b.bloom(6) & Bloom.SET) == 0)
      assert((b.bloom(7) & Bloom.SET) == 0)
      assert((b.bloom(8) & Bloom.SET) == 0)
      assert((b.bloom(9) & Bloom.SET) == 0)
      assert((b.bloom(0) & Bloom.CHECKED) == 0)
      assert((b.bloom(1) & Bloom.CHECKED) != 0)
      assert((b.bloom(2) & Bloom.CHECKED) == 0)
      assert((b.bloom(3) & Bloom.CHECKED) != 0)
      assert((b.bloom(4) & Bloom.CHECKED) != 0)
      assert((b.bloom(5) & Bloom.CHECKED) != 0)
      assert((b.bloom(6) & Bloom.CHECKED) == 0)
      assert((b.bloom(7) & Bloom.CHECKED) == 0)
      assert((b.bloom(8) & Bloom.CHECKED) != 0)
      assert((b.bloom(9) & Bloom.CHECKED) == 0)
    }
    'clearChecked {
      val b = new Bloom(10)

      assert(! b.containsWord("fish"))
      b.clearCheckedBits()
      assert((b.bloom(0) & Bloom.CHECKED) == 0)
      assert((b.bloom(1) & Bloom.CHECKED) == 0)
      assert((b.bloom(2) & Bloom.CHECKED) == 0)
      assert((b.bloom(3) & Bloom.CHECKED) == 0)
      assert((b.bloom(4) & Bloom.CHECKED) == 0)
      assert((b.bloom(5) & Bloom.CHECKED) == 0)
      assert((b.bloom(6) & Bloom.CHECKED) == 0)
      assert((b.bloom(7) & Bloom.CHECKED) == 0)
      assert((b.bloom(8) & Bloom.CHECKED) == 0)
      assert((b.bloom(9) & Bloom.CHECKED) == 0)
    }
    'clearSet {
      val b = new Bloom(10)

      b.addWord("fish")
      b.clearSetBits()
      assert((b.bloom(0) & Bloom.SET) == 0)
      assert((b.bloom(1) & Bloom.SET) == 0)
      assert((b.bloom(2) & Bloom.SET) == 0)
      assert((b.bloom(3) & Bloom.SET) == 0)
      assert((b.bloom(4) & Bloom.SET) == 0)
      assert((b.bloom(5) & Bloom.SET) == 0)
      assert((b.bloom(6) & Bloom.SET) == 0)
      assert((b.bloom(7) & Bloom.SET) == 0)
      assert((b.bloom(8) & Bloom.SET) == 0)
      assert((b.bloom(9) & Bloom.SET) == 0)
    }
  }
}
