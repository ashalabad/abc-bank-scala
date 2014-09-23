package com.abc

/**
 * Created by igor on 9/22/14.
 */
class CustomerSummaryGenerator {
  import com.abc.ext.Helpers._
  private val statementGenerator=new AccountStatementGenerator

  def summary(c:Customer):String ={
    val totalAcrossAllAccounts = c.accounts.map(_.sumTransactions()).sum
    val statement = f"Statement for ${c.name}\n" +
      c.accounts.map(a=>statementGenerator.statement(a)).mkString("\n", "\n\n", "\n") +
      s"\nTotal In All Accounts ${totalAcrossAllAccounts.toDollars}"
    statement
  }
}
