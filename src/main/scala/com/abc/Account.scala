package com.abc

import org.joda.time.DateTime

import scala.collection.mutable.ListBuffer

/**
 * Account Builder
 */
object Account {
  def CHECKING: Account = new Account with CheckingInterestEarner with CheckingName with ConcreteDateProvider
  def SAVINGS: Account = new Account with SavingsInterestEarner with SavingsName with ConcreteDateProvider
  def MAXI_SAVINGS: Account = new Account with MaxiSavingsInterestEarner with MaxiSavingsName with ConcreteDateProvider
}

/**
 * Abstract Account class
 * Account Builder assembles an account with an interest earner,name and transaction date generator
 * @param _transactions
 */
abstract class Account(private val _transactions: ListBuffer[Transaction] = ListBuffer()) {

  def name:String
  def transactions:Iterable[Transaction]=_transactions.toIterable

  def deposit(amount: Double):Transaction={
    if (amount <= 0)
      throw new IllegalArgumentException("amount must be greater than zero")
    val trx=Transaction(currentDate, amount)
    _transactions += trx
    trx
  }

  def withdraw(amount: Double):Transaction={
    if (amount <= 0)
      throw new IllegalArgumentException("amount must be greater than zero")
    if(sumTransactions(true)<amount)
      throw new IllegalArgumentException("not enough funds")
    val trx=Transaction(currentDate,-amount)
    _transactions += trx
    trx
  }
  def balance:Double=sumTransactions(true)
  protected def currentDate:DateTime
  def interestEarned: Double
  def sumTransactions(checkAllTransactions: Boolean = true): Double = _transactions.map(_.amount).sum
}