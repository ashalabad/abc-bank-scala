package com.abc
import org.scalatest.{FlatSpec, Matchers}
/**
 * Created by igor on 9/21/14.
 */
class CheckingInterestCalculationRuleTest extends FlatSpec with Matchers {

  "Checking Interest Calculator Rule" should "return 0.0 for empty transactions" in {
    val rule=new CheckingInterestCalculator(0.001)
    rule.calculateInterest(List[Transaction]())

  }

  it should "accumulate a flat rate of 0.1% for 360 days with one transaction" in {
    val rule=new CheckingInterestCalculator(0.001)
    val transaction=new Transaction(DateProvider.getInstance.now.minusDays(360),1000.0)
    val interest = rule.calculateInterest(List(transaction))
    interest should be(1.0)
  }

  it should "accumulate a flat rate of 0.1% for 180 days with one transaction"  in {
    val rule=new CheckingInterestCalculator(0.001)
    val transaction=new Transaction(DateProvider.getInstance.now.minusDays(180),1000.0)
    val interest = rule.calculateInterest(List(transaction))
    interest should be(0.5)
  }

  it should "calculate correct interest of 0.1% rate for two transactions of 360 days old and 180 days old" in {
    val rule=new CheckingInterestCalculator(0.001)
    val transaction360=new Transaction(DateProvider.getInstance.now.minusDays(360),1000.0)
    val transaction180=new Transaction(DateProvider.getInstance.now.minusDays(180),1000.0)
    val interest = rule.calculateInterest(List(transaction360,transaction180))
    interest should be(1.5)
  }

  it should "calculate correct interest of 01.% rate for positive and negative transaction of 360 days old and 180 days old" in {
    val rule=new CheckingInterestCalculator(0.001)
    val transaction360=new Transaction(DateProvider.getInstance.now.minusDays(360),1000.0)
    val transaction180=new Transaction(DateProvider.getInstance.now.minusDays(180),-500.0)
    val interest = rule.calculateInterest(List(transaction360,transaction180))
    interest should be(0.75)
  }


  it should "calculate correct interest for three transaction of two years old one year old and 180 days old" in {
    val rule=new CheckingInterestCalculator(0.001)
    val transaction720=new Transaction(DateProvider.getInstance.now.minusDays(720),1000.0)
    val transaction360=new Transaction(DateProvider.getInstance.now.minusDays(360),1000.0)
    val transaction180=new Transaction(DateProvider.getInstance.now.minusDays(180),1000.0)
    val interest = rule.calculateInterest(List(transaction720, transaction360, transaction180))
    interest should be(3.5)

  }
}
