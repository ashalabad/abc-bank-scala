package com.abc.ext

/**
 * Extension methods helper classes
 */
object Helpers {

  implicit class ExtensionDouble(val value: Double) extends AnyVal {
    def toDollars: String = f"$$$value%.2f"
  }

  implicit class ExtensionInt(val value: Int) extends AnyVal {
    def pluralize(word: String): String = {
      value + " " + (if (value == 1) word else word + "s")
    }
  }

}
