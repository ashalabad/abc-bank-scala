package com.abc.report

import com.abc.Bank

/**
 * Created by igor on 9/23/14.
 */
class BankReportGenerator {
  import com.abc.ext.Helpers._
  def summary(b:Bank):String= {
    var summary: String = "Customer Summary"
    for (customer <- b.customers)
      summary = summary + "\n - " + customer.name + " (" + customer.numberOfAccounts.pluralize("account") + ")"
    summary
  }
}
