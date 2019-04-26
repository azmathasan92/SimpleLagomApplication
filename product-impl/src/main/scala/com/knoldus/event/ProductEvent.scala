package com.knoldus.event
import com.knoldus.Products
import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventShards, AggregateEventTag}
import play.api.libs.json.{Format, Json}

sealed trait ProductEvent extends AggregateEvent[ProductEvent] {
  override def aggregateTag: AggregateEventShards[ProductEvent] = ProductEvent.Tag
}

object ProductEvent {
  val NumShards = 3
  val Tag: AggregateEventShards[ProductEvent] = AggregateEventTag.sharded[ProductEvent](NumShards)
}


case class ProductAdded(product: Products) extends ProductEvent

object ProductAdded {
  implicit val format: Format[ProductAdded] = Json.format[ProductAdded]

}

