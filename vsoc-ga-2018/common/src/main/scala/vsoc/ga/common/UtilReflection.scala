package vsoc.ga.common

object UtilReflection {

  import scala.reflect.runtime.universe._

  /**
    * Calls a method with no parameters that return T
    */
  def call[T](obj: Any, name: String, rc: Class[T]): T = {
    try {
      val r = runtimeMirror(getClass.getClassLoader).reflect(obj)
      val m = r.symbol.typeSignature.member(TermName(name))
      r.reflectMethod(m.asMethod)().asInstanceOf[T]
    } catch {
      case e: ScalaReflectionException =>
        val cl = obj.getClass.getName
        val msg = e.getMessage
        throw new IllegalStateException(s"Error calling method '$name' on $cl. $msg")
    }
  }

}
