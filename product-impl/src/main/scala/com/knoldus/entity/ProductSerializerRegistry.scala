package com.knoldus.entity

import com.knoldus.command.AddProduct
import com.knoldus.event.ProductAdded
import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}

object ProductSerializerRegistry extends JsonSerializerRegistry {
  override def serializers = List(
    JsonSerializer[AddProduct],
    JsonSerializer[ProductAdded]
  )
}
