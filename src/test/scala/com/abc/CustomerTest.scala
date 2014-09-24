package com.abc

import com.abc.report.CustomerReportGenerator
import org.scalatest.{Matchers, FlatSpec}

class CustomerTest extends FlatSpec with Matchers {
  "Customer" should "generate the statement" in {
    val checkingAccount: Account =Account.CHECKING
    val savingsAccount: Account = Account.SAVINGS
    val henry: Customer = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount)
    checkingAccount.deposit(100.0)
    savingsAccount.deposit(4000.0)
    savingsAccount.withdraw(200.0)
    new CustomerReportGenerator().summary(henry) should be("Statement for Henry\n" +
      "\nChecking Account\n  deposit $100.00\nTotal $100.00\n" +
      "\nSavings Account\n  deposit $4000.00\n  withdrawal $200.00\nTotal $3800.00\n" +
      "\nTotal In All Accounts $3900.00")
  }

  it should "testOneAccount" in {
    val oscar: Customer = new Customer("Oscar").openAccount(Account.SAVINGS)
    oscar.numberOfAccounts should be(1)
  }

  it should "testTwoAccount" in {
    val oscar: Customer = new Customer("Oscar").openAccount(Account.SAVINGS)
    oscar.openAccount(Account.CHECKING)
    oscar.numberOfAccounts should be(2)
  }

  it should "testThreeAccounts" in {
    val oscar: Customer = new Customer("Oscar").openAccount(Account.SAVINGS)
    oscar.openAccount(Account.CHECKING).openAccount(Account.MAXI_SAVINGS)
    oscar.numberOfAccounts should be(3)
  }

  it should "allow transfer between accounts" in {
    val oscar: Customer = new Customer("Oscar").openAccount(Account.SAVINGS)
    oscar.openAccount(Account.CHECKING)
    oscar.numberOfAccounts should be(2)
    oscar.accounts.head.deposit(100.0)
    oscar.transfer(oscar.accounts.head,oscar.accounts.tail.head,50.0)
    oscar.accounts.head.balance should be(50.0)
    oscar.accounts.tail.head.balance should be(50.0)
  }

  it should "fail transfer between accounts with insufficient funds" in {
    val oscar: Customer = new Customer("Oscar").openAccount(Account.SAVINGS)
    oscar.openAccount(Account.CHECKING)
    oscar.numberOfAccounts should be(2)
    oscar.accounts.head.deposit(100.0)
    withClue("not enough funds") {
      intercept[IllegalArgumentException] {
        oscar.transfer(oscar.accounts.head,oscar.accounts.tail.head,200.0)
      }
    }
  }
}
