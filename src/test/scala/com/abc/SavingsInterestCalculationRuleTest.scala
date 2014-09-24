package com.abc

import com.abc.rules.SavingsInterestCalculator
import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by igor on 9/23/14.
 */
class SavingsInterestCalculationRuleTest extends FlatSpec with Matchers {
  import com.abc.ext.Helpers._
  private val _calc=new SavingsInterestCalculator
  it should "should calculate correct interest for amount < 1000" in {
    val interest=_calc.calculateInterest(List(Transaction(DateProvider.getInstance.now.minusDays(360),100.0)))
    interest should be(100.0.dailyInterest(0.1,360))
  }
  it should "should calculate correct interest for amount == 1000" in {
    val interest=_calc.calculateInterest(List(Transaction(DateProvider.getInstance.now.minusDays(360),1000.0)))
    interest should be(1000.0.dailyInterest(0.1,360))
  }
  it should "should calculate correct interest for amount > 1000" in {
    val interest=_calc.calculateInterest(List(Transaction(DateProvider.getInstance.now.minusDays(360),1500.0)))
    interest should be(1500.0.dailyInterest(0.2,360))
  }
  it should "should calculate correct interest for amount >=2000" in {
    val interest=_calc.calculateInterest(List(Transaction(DateProvider.getInstance.now.minusDays(360),2000.0)))
    interest should be(2000.0.dailyInterest(0.2,360))
  }

}
