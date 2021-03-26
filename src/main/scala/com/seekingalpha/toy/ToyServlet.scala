package com.seekingalpha.toy

import org.scalatra._
import ToyService._

class ToyServlet extends ScalatraServlet {

  get("/") {
    contentType="text/html"
    renderPage(params.get("state"))
  }
}
