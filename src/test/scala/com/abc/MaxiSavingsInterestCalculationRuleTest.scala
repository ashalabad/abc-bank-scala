package com.abc

import com.abc.rules.MaxiSavingsInterestCalculator
import org.joda.time.Days
import org.scalatest.{FlatSpec, Matchers}
/**
 * Created by igor on 9/21/14.
 */
class MaxiSavingsInterestCalculationRuleTest extends FlatSpec with Matchers {
  import com.abc.ext.Helpers._
  "MaxiSaving Interest Calculator Rule" should "accumulate 5% interest rate for single transaction of 360 days" in {
    val rule=new MaxiSavingsInterestCalculator()
    val transaction=new Transaction(DateProvider.getInstance.now.minusDays(360),1000.0)
    val interest=rule.calculateInterest(List(transaction))
    interest should be(50.0)
  }

  it should "accumulate 5% interest rate for single transaction of 180 days" in {
    val rule=new MaxiSavingsInterestCalculator()
    val transaction=new Transaction(DateProvider.getInstance.now.minusDays(180),1000.0)
    val interest=rule.calculateInterest(List(transaction))
    interest should be(25.0)
  }

  it should "accumulate 5% interest rate for two transactions of 180 and 360 days" in {
    val rule=new MaxiSavingsInterestCalculator()
    val transaction180=new Transaction(DateProvider.getInstance.now.minusDays(180),1000.0)
    val transaction360=new Transaction(DateProvider.getInstance.now.minusDays(360),1000.0)
    val interest=rule.calculateInterest(List(transaction360,transaction180))
    interest should be(75.0)
  }

  it should "accumulate 0.1% interest rate if there was a withdrawal within 10 days" in {
    val rule=new MaxiSavingsInterestCalculator()
    val transaction180=new Transaction(DateProvider.getInstance.now.minusDays(180),1000.0)
    val transaction360=new Transaction(DateProvider.getInstance.now.minusDays(360),1000.0)
    val transactionMinus=new Transaction(DateProvider.getInstance.now.minusDays(5),-1000.0)
    val interest=rule.calculateInterest(List(transaction360,transaction180,transactionMinus))
    val t=1000.0.dailyInterest(0.1,360)+1000.0.dailyInterest(0.1,175)
    interest should be(t)

  }

  it should "accumulate 5% interest rate if there was a withdrawal older than 10 days" in {
    val rule=new MaxiSavingsInterestCalculator()
    val transaction180=new Transaction(DateProvider.getInstance.now.minusDays(180),1000.0)
    val transaction360=new Transaction(DateProvider.getInstance.now.minusDays(360),1000.0)
    val transactionMinus=new Transaction(DateProvider.getInstance.now.minusDays(100),-1000.0)
    val transactionLast=new Transaction(DateProvider.getInstance.now.minusDays(80),1000.0)
    val interest=rule.calculateInterest(List(transaction360,transaction180,transactionMinus,transactionLast))
    val t=1000.0.dailyInterest(5.0,80)+1000.0.dailyInterest(5.0,360)+1000.0.dailyInterest(5.0,80)
    interest should be(t)
  }

}
