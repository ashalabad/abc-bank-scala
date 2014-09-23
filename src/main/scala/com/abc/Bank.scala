package com.abc

import scala.collection.mutable.ListBuffer

class Bank {
  import com.abc.ext.Helpers._
  private val _customers= new ListBuffer[Customer]

  def customers:Iterable[Customer]=_customers.toIterable

  def addCustomer(customer: Customer) {
    _customers += customer
  }

  def customerSummary: String = {
    var summary: String = "Customer Summary"
    for (customer <- customers)
      summary = summary + "\n - " + customer.name + " (" + customer.numberOfAccounts.pluralize("account") + ")"
    summary
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


