package com.abc.rules

import com.abc.Transaction

/**
 * A Concrete implementation for the checking account
 * Checking account have a flat rate of 0.1% per-annum
 * For simplicity assume 360 days per year
 */
class CheckingInterestCalculator(val yearlyInterest:Double) extends InterestCalculationRule {
  private lazy val dailyInterest=yearlyInterest/360.0
  override def calculateInterest(transactions: Iterable[Transaction]): Double = {
    calculateTransactionsInterest((b,d)=>b*d*dailyInterest, transactions,0.0,0.0)
  }
}
