import play.api.libs.json._

val jsonString = """{"v":{"knolx":"50"}}"""

case class RuleInfo(appId: String, ruleId: String, name: String, datatype: String, rule: MyTypes)

trait MyTypes

case class RuleString(comparision: String, point: Int, valueToMatch: String) extends MyTypes

case class RuleBoolean(point: Int, valueToMatch: Boolean) extends MyTypes

case class RuleNumber(comparision: String, point: Int, valueToMatch: String) extends MyTypes

case class RuleNone(point: Int) extends MyTypes





  implicit val dataTypeReads: Reads[DataTypes] {
    def reads(json: JsValue): JsResult[DataTypes]
  } = new Reads[DataTypes] {
    def reads(json: JsValue): JsResult[DataTypes] = json match {
      case JsString(typ) => typ match {
        case "number" => JsSuccess(DataTypes.NumberType)
        case "boolean" => JsSuccess(DataTypes.BooleanType)
        case "string" => JsSuccess(DataTypes.StringType)
        case "none"=>JsSuccess(DataTypes.NoneType)
        case _ => JsError(Seq(JsPath() -> Seq(JsonValidationError("error.invalid data types"))))
      }
      case _ => JsError(Seq(JsPath() -> Seq(JsonValidationError("error.invalid"))))
    }
  }


}

case class Test(v: DataTypes)

object Test {

  implicit val ReadTest = Json.reads[Test]
}

val tt = Json.parse("""{"v":"hhfjd"}""").as[Test]














/*implicit object DataTypeReads extends Reads[DataTypes] {

  def reads(json: JsValue): JsResult[DataTypes] = json match {

    case JsString(dataTypes) => if (dataTypes == "string") JsSuccess(StringType)
    else if (dataTypes == "number") JsSuccess(NumberType)
    else if (dataTypes == "boolean") JsSuccess(BooleanType)
    else if (dataTypes == "none") JsSuccess(NoneType)
    else JsError(Seq(JsPath() -> Seq(JsonValidationError("error.invalid.Type"))))

    case _ => JsError(Seq(JsPath() -> Seq(JsonValidationError("error.invalid"))))
  }
}*/


//val jsonString = """{"appId":"123dda","ruleId":"123hgsa","name":"Session","datatype":"string","value":{"true":"50", "false":"0"}}"""




/*


// the trait and extending classes/objects
sealed trait Car
case object MercedesBenz extends Car
case class NormalCar(model: String) extends Car

// the reader for the trait
implicit object CarReads extends Reads[Car] {
  // as in the other example, you need to define
  def reads(json: JsValue): JsResult[Car] = json match {
    // a car can be a single json string
    case JsString(s) => if (s == "mercedes") JsSuccess(MercedesBenz) else JsError(Seq(JsPath() -> Seq(JsonValidationError("error.invalid"))))
    // or a json object with a "model" attribute
    case JsObject(obj) => obj.get("model") match {
      case Some(model) => model match {
        case JsString(str) => JsSuccess(NormalCar(str))
        case _ => JsError(Seq(JsPath() \ "model" -> Seq(JsonValidationError("error.expected.js.string"))))
      }
      case None => JsError(Seq(JsPath() -> Seq(JsonValidationError("error.expected.car"))))
    }
    case _ => JsError(Seq(JsPath() -> Seq(JsonValidationError("error.invalid"))))
  }
}
*/



