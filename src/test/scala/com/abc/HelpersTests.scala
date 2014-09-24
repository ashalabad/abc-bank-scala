package com.abc

import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by igor_pol on 9/23/14.
 */
class HelpersTests extends FlatSpec with Matchers
{
  import com.abc.ext.Helpers._

  it should "format correct dollars amount" in {
    10.25.toDollars should be("$10.25")
    1.002123.toDollars should be("$1.00")
  }
  it should "pluralize correctly" in {
    1.pluralize("item") should be("1 item")
    2.pluralize("item") should be("2 items")
  }

  it should "calculate interest correctly" in {
    100.0.dailyInterest(5.0,360) should be(5.0)
    100.0.dailyInterest(5.0,180) should be(2.5)
  }
}
