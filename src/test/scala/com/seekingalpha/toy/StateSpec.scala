package com.seekingalpha.toy

import com.seekingalpha.toy.domain.State
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers.contain
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

class StateSpec extends AnyFreeSpec {

  "A State" - {
    "with its tick method" - {

      "should keep a grid with the isolated 2x2 block of live cells the same" in {
        val old = Array(
          Array(false, false, false, false),
          Array(false, true, true, false),
          Array(false, true, true, false),
          Array(false, false, false, false)
        )

        new State(old).tick().value() should contain theSameElementsInOrderAs old
      }

      "should rotate the isolated 1x3 block of live cells" - {

        val horizontal = Array(
          Array(false, false, false, false, false),
          Array(false, false, false, false, false),
          Array(false, true, true, true, false),
          Array(false, false, false, false, false),
          Array(false, false, false, false, false)
        )

        val vertical = Array(
          Array(false, false, false, false, false),
          Array(false, false, true, false, false),
          Array(false, false, true, false, false),
          Array(false, false, true, false, false),
          Array(false, false, false, false, false)
        )

        "namely horizontal should become vertical" in {
          new State(horizontal).tick().value() should contain theSameElementsInOrderAs vertical
        }

        "and vertical should become horizontal" in {
          new State(vertical).tick().value() should contain theSameElementsInOrderAs horizontal
        }
      }
    }

    "should correctly encode 4x4 array with True values in the centre into string" in {
      val value = Array(
        Array(false, false, false, false),
        Array(false, true, true, false),
        Array(false, true, true, false),
        Array(false, false, false, false)
      )

      new State(value).encoded shouldBe "0660"
    }
  }
}
