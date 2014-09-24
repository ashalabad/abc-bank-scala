package com.abc.report

import com.abc.{Account, Transaction}

/**
 * Account Statement Generator
 */
class AccountReportGenerator {
  import com.abc.ext.Helpers._
  /**
   * Generate an account statement
   * @param a
   * @return
   */
  def statement(a:Account) : String = {
    val transactionSummary = a.transactions.map(t => withdrawalOrDepositText(t) + " " + t.amount.abs.toDollars)
      .mkString("  ", "\n  ", "\n")
    val totalSummary = s"Total ${a.transactions.map(_.amount).sum.toDollars}"
    a.name + "\n"+ transactionSummary + totalSummary
  }
  private def withdrawalOrDepositText(t: Transaction) =
    t.amount match {
      case a if a < 0 => "withdrawal"
      case a if a > 0 => "deposit"
      case _ => "N/A"
    }

}
