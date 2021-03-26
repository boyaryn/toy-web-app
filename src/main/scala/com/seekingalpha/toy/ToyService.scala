package com.seekingalpha.toy

import com.seekingalpha.toy.domain.State

object ToyService {

  private val tableSize = 50

  /**
   * Produces an HTML table which visualizes a State object.
   * Alive cells are shown as black table cells and stored as True in the State object,
   * dead cells are shown as white table cells and stored as False.
   *
   * @param state the State object
   * @return the HTML code for the table to be rendered
   */
  private def renderTable(state: State) = {
    val tableSource = new StringBuilder()
    tableSource.append("""<table cellspacing="1" cellpadding="5" border="1" align="center">""")
    state.value()
      .foreach { row =>
        tableSource.append("<tr>")
        row.foreach { cell =>
          if (cell) {
            tableSource.append("""<td style="background: black;"></td>""")
          } else {
            tableSource.append("<td></td>")
          }
        }
        tableSource.append("</tr>")
      }
    tableSource.append("</table>")
    tableSource.toString
  }

  def renderPage(stateValue: Option[String]): String = {

    val state = stateValue match {
      case Some(value) =>
        val oldState = State.decode(value, tableSize)
        oldState.tick()
      case _ => new State(tableSize, tableSize)
    }
    val encoded = state.encoded

    val pageSource = new StringBuilder()
    pageSource.append("<html>")
    pageSource.append("<body>")
    pageSource.append("""<table align="center"><tr><td style="text-align: center;">""")
    pageSource.append(s"""<button onclick="window.location.href='http://localhost:8080/?state=$encoded';">Tick</button>""")
    pageSource.append(renderTable(state))
    pageSource.append("</td></tr></table>")
    pageSource.append("</body>")
    pageSource.append("</html>")
    pageSource.toString
  }
}
