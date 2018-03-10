package vsoc.ga.common.describe

trait PropertiesProvider {

  /**
    *
    * @return A sequence of properties describing the implementing class
    */
  def properties: Seq[(String, Any)]

  lazy val propsFmt: String = DescribableFormatter.format(properties, 0)

}
