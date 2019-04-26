
import play.api.libs.json._

val jsonString ="""{"appId":"123","ruleId":"123","name":"session","datatype":"string","rule":{"comparision":"knolx","point":"50","datatype":"string"}}"""

val jsonObject = Json.parse(jsonString)

sealed trait DataTypes

case object StringType extends DataTypes

case object BooleanType extends DataTypes

case object NumberType extends DataTypes

case object NoneType extends DataTypes

case class RuleInfo(appId: String, ruleId: String, name: String, datatype:String, rule: MyTypes)

object RuleInfo{
  implicit val ReadRule: Reads[RuleInfo] = Json.reads[RuleInfo]
}
trait MyTypes
object MyTypes{
  implicit val ReadType: Reads[MyTypes] = Json.reads[MyTypes]


}

case class RuleString(comparision: String, point:String, datatype:String) extends MyTypes

object RuleString{
  implicit val ReaddString: Reads[RuleString] = Json.reads[RuleString]
}

case class RuleBoolean(comparision: String, point: Int, datatype:String) extends MyTypes

object RuleBoolean
{
  implicit val ReadBoolean: Reads[RuleBoolean] = Json.reads[RuleBoolean]
}

case class RuleNumber(comparision: String, point: Int, datatype: String) extends MyTypes
object RuleNumber{
  implicit val ReadNumber: Reads[RuleNumber] = Json.reads[RuleNumber]
}

case class RuleNone(point: Int, datatype: String) extends MyTypes
object RuleNone{
  implicit val ReadNone: Reads[RuleNone] = Json.reads[RuleNone]
}




val jsonO= Json.parse(jsonString).as[RuleInfo]



