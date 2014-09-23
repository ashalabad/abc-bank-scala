package com.abc

import scala.collection.mutable.ListBuffer

class Customer(val name: String, private val _accounts: ListBuffer[Account] = ListBuffer()) {

  def accounts:Iterable[Account]=_accounts.toIterable
  def openAccount(account: Account): Customer = {
    _accounts += account
    this
  }
  def transfer(from:Account,to:Account,amount:Double)=to.deposit(math.abs(from.withdraw(amount).amount))
  def balance:Double=_accounts.map(_.balance).sum
  def numberOfAccounts: Int = _accounts.size
  def totalInterestEarned: Double = _accounts.map(_.interestEarned).sum
}

