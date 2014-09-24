package com.abc.rules

import com.abc.Transaction

/**
 * A concrete implementation for the saving account
 */
class SavingsInterestCalculator extends InterestCalculationRule {
  import com.abc.ext.Helpers._
  override def calculateInterest(transactions: Iterable[Transaction]): Double = {
    calculateTransactionsInterest((b,d)=> {
      if(b<=1000) b.dailyInterest(0.1,d)
      else b.dailyInterest(0.2,d)
    }, transactions,0.0,0.0)
  }
}
