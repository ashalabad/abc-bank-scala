package com.abc

import org.scalatest.{Matchers, FlatSpec}

/**
 * Created by igor on 9/22/14.
 */
class StatementGeneratorTest extends FlatSpec with Matchers{
  import com.abc.ext.Helpers._
  it should "format correct dollars amount" in {
    10.25.toDollars should be("$10.25")
    1.002123.toDollars should be("$1.00")
  }
  it should "pluralize correctly" in {
    1.pluralize("item") should be("1 item")
    2.pluralize("item") should be("2 items")
  }
  it should "generate correct statement for checking account" in {
    val checkingAccount: Account =Account.CHECKING
    checkingAccount.deposit(100.0)
    checkingAccount.withdraw(50.0)
    val g=new AccountStatementGenerator
    g.statement(checkingAccount) should be("Checking Account\n  deposit $100.00\n  withdrawal $50.00\nTotal $50.00")
  }
  it should "generate correct statement for two accounts" in {
    val checkingAccount: Account =Account.CHECKING
    val savingsAccount: Account = Account.SAVINGS
    val henry: Customer = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount)
    checkingAccount.deposit(100.0)
    savingsAccount.deposit(4000.0)
    savingsAccount.withdraw(200.0)
    val g=new CustomerSummaryGenerator
    g.summary(henry) should be("Statement for Henry\n" +
      "\nChecking Account\n  deposit $100.00\nTotal $100.00\n" +
      "\nSavings Account\n  deposit $4000.00\n  withdrawal $200.00\nTotal $3800.00\n" +
      "\nTotal In All Accounts $3900.00")
  }
}
