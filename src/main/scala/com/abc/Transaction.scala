package com.abc

import org.joda.time.DateTime

import scala.collection.mutable.ListBuffer

/**
 * Simple case class that includes
 * @param transactionDate
 * @param amount
 */
case class Transaction( val transactionDate:DateTime, val amount: Double)


trait TransactionStore {
  this:Account=>
  def currentDate:DateTime
}

/**
 * Simple in-memory implementation of Account Transaction Store
 */
trait InMemoryTransactionStore extends TransactionStore {
  this:Account=>
  private val _transactions:ListBuffer[Transaction]=new ListBuffer[Transaction]
  def transactions:Iterable[Transaction] = _transactions.toIterable
  def addTransaction(amount:Double):Transaction={
    val trx=Transaction(currentDate,amount)
    _transactions+=trx
    trx
  }
}

trait TransactionDateProvider {
  this:TransactionStore=>
  def currentDate=DateProvider.getInstance.now
}