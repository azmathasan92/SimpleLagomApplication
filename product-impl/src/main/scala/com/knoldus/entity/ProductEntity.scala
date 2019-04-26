package com.knoldus.entity

import akka.{Done, NotUsed}
import com.knoldus.command.{AddProduct, ProductCommand}
import com.knoldus.event.{ProductAdded, ProductEvent}
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity


class ProductEntity extends PersistentEntity {
  override type Command = ProductCommand[_]
  override type Event = ProductEvent
  override type State = NotUsed

  override def initialState: NotUsed.type = NotUsed

  override def behavior: Actions =
    Actions()
      .onCommand[AddProduct, Done] {
      case (AddProduct(product), ctx, _) =>
        val event: ProductEvent = ProductAdded(product)
        ctx.thenPersist(event) { _ =>
          ctx.reply(Done)
        }
    }.onEvent {
      case (_, state) =>
        state
    }

}
