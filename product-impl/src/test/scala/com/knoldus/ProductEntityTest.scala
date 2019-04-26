package com.knoldus

import akka.actor.ActorSystem
import akka.testkit.TestKit
import com.knoldus.command.{AddProduct, ProductCommand}
import com.knoldus.entity.{ProductEntity, ProductSerializerRegistry}
import com.knoldus.event.{ProductAdded, ProductEvent}
import com.lightbend.lagom.scaladsl.playjson.JsonSerializerRegistry
import com.lightbend.lagom.scaladsl.testkit.PersistentEntityTestDriver
import org.scalatest.{AsyncWordSpec, BeforeAndAfterAll, Matchers, OptionValues}

class ProductEntityTest extends AsyncWordSpec with Matchers with BeforeAndAfterAll with OptionValues {

  val system = ActorSystem("ProductEntityTest", JsonSerializerRegistry.actorSystemSetupFor(ProductSerializerRegistry))

  val product = Products("1", "Mobile")

  override protected def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  private def withDriver[T](block: PersistentEntityTestDriver[ProductCommand[_], ProductEvent, _] => T): T = {
    val driver = new PersistentEntityTestDriver(system, new ProductEntity, "product-1")
    try {
      block(driver)
    } finally {
      driver.getAllIssues shouldBe empty
    }
  }

  "The product entity" should {
    "allow creating a product" in withDriver { driver =>
      val outcome = driver.run(AddProduct(product))
      outcome.events should contain only ProductAdded(product)
    }

  }
}
