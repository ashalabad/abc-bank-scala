package com.abc.ext

/**
 * Extension methods helper classes
 */
object Helpers {

  implicit class ExtensionDouble(val value: Double) extends AnyVal {
    /**
     * Format a string to a dollar string
     * @return
     */
    def toDollars: String = f"$$$value%.2f"

    /**
     * Calculate a daily interest
     * @param yearRate    yearly % rate
     * @param days        days over which the interest was accumulated
     * @param daysInYear  days in the year, default is 360
     * @return interest accumulated
     */
    def dailyInterest(yearRate:Double,days:Int,daysInYear:Int=360):Double={
      val rate:Double=yearRate/100.0
      (value*(rate/daysInYear))*days
    }

    def positive(message:String="amount must be greater than zero"):Unit={
      if(value<=0)
        throw new IllegalArgumentException(message)
    }
  }

  implicit class ExtensionInt(val value: Int) extends AnyVal {
    def pluralize(word: String): String = {
      value + " " + (if (value == 1) word else word + "s")
    }
  }

}
