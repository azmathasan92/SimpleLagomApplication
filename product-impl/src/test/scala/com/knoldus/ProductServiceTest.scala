package com.knoldus

import akka.Done
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraSession
import com.lightbend.lagom.scaladsl.server.LocalServiceLocator
import com.lightbend.lagom.scaladsl.testkit.ServiceTest
import org.scalatest.{AsyncWordSpec, BeforeAndAfterAll, Matchers}

import scala.concurrent.Await
import scala.concurrent.duration._

class ProductServiceTest extends AsyncWordSpec with Matchers with BeforeAndAfterAll {


  lazy val server = ServiceTest.startServer(ServiceTest.defaultSetup.withCassandra(true)) { ctx =>
    new ProductApplication(ctx) with LocalServiceLocator
  }

  lazy val client = server.serviceClient.implement[ProductService]

  override protected def beforeAll(): Unit = {
    server
    val session: CassandraSession = server.application.cassandraSession


    createSchema(session)

    populateData(session)

  }

  private def createSchema(session: CassandraSession): Unit = {

    //Create Keyspace
    val createKeyspace = session.executeWrite("CREATE KEYSPACE IF NOT EXISTS product WITH replication = {'class': 'SimpleStrategy','replication_factor': 1};")
    Await.result(createKeyspace, 20 seconds)

    //Create table
    val createTable = session.executeCreateTable(
      """CREATE TABLE IF NOT EXISTS product (
        |id text PRIMARY KEY, name text)""".stripMargin)
    Await.result(createTable, 20.seconds)
  }

  private def populateData(session: CassandraSession): Unit = {
    val product = Products("1", "Mobile")
    val insertProduct = session.executeWrite("INSERT INTO product (id, name) VALUES (?, ?)", product.id,
      product.name)
    Await.result(insertProduct, 20.seconds)
  }

  override protected def afterAll() = server.stop()


  "Product service" should {
    val product = Products("1", "Mobile")
    "should get product by id" in {
      client.getProduct("1").invoke().map { response =>
        response should ===(product)

      }
    }
  }

  "Product service" should {
    val product = Products("2", "Bat")
    "should add a new Product" in {
      client.addProduct().invoke(product).map { response =>
        response should ===(Done)

      }
    }
  }


}
