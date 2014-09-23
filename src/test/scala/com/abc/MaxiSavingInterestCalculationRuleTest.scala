package com.abc

import com.abc.rules.MaxiSavingsInterestCalculator
import org.joda.time.Days
import org.scalatest.{FlatSpec, Matchers}
/**
 * Created by igor on 9/21/14.
 */
class MaxiSavingInterestCalculationRuleTest extends FlatSpec with Matchers {

  "MaxiSaving Interest Calculator Rule" should "accumulate 5% interest rate for single transaction of 360 days" in {
    val rule=new MaxiSavingsInterestCalculator(0.001,0.05)
    val transaction=new Transaction(DateProvider.getInstance.now.minusDays(360),1000.0)
    val interest=rule.calculateInterest(List(transaction))
    interest should be(50.0)
  }

  it should "accumulate 5% interest rate for single transaction of 180 days" in {
    val rule=new MaxiSavingsInterestCalculator(0.001,0.05)
    val transaction=new Transaction(DateProvider.getInstance.now.minusDays(180),1000.0)
    val interest=rule.calculateInterest(List(transaction))
    interest should be(25.0)
  }

  it should "accumulate 5% interest rate for two transactions of 180 and 360 days" in {
    val rule=new MaxiSavingsInterestCalculator(0.001,0.05)
    val transaction180=new Transaction(DateProvider.getInstance.now.minusDays(180),1000.0)
    val transaction360=new Transaction(DateProvider.getInstance.now.minusDays(360),1000.0)
    val interest=rule.calculateInterest(List(transaction360,transaction180))
    interest should be(75.0)
  }

  it should "accumulate 0.1% interest rate if there was a withdrawal within 10 days" in {
    val rule=new MaxiSavingsInterestCalculator(0.001,0.05)
    val transaction180=new Transaction(DateProvider.getInstance.now.minusDays(180),1000.0)
    val transaction360=new Transaction(DateProvider.getInstance.now.minusDays(360),1000.0)
    val transactionMinus=new Transaction(DateProvider.getInstance.now.minusDays(5),-1000.0)
    val transactionLast=new Transaction(DateProvider.getInstance.now.minusDays(1),1000.0)
    val interest=rule.calculateInterest(List(transaction360,transaction180,transactionMinus,transactionLast))
    val t=1000*0.001+1000*0.0005+1000*(0.001/360)-(1000*((0.001/360)*5))
    interest should be(t)

  }

  it should "accumulate 5% interest rate if there was a withdrawal older than 10 days" in {
    val rule=new MaxiSavingsInterestCalculator(0.001,0.05)
    val transaction180=new Transaction(DateProvider.getInstance.now.minusDays(180),1000.0)
    val transaction360=new Transaction(DateProvider.getInstance.now.minusDays(360),1000.0)
    val transactionMinus=new Transaction(DateProvider.getInstance.now.minusDays(25),-1000.0)
    val transactionLast=new Transaction(DateProvider.getInstance.now.minusDays(1),1000.0)
    val interest=rule.calculateInterest(List(transaction360,transaction180,transactionMinus,transactionLast))
    val t=1000*0.05+1000*0.025+1000*(0.05/360)-(1000*((0.05/360)*25))
    interest should be(t)

  }

}
