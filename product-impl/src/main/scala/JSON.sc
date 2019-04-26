

import play.api.libs.json._

val jsonString ="""{"appId":"123","ruleID":"123","name":"session","datatype":"string","rule":{"meetup":"50"}}"""

val jsonObject = Json.parse(jsonString)


val json="""{"name":"azmat","age":{"name1":"azmathasan","age1":"12"}}"""


case class Test(name:String,age:test2)
object Test
{
  implicit val ReadTest: Reads[Test] = Json.reads[Test]
}

trait test2

case class Test1(name1:String,age1:String) extends test2
object Test1{
  implicit val ReadTest1: Reads[Test1] = Json.reads[Test1]
}
case class Test3(name1:String) extends test2
object Test3{
  implicit val ReadTest1: Reads[Test1] = Json.reads[Test1]
}



implicit object DataTypeReads extends Reads[test2] {

  def reads(json: JsValue): JsResult[test2] = json match {

    case JsString(dataTypes) => if (Json.parse(dataTypes).as[Test1]) JsSuccess(Test1)
    else JsError(Seq(JsPath() -> Seq(JsonValidationError("error.invalid.Type"))))

    case _ => JsError(Seq(JsPath() -> Seq(JsonValidationError("error.invalid"))))
  }
}






val jsonO= Json.parse(json).as[Test]










