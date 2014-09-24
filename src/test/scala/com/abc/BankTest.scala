package com.abc

import com.abc.report.BankReportGenerator
import org.joda.time.DateTime
import org.scalatest.{Matchers, FlatSpec}

class BankTest extends FlatSpec with Matchers {

  "Bank" should "should print customer summary" in {
    val bank: Bank = new Bank
    val john: Customer = new Customer("John").openAccount(Account.CHECKING)
    bank.addCustomer(john)
    val g=new BankReportGenerator
    g.summary(bank) should be("Customer Summary\n - John (1 account)")
  }

  it should "checking account" in {
    val bank: Bank = new Bank
    trait MockTransactionStore extends InMemoryTransactionStore  {
      this:Account=>
      override def currentDate:DateTime=DateProvider.getInstance.now.minusDays(360)
    }
    val checkingAccount: Account =new Account with CheckingInterestEarner
      with CheckingName with MockTransactionStore
    val bill: Customer = new Customer("Bill").openAccount(checkingAccount)
    bank.addCustomer(bill)
    checkingAccount.deposit(1000.0)
    bank.totalInterestPaid should be(0.01)
  }

  it should "savings account" in {
    val bank: Bank = new Bank
    trait MockTransactionStore extends InMemoryTransactionStore  {
      this:Account=>
      override def currentDate:DateTime=DateProvider.getInstance.now.minusDays(360)
    }
    val savingsAccount: Account =new Account with SavingsInterestEarner
      with SavingsName with MockTransactionStore
    bank.addCustomer(new Customer("Bill").openAccount(savingsAccount))
    savingsAccount.deposit(2000.0)
    bank.totalInterestPaid should be(4.0)
  }

  it should "maxi savings account" in {
    val bank: Bank = new Bank
    trait MockTransactionStore extends InMemoryTransactionStore {
      this:Account=>
      override def currentDate:DateTime=DateProvider.getInstance.now.minusDays(360)
    }
    val checkingAccount: Account =new Account with MaxiSavingsInterestEarner
      with MaxiSavingsName with MockTransactionStore
    bank.addCustomer(new Customer("Bill").openAccount(checkingAccount))
    checkingAccount.deposit(100.0)
    bank.totalInterestPaid should be(5.0)
  }

}
