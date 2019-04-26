package com.knoldus.event

import akka.Done
import com.datastax.driver.core.{BoundStatement, PreparedStatement}
import com.knoldus.Products
import com.lightbend.lagom.scaladsl.persistence.cassandra.{CassandraReadSide, CassandraSession}
import com.lightbend.lagom.scaladsl.persistence.{AggregateEventTag, ReadSideProcessor}

import scala.concurrent.{ExecutionContext, Future}

class ProductEventReadSideProcessor(db: CassandraSession, readSide: CassandraReadSide)(implicit ec: ExecutionContext) extends ReadSideProcessor[ProductEvent] {

  private var insertProduct: PreparedStatement = _

  override def buildHandler() = readSide.builder[ProductEvent]("ProductEventReadSideProcessor")
    .setGlobalPrepare(createTable)
    .setPrepare(_ => prepareStatements())
    .setEventHandler[ProductAdded](ese => insertProduct(ese.event.product))
    .build()

  private def createTable(): Future[Done] = {
    db.executeCreateTable(
      """CREATE TABLE IF NOT EXISTS product.product(
        |id text PRIMARY KEY, name text)""".stripMargin)
  }

  private def prepareStatements(): Future[Done] =
    db.prepare("INSERT INTO product.product(id,name) VALUES(?, ?)")
      .map { ps =>
        insertProduct = ps
        Done
      }

  private def insertProduct(product: Products): Future[List[BoundStatement]] = {
    val bindInsertProduct: BoundStatement = insertProduct.bind()
    bindInsertProduct.setString("id", product.id)
    bindInsertProduct.setString("name", product.name)
    Future.successful(List(bindInsertProduct))
  }

  override def aggregateTags: Set[AggregateEventTag[ProductEvent]] = ProductEvent.Tag.allTags
}
