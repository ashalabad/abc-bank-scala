package com.abc

import org.scalatest.{Matchers, FlatSpec}

import scala.concurrent._
import scala.concurrent.duration._
import ExecutionContext.Implicits.global

/**
 * Created by igor on 9/21/14.
 */
class AccountTest extends FlatSpec with Matchers {

  "Account" should "not allowed deposit with negative amount" in {
    val acc=Account.CHECKING
    withClue("amount must be greater than zero") {
      intercept[IllegalArgumentException] {
        acc.deposit(-100.0)
      }
    }
  }

  it should "not allowed withdrawal with negative amount" in {
    val acc=Account.CHECKING
    withClue("amount must be greater than zero") {
      intercept[IllegalArgumentException] {
        acc.withdraw(-100.0)
      }
    }
  }

  it should "not allowed for deposit zero amount" in {
    val acc=Account.CHECKING
    withClue("amount must be greater than zero") {
      intercept[IllegalArgumentException] {
        acc.deposit(0.0)
      }
    }
  }

  it should "not allowed for withdraw of zero amount" in {
    val acc=Account.CHECKING
    withClue("amount must be greater than zero") {
      intercept[IllegalArgumentException] {
        acc.withdraw(0.0)
      }
    }
  }

  it should "not allowed for overdraw" in {
    val acc=Account.CHECKING
    acc.deposit(100.0)
    withClue("not enough funds") {
      intercept[IllegalArgumentException] {
        acc.withdraw(200.0)
      }
    }
  }


  it should "have correct list of transaction after deposit and withdraw operations" in {
    val testData=List(100.0,-30.0,10.0,-50.0)
    val acc=Account.CHECKING
    acc.deposit(100.0)
    acc.withdraw(30.0)
    acc.deposit(10.0)
    acc.withdraw(50.0)
    acc.transactions.size should be(4)
    (acc.transactions,testData).zipped foreach {
      (t,d)=> {
        t.amount should be(d)
      }
    }
  }

  it should "calculate correct balance" in {
    val acc=Account.CHECKING
    acc.deposit(100.0)
    acc.withdraw(30.0)
    acc.deposit(10.0)
    acc.withdraw(50.0)
    acc.sumTransactions() should be(30.0)

  }


  it should "calculate correct interest for Checking account" in {
    val acc=Account.CHECKING
    acc.deposit(1000.0)
    acc.interestEarned should be(1.0)
  }

  it should "calculate correct interest for Savings account with the amount < 1000" in {
    val acc=Account.SAVINGS
    acc.deposit(100.0)
    acc.interestEarned should be (0.1)
  }

  it should "calculate correct interest for Savings account with the amount == 1000" in {
    val acc=Account.SAVINGS
    acc.deposit(1000.0)
    acc.interestEarned should be (1.0)
  }

  it should "calculate correct interest for Savings account with the amount > 1000" in {
    val acc=Account.SAVINGS
    acc.deposit(2000.0)
    acc.interestEarned should be (3.0)
  }

  it should "calculate correct interest for Maxi Savings account < 1000" in {
    val acc=Account.MAXI_SAVINGS
    acc.deposit(100.0)
    acc.interestEarned should be (2.0)
  }

  it should "calculate correct interest for Maxi Savings account == 1000" in {
    val acc=Account.MAXI_SAVINGS
    acc.deposit(1000.0)
    acc.interestEarned should be (20.0)
  }

  it should "calculate correct interest for Maxi Savings account >1000 and < 2000" in {
    val acc=Account.MAXI_SAVINGS
    acc.deposit(1100.0)
    acc.interestEarned should be (25.0)
  }

  it should "calculate correct interest for Maxi Savings account ==2000" in {
    val acc=Account.MAXI_SAVINGS
    acc.deposit(2000.0)
    acc.interestEarned should be (70.0)
  }

  it should "calculate correct interest for Maxi Savings account >2000" in {
    val acc=Account.MAXI_SAVINGS
    acc.deposit(3000.0)
    acc.interestEarned should be (170.0)
  }

  it should "hold correct balance for concurrent withdraw operations" in {
    val acc=Account.CHECKING
    acc.deposit(10000.0)
    var count:Int=0
    // run 10 threads withdrawing 0.1 dollars each
    val tasks:Seq[Future[Unit]]=for(i<-1 to 10) yield Future {
      for(j<-1 to 1000) {
        try {
          acc.withdraw(1.0)
          count+=1
        }
        catch {
          case e:IllegalArgumentException=>{}
        }
      }
    }
    val aggregated: Future[Seq[Unit]] = Future.sequence(tasks)

    Await.result(aggregated,10.second)
    //Await.ready(aggregated,10.second)
    acc.sumTransactions() should be(0.0)
  }
}
