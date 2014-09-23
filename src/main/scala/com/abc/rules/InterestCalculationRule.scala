package com.abc.rules

import com.abc.{DateProvider, Transaction}
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
