package com.abc

import org.scalatest.{FlatSpec, Matchers}

class TransactionTest extends FlatSpec with Matchers {
  it should "type" in {
    val t = new Transaction(DateProvider.getInstance.now, 5)
    t.isInstanceOf[Transaction] should be(true)
  }

  it should "have fields populated" in {

  }
}
