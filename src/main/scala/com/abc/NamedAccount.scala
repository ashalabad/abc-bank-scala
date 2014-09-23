package com.abc

/**
 * Created by igor on 9/22/14.
 */
trait NamedAccount {
  this:Account=>
}

trait CheckingName extends NamedAccount {
  this:Account=>
  def name="Checking Account"
}
trait SavingsName extends NamedAccount {
  this:Account=>
  def name="Savings Account"
}
trait MaxiSavingsName extends NamedAccount {
  this:Account=>
  def name="Maxi Savings Account"
}