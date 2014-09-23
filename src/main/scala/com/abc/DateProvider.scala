package com.abc

import java.util.Calendar
import org.joda.time.DateTime

object DateProvider {
  def getInstance: DateProvider = {
    if (instance == null) instance = new DateProvider
    instance
  }

  private var instance: DateProvider = null
}

class DateProvider {
  def now: DateTime = {
    return new DateTime(Calendar.getInstance.getTime)
  }
}



