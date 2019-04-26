package com.knoldus

import akka.{Done, NotUsed}
import com.knoldus.command.AddProduct
import com.knoldus.entity.ProductEntity
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.transport.{ExceptionMessage, NotFound, TransportErrorCode}
import com.lightbend.lagom.scaladsl.persistence.PersistentEntityRegistry
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraSession
import javax.inject.Inject
import org.slf4j.{Logger, LoggerFactory}
import play.api.Configuration

import scala.concurrent.ExecutionContext


class ProductServiceImpl(persistentEntityRegistry: PersistentEntityRegistry, session: CassandraSession)(implicit ec: ExecutionContext) extends ProductService {

  private final val log: Logger = LoggerFactory.getLogger(classOf[ProductServiceImpl])

  override def addProduct: ServiceCall[Products, Done] =
    ServiceCall { request =>
      persistentEntityRegistry
        .refFor[ProductEntity](request.id).ask(AddProduct(request))
        .map(_ => {
          log.info(s"Product with product id ${request.id}, " +
            s"successfully added. ")
          Done
        })
    }


  override def getProduct(id: String): ServiceCall[NotUsed, Products] =
    ServiceCall { _ =>
      session.selectOne("SELECT * FROM PRODUCT WHERE id =?", id).map {
        case Some(row) => Products(row.getString("id"), row.getString("name"))
        case None => throw new NotFound(TransportErrorCode.NotFound, new ExceptionMessage("Product Id Not Found",
          "Product with this product id does not exist"))
      }
    }


}
