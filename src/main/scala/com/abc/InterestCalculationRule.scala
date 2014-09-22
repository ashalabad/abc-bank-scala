package com.abc

import org.joda.time.Days

/**
 * A base trait for interest calculation rules
 */
trait InterestCalculationRule {
  /**
   * Caclulate Interest rates
   * @param transactions transactions
   * @return acquired interest rate
   */
  def calculateInterest(transactions:Iterable[Transaction]):Double
}

trait SimpleInterestCalculationRule extends InterestCalculationRule {

  protected def calculateTransactionsInterest(
                                               func:(Double,Int)=>Double,
                                               trx:Iterable[Transaction],
                                               ballance:Double,
                                               acquiredInterest:Double):Double = {
    trx match {
      case x::Nil=>{
        val days:Int = Days.daysBetween(x.transactionDate,DateProvider.getInstance.now).getDays
        acquiredInterest+func(ballance+x.amount,days)
      }
      case x::xs=>{
        val days:Int = Days.daysBetween(x.transactionDate,xs.head.transactionDate).getDays
        calculateTransactionsInterest(func, xs,ballance+x.amount,
          acquiredInterest+func(ballance+x.amount,days))
      }
      case Nil=>{
        0.0
      }
    }
  }

}
/**
 * A Concrete implementation for the checking account
 * Checking account have a flat rate of 0.1% per-annum
 * For simplicity assume 360 days per year
 */
class CheckingInterestCalculator(val yearlyInterest:Double) extends SimpleInterestCalculationRule {
  private lazy val dailyInterest=yearlyInterest/360.0
  override def calculateInterest(transactions: Iterable[Transaction]): Double = {
    calculateTransactionsInterest((b,d)=>b*d*dailyInterest, transactions,0.0,0.0)
  }
}

/**
 * A concrete implementation for the saving account
 */
class SavingInterestCalculator(val yearlyInterest1K:Double, val yearlyInterest2K:Double) extends SimpleInterestCalculationRule {
  private lazy val dailyInterestHigh:Double=yearlyInterest2K/360.0
  private lazy val dailyInterestLow:Double=yearlyInterest1K/360.0
  override def calculateInterest(transactions: Iterable[Transaction]): Double = {
    calculateTransactionsInterest((b,d)=> {
      if(b<=1000) b*d*dailyInterestLow
      else b*d*dailyInterestHigh
    }, transactions,0.0,0.0)
  }
}

/**
 * A concrete implementation for Maxi Saving interest calculator
 * interest rate of yearIterestHigh assuming no withdrawals in the past 10 days otherwise yearInterestLow
 */
class MaxiSavingInterestCalculator(val yearlyInterestLow:Double, val yearlyInterestHigh:Double) extends SimpleInterestCalculationRule {
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