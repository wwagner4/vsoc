package vsoc.ga.common.describe

trait Describable {

  /**
    * @return A short single line description
    */
  def shortDesc: String

  /**
    * @return A complete multiple line description
    */
  def fullDesc: String

}
