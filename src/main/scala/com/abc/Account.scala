package com.abc

import org.joda.time.DateTime

import scala.collection.mutable.ListBuffer

/**
 * Account Builder
 */
object Account {
  def CHECKING: Account = new Account with CheckingInterestEarner with CheckingName
    with InMemoryTransactionStore with TransactionDateProvider
  def SAVINGS: Account = new Account with SavingsInterestEarner with SavingsName
    with InMemoryTransactionStore with TransactionDateProvider
  def MAXI_SAVINGS: Account = new Account with MaxiSavingsInterestEarner with MaxiSavingsName
    with InMemoryTransactionStore with TransactionDateProvider
}

/**
 * Abstract Account class
 */
abstract class Account {
  import com.abc.ext.Helpers._
  def name:String
  def transactions:Iterable[Transaction]

  def deposit(amount: Double):Transaction={
    amount.positive()
    addTransaction(amount)
  }

  def withdraw(amount: Double):Transaction={
    amount.positive()
    if(sumTransactions(true)<amount)
      throw new IllegalArgumentException("insufficient funds")
    addTransaction(-amount)
  }
  def balance:Double=sumTransactions(true)
  def interestEarned: Double
  def sumTransactions(checkAllTransactions: Boolean = true): Double = transactions.map(_.amount).sum
  protected def addTransaction(amount:Double):Transaction
}