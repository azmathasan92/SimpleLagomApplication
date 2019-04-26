package com.knoldus

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}

trait ProductService extends Service{

  def addProduct(): ServiceCall[Products, Done]
  def getProduct(id:String):ServiceCall[NotUsed,Products]

  override def descriptor: Descriptor = {
    import Service._
    named("product")
      .withCalls(
        restCall(Method.POST,"/products/add/product", addProduct _),
        restCall(Method.GET,"/products/get/product/:id",getProduct _)
      )
      .withAutoAcl(true)
  }
}
