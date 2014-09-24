package com.abc.rules

import com.abc.Transaction

/**
 * A Concrete implementation for the checking account
 * Checking account have a flat rate of yearlyInterest% per-annum
 * For simplicity assume 360 days per year
 */
class CheckingInterestCalculator(val yearlyInterest:Double) extends InterestCalculationRule {
  import com.abc.ext.Helpers._
  override def calculateInterest(transactions: Iterable[Transaction]): Double = {
    calculateTransactionsInterest((b,d)=>b.dailyInterest(yearlyInterest,d), transactions,0.0,0.0)
  }
}
