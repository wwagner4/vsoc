package vsoc.ga.common.describe

case class DA(a: String, b: Double) extends Describable with PropertiesProvider {

  override def fullDesc: String = {
    val props: String = DescribableFormatter.format(properties, 0)
    s"""The class DA for testing
       |Some description of DA
       |$props
       |Some description of DA comming after the props
    """.stripMargin
  }

  override def properties: Seq[(String, Any)] = Seq(
    ("ASTRING", a),
    ("DVAL", b),
    ("CONST", "A_CONSTANT_VALUE"),
    ("AKEYLONGERTHANTEN", "A key longer than 10"),
    ("DB", DB(123456)),
  )
}

case class DB(b: Double) extends Describable with PropertiesProvider {

  override def fullDesc: String = {
    val props: String = DescribableFormatter.format(properties, 0)
    s"""The class DB also for testing
       |Some description of DB
       |$props
       |----------------------------------------------
       |- MR: Mutation Rate
       |Some description of DB comming after the props
    """.stripMargin
  }

  override def properties: Seq[(String, Any)] = Seq(
    ("A", 12000),
    ("B", 7.4f),
    ("C", 33333.333333),
    ("MR", 0.0001),
  )
}

