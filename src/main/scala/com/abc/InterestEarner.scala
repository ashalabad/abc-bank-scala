package com.abc

import com.abc.rules.{SavingsInterestCalculator, MaxiSavingsInterestCalculator, InterestCalculationRule, CheckingInterestCalculator}

/**
 * Base trait for interest earner
 */
trait InterestEarner {
  this:Account=>
  protected def calculator:InterestCalculationRule
  lazy val _calc=calculator
  override def interestEarned: Double = _calc.calculateInterest(transactions)
}


/**
 * Implementation of Checking Interest Earner trait
 */
trait CheckingInterestEarner extends InterestEarner {
  this:Account=>
  override def calculator: InterestCalculationRule = new CheckingInterestCalculator(0.001)
}

/**
 * Implementation of Savings Interest Earner trait
 */
trait SavingsInterestEarner extends InterestEarner {
  this:Account=>
  override def calculator: InterestCalculationRule = new SavingsInterestCalculator()
}

/**
 * Implementation of Maxi Savings Interest Earner trait
 */
trait MaxiSavingsInterestEarner extends InterestEarner {
  this:Account=>
  override def calculator: InterestCalculationRule = new MaxiSavingsInterestCalculator()
}
