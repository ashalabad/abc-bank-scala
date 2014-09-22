package com.abc

import org.joda.time.DateTime

/**
 * Simple case class that includes
 * @param transactionDate
 * @param amount
 */
case class Transaction( val transactionDate:DateTime, val amount: Double) {
}

