package com.abc.rules

import com.abc.{DateProvider, Transaction}
import org.joda.time.Days

/**
 * A concrete implementation for Maxi Saving interest calculator
 */
class MaxiSavingsInterestCalculator extends InterestCalculationRule {
  import com.abc.ext.Helpers._
  override def calculateInterest(transactions: Iterable[Transaction]): Double = {
    val negativeTrx = transactions.filter(t=>t.amount<0).lastOption
    val interest = if(!negativeTrx.isEmpty &&
      Days.daysBetween(negativeTrx.get.transactionDate,DateProvider.getInstance.now).getDays<=10)
    {
      0.1
    } else
      5.0
    calculateTransactionsInterest((b,d)=>b.dailyInterest(interest,d), transactions,  0.0, 0.0)
  }
}
