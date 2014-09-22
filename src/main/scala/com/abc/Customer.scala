package com.abc

import scala.collection.mutable.ListBuffer

trait CustomerVisitor {
  def visit(customer:Customer)
  def visit(account:Account)
}

class Customer(val name: String, private val _accounts: ListBuffer[Account] = ListBuffer()) {

  def accounts:Iterable[Account]=_accounts
  def openAccount(account: Account): Customer = {
    _accounts += account
    this
  }

  def numberOfAccounts: Int = _accounts.size

  def totalInterestEarned: Double = _accounts.map(_.interestEarned).sum

  /**
   * This method gets a statement
   */
  def getStatement: String = {
    //JIRA-123 Change by Joe Bloggs 29/7/1988 start
    var statement: String = null //reset statement to null here
    //JIRA-123 Change by Joe Bloggs 29/7/1988 end
    val totalAcrossAllAccounts = _accounts.map(_.sumTransactions()).sum
    statement = f"Statement for $name\n" +
      _accounts.map(statementForAccount).mkString("\n", "\n\n", "\n") +
      s"\nTotal In All Accounts ${toDollars(totalAcrossAllAccounts)}"
    statement
  }

  private def statementForAccount(a: Account): String = {
    val accountType = a.accountType match {
      case Account.CHECKING =>
        "Checking Account\n"
      case Account.SAVINGS =>
        "Savings Account\n"
      case Account.MAXI_SAVINGS =>
        "Maxi Savings Account\n"
    }
    val transactionSummary = a.transactions.map(t => withdrawalOrDepositText(t) + " " + toDollars(t.amount.abs))
      .mkString("  ", "\n  ", "\n")
    val totalSummary = s"Total ${toDollars(a.transactions.map(_.amount).sum)}"
    accountType + transactionSummary + totalSummary
  }

  private def withdrawalOrDepositText(t: Transaction) =
    t.amount match {
      case a if a < 0 => "withdrawal"
      case a if a > 0 => "deposit"
      case _ => "N/A"
    }

  private def toDollars(number: Double): String = f"$$$number%.2f"

  def accept(c:CustomerVisitor):Unit={
    c.visit(this)
    accounts.foreach(a=>c.visit(a))
  }
}

