package com.abc

import java.util.Calendar

import org.joda.time.DateTime
import org.scalatest.{BeforeAndAfterEach, BeforeAndAfter, Matchers, FlatSpec}

import scala.concurrent._
import scala.concurrent.duration._
import ExecutionContext.Implicits.global

/**
 * Created by igor on 9/21/14.
 */
class AccountTest extends FlatSpec with Matchers with BeforeAndAfterEach {

  it should "create correct checking account through account builder" in {
    val acc=Account.CHECKING
    acc.isInstanceOf[CheckingInterestEarner] should be(true)
  }

  it should "create correct savings account through account builder" in {
    val acc=Account.SAVINGS
    acc.isInstanceOf[SavingsInterestEarner] should be(true)
  }

  it should "create correct maxi savings account through account builder" in {
    val acc=Account.MAXI_SAVINGS
    acc.isInstanceOf[MaxiSavingsInterestEarner] should be(true)
  }

  it should "not allowed deposit with negative amount" in {
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


  ignore should "hold correct balance for concurrent withdraw operations" in {
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
