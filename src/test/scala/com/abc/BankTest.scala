package com.abc

import org.joda.time.DateTime
import org.scalatest.{Matchers, FlatSpec}

class BankTest extends FlatSpec with Matchers {

  "Bank" should "customer summary" in {
    val bank: Bank = new Bank
    var john: Customer = new Customer("John").openAccount(Account.CHECKING)
    bank.addCustomer(john)
    bank.customerSummary should be("Customer Summary\n - John (1 account)")
  }

  it should "checking account" in {
    val bank: Bank = new Bank
    trait MockDateProvider extends AccountDateProvider {
      this:Account=>
      def currentDate:DateTime=DateProvider.getInstance.now.minusDays(360)
    }
    val checkingAccount: Account =new Account with CheckingInterestEarner
      with CheckingName with MockDateProvider

    val bill: Customer = new Customer("Bill").openAccount(checkingAccount)
    bank.addCustomer(bill)
    checkingAccount.deposit(100.0)
    bank.totalInterestPaid should be(0.1)
  }

  it should "savings account" in {
    val bank: Bank = new Bank
    trait MockDateProvider extends AccountDateProvider {
      this:Account=>
      def currentDate:DateTime=DateProvider.getInstance.now.minusDays(360)
    }
    val savingsAccount: Account =new Account with SavingsInterestEarner
      with SavingsName with MockDateProvider
    bank.addCustomer(new Customer("Bill").openAccount(savingsAccount))
    savingsAccount.deposit(1500.0)
    bank.totalInterestPaid should be(2.0)
  }

  it should "maxi savings account" in {
    val bank: Bank = new Bank
    val checkingAccount: Account = Account.MAXI_SAVINGS
    bank.addCustomer(new Customer("Bill").openAccount(checkingAccount))
    checkingAccount.deposit(3000.0)
    bank.totalInterestPaid should be(170.0)
  }

}
