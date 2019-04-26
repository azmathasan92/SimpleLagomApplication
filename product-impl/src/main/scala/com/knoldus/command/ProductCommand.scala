package com.knoldus.command

import akka.Done
import com.knoldus.Products
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import play.api.libs.json.{Format, Json}

sealed trait ProductCommand[R] extends ReplyType[R]

case class AddProduct(product: Products) extends ProductCommand[Done]

object AddProduct {
  implicit val format: Format[AddProduct] = Json.format[AddProduct]
}
