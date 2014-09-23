package com.abc.rules

import com.abc.Transaction

/**
 * A concrete implementation for the saving account
 */
class SavingsInterestCalculator(val yearlyInterest1K:Double, val yearlyInterest2K:Double) extends InterestCalculationRule {
  private lazy val dailyInterestHigh:Double=yearlyInterest2K/360.0
  private lazy val dailyInterestLow:Double=yearlyInterest1K/360.0
  override def calculateInterest(transactions: Iterable[Transaction]): Double = {
    calculateTransactionsInterest((b,d)=> {
      if(b<=1000) b*d*dailyInterestLow
      else b*d*dailyInterestHigh
    }, transactions,0.0,0.0)
  }
}
