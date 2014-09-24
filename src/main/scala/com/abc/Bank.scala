package com.abc

import scala.collection.mutable.ListBuffer

class Bank {
  private val _customers= new ListBuffer[Customer]

  def customers:Iterable[Customer]=_customers.toIterable

  def addCustomer(customer: Customer) {
    _customers += customer
  }

  def totalInterestPaid: Double = {
    var total: Double = 0
    for (c <- customers) total += c.totalInterestEarned
    return total
  }

  def getFirstCustomer: String = {
    customers match {
      case x::Nil=>x.name
      case x::xs=>x.name
      case Nil=>""
    }
  }

}


