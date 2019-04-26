import play.api.libs.json._

sealed trait DataTypes

object DataTypes {

  case object StringType extends DataTypes

  case object BooleanType extends DataTypes

  case object NumberType extends DataTypes

  case object NoneType extends DataTypes

}

case class Data(v: DataTypes)

object Data {

  implicit val ReadTest: Reads[Data] = Json.reads[Data]
}

case class RuleInfo(appId: String, ruleId: String, name: String, datatype: String, rule: MyTypes)

object RuleInfo{
  implicit val ReadRuleInfo=Json.reads[RuleInfo]
}

sealed trait MyTypes

object MyTypes {

  case class RuleString(comparision: String, point: Int) extends MyTypes {
    val datatype = DataTypes.StringType
  }
  object RuleString
  {
    implicit val ReadRuleInfo=Json.reads[RuleString]
  }

  case class RuleBoolean(point: Int) extends MyTypes
  {
     val datatype= DataTypes.BooleanType
  }
  object RuleBoolean
  {
    implicit val ReadRuleInfo=Json.reads[RuleBoolean]
  }

  case class RuleNumber(comparision: String, point: Int) extends MyTypes
  {
    val datatype=DataTypes.NumberType
  }
  object RuleNumber{
    implicit val ReadRuleInfo=Json.reads[RuleNumber]
  }

  case class RuleNone(point: Int) extends MyTypes
  {
    val datatype=DataTypes.NoneType
  }
  object RuleNone {
    implicit val ReadRuleInfo = Json.reads[RuleBoolean]
  }

}

val jsonString ="""{"appId":"123","ruleID":"123","name":"session","datatype":"string","rule":{"meetup":"50"}}"""

val jsonObject = Json.parse(jsonString).as[RuleInfo]
