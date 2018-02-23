package vsoc.ga.common

object UtilReflection {

  import scala.reflect.runtime.universe._

  /**
    * Calls a method with no parameters that return T
    */
  def call[T](obj: Any, name: String, rc: Class[T]): T = {
    val r = runtimeMirror(getClass.getClassLoader).reflect(obj)
    val m = r.symbol.typeSignature.member(TermName(name))
    r.reflectMethod(m.asMethod)().asInstanceOf[T]
  }

}
