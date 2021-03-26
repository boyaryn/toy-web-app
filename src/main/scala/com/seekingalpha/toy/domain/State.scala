package com.seekingalpha.toy.domain

import scala.language.implicitConversions

/**
 * A class state representation of a a two-dimensional grid and methods to work with it.
 *
 * @constructor create a new state object with provided cell states.
 * @param value the array with cell states
 */
class State(value: Array[Array[Boolean]]) {

  require(value.map(_.length).toSet.size == 1, "state value passed which is not a 2D array")

  private val bitsInHexDigit = 4

  def this(height: Int, width: Int) {
    this(State.generateRandom(height, width))
  }

  private implicit def bool2int(b:Boolean): Int = if (b) 1 else 0

  /**
   * Encodes this State object into a string representation which can be used in URL.
   * Supports only state two-dimensional arrays in which sized of both dimensions are even.
   *
   * @return the encoded string
   */
  lazy val encoded: String = {
    value.flatten[Boolean]
      .map(_.toInt)
      .grouped(bitsInHexDigit)
      .map { group =>
        group.reduceLeft(2*_+_)
      }
      .map(_.toHexString)
      .mkString
  }

  /**
   * Performs a tick on a state according to the following rules:
   * a. Any live cell with fewer than two live neighbors dies (underpopulation).
   * b. Any live cell with two or three live neighbors lives on to the next generation.
   * c. Any live cell with more than three live neighbors dies (overcrowding).
   * d. Any dead cell with exactly three live neighbors becomes a live cell (reproduction).
   *
   * @return the new State
   */
  def tick(): State = {
    val (height, width) = (value.length, value(0).length)
    val result = Array.ofDim[Boolean](height, width)
    for (i <- 0 until height)
      for (j <- 0 until width) {
        val neighbourCoords = (i-1 to i+1).flatMap { y => (j-1 to j+1).map((y, _)) }
          .filter { case (y, x) => y >= 0 && x >= 0 && y < height && x < width }  // exclude coordinates outside the state table
          .filterNot { case (y, x) => y == i && x == j }  // exclude the current cell from its neighbour list
        val nLiveNeighbours = neighbourCoords.count { case (y, x) => value(y)(x) }
        if ((value(i)(j) && (nLiveNeighbours >=2 && nLiveNeighbours <= 3)) || (!value(i)(j) && nLiveNeighbours == 3))
          result(i)(j) = true
      }
    new State(result)
  }

  def value(): Array[Array[Boolean]] = value.map(_.clone)
}

object State {

  private def generateRandom(height: Int, width: Int) = {

    require(height > 0 && width > 0, "state table dimensions must be positive integers")

    def getRandomBoolean =
      if (Math.random > 0.5) true
      else false

    val result = Array.ofDim[Boolean](height, width)
    for (i <- 0 until height)
      for (j <- 0 until width)
        result(i)(j) = getRandomBoolean

    result
  }

  /**
   * Decodes a string of hexadecimal digits into a State object.
   *
   * @param s the encoded string
   * @param width the state table width
   * @return the decoded State object
   */
  def decode(s: String, width: Int): State = {  // todo: add error handling for incorrect parameters
    val state = s.map {
      digit => Integer.parseInt(digit.toString, 16)
    }
      .map(_.toBinaryString)
      .map(String.format("%4s", _).replace(' ', '0'))
      .mkString
      .toArray
      .map { bit =>
        if (bit == '0') false else true
      }
      .grouped(width)
      .toArray
    new State(state)
  }
}
