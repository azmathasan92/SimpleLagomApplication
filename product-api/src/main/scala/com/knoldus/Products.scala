package com.knoldus

import play.api.libs.json.{Format, Json}

case class Products(id: String, name: String)

object Products {
  implicit val format: Format[Products] = Json.format
}
