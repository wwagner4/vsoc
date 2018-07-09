package vsoc.ga.common

object UtilReflection {

  import scala.reflect.runtime.universe._

  /**
    * Calls a method with no parameters that return T
    */
  def call[T <: AnyRef](obj: Any, name: String, rc: Class[T]): T = {
    try {
      val r = scala.reflect.runtime.currentMirror.reflect(obj)
      val m = r.symbol.typeSignature.member(TermName(name))
      r.reflectMethod(m.asMethod)().asInstanceOf[T]
    } catch {
      case e: ScalaReflectionException =>
        val cl = obj.getClass.getName
        val msg = e.getMessage
        throw new IllegalStateException(s"Error calling method '$name' on $cl. $msg")
    }
  }

  def allPropValues(obj: AnyRef): Seq[Any] = {
    val mirror = scala.reflect.runtime.currentMirror
    val accessors = mirror.classSymbol(obj.getClass).toType.members.collect {
      case m: MethodSymbol if m.isGetter && m.isPublic => m
    }
    val r = mirror.reflect(obj)
    accessors.map(a => {
      val b = a.asMethod
      r.reflectMethod(b).apply()
    }).toSeq.reverse
  }

  def allPropNames(obj: AnyRef): Seq[Any] = {
    val mirror = scala.reflect.runtime.currentMirror
    val accessors = mirror.classSymbol(obj.getClass).toType.members.collect {
      case m: MethodSymbol if m.isGetter && m.isPublic => m
    }
    accessors.map(a => a.name.toString).toSeq.reverse
  }


}
