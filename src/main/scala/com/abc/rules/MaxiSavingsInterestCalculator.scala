package com.abc.rules

import com.abc.{DateProvider, Transaction}
import org.joda.time.Days

/**
 * A concrete implementation for Maxi Saving interest calculator
 * interest rate of yearIterestHigh assuming no withdrawals in the past 10 days otherwise yearInterestLow
 */
class MaxiSavingsInterestCalculator(val yearlyInterestLow:Double, val yearlyInterestHigh:Double) extends InterestCalculationRule {
  override def calculateInterest(transactions: Iterable[Transaction]): Double = {
    val negativeTrx = transactions.filter(t=>t.amount<0).lastOption
    val dailyInterest = if(!negativeTrx.isEmpty &&
      Days.daysBetween(negativeTrx.get.transactionDate,DateProvider.getInstance.now).getDays<=10)
    {
      yearlyInterestLow/360.0
    } else
      yearlyInterestHigh/360.0
    calculateTransactionsInterest((b,d)=>b*d*dailyInterest, transactions,  0.0, 0.0)
  }
}
