import play.api.libs.json.Json

val jsonString ="""{"appId":"123","ruleID":"123","name":"session","datatype":"string","rule":{"comparision":"meetup":"50"}}"""

val jsonObject = Json.parse(jsonString)

sealed trait DataTypes

case object StringType extends DataTypes

case object BooleanType extends DataTypes

case object NumberType extends DataTypes

case object NoneType extends DataTypes


case class RuleInfo(appId: String, ruleId: String, name: String, datatype: DataTypes, rule: MyTypes)
{
  require(appId.nonEmpty, "AppId should be not Null")
  require(ruleId.nonEmpty, "RuleId should be not Null")
  require(name.nonEmpty, "NAME Should not be  EMPTY")
  require(datatype == StringType || datatype == NumberType || datatype == BooleanType || datatype == NoneType, "Data type is incorrect")
  require(datatype == StringType && rule.isInstanceOf[RuleString] || datatype == BooleanType && rule.isInstanceOf[RuleBoolean] || datatype == NumberType && rule.isInstanceOf[RuleNumber] || datatype == NoneType && rule.isInstanceOf[RuleNone], "Please USe correct rule for the given datatype")
}

trait MyTypes

case class RuleString(comparision: String, point: Int, valueToMatch: DataTypes) extends MyTypes {
  require(comparision.nonEmpty, "comparision should be not Null")
  require(comparision.equalsIgnoreCase("knolx") || comparision.equalsIgnoreCase("knolmeet"), "it should be knolx or knolmeet")
  require(valueToMatch == StringType, "Data type should be String type")
}

case class RuleBoolean(comparision: String, point: Int, valueToMatch: DataTypes) extends MyTypes {
  require(comparision.nonEmpty, "comparision should be not Null")
  require(comparision == "true" || comparision == "false", "value is either true or false")
  require(valueToMatch == BooleanType, "Data type should be boolean type")
}

case class RuleNumber(comparision: String, point: Int, valueToMatch: DataTypes) extends MyTypes {
  require(valueToMatch == NumberType, "Data type Should be number type")
}

case class RuleNone(point: Int, valueToMatch: DataTypes) extends MyTypes {
  require(valueToMatch == NoneType, "Data type should be None")
}




RuleInfo("dfcsdfw3", "1223dsds", "session", StringType, RuleString("Knolx", 50, StringType))




