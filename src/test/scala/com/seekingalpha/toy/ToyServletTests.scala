package com.seekingalpha.toy

import org.scalatra.test.scalatest._

class ToyServletTests extends ScalatraFunSuite {

  addServlet(classOf[ToyServlet], "/*")

  test("GET / on ToyServlet should return status 200") {
    get("/") {
      status should equal (200)
    }
  }

}
