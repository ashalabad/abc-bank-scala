package com.abc

import org.joda.time.DateTime

/**
 * Created by igor on 9/22/14.
 */
trait AccountDateProvider {
  this:Account=>
}

trait ConcreteDateProvider extends AccountDateProvider {
  this:Account=>
  def currentDate:DateTime=DateProvider.getInstance.now
}