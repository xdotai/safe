package ai.x.safe
import scala.collection._
import scala.annotation.implicitNotFound
package object conversions{
  implicit val intSafeToString: SafeToString[Int] = new SafeToString[Int]{
    @inline def safeToString(s: Int): String = s.toString
  }
  implicit val longSafeToString: SafeToString[Long] = new SafeToString[Long]{
    @inline def safeToString(s: Long): String = s.toString
  }
  import scala.language.implicitConversions
  implicit def implicitToString[T](value: T)(implicit safeToString: SafeToString[T]) =
     safeToString.safeToString(value)
}
object `package`{
  implicit class SafeEquals[T](val l: T) extends AnyVal{
    @inline def ===(r: T): Boolean = l == r
    @inline def !==(r: T): Boolean = l != r
  }

  implicit class SafeSeqContains[A, Repr](val coll: SeqLike[A, Repr]) extends AnyVal{
    @inline def safeContains(t: A): Boolean = coll.contains(t)
  }

  // Set.contains is already type-safe, so this is only here for access to consistent names
  implicit class SafeSetContains[A, This <: scala.collection.SetLike[A,This] with scala.collection.Set[A]](val coll: SetLike[A, This]) extends AnyVal{
    @inline def safeContains(t: A): Boolean = coll.contains(t)
  }

  @implicitNotFound("""Could not automatically convert ${T} to String. Did you mean to convert? If yes, consider converting manually or implementing a SafeToString[${T}] type class instance or making ${T} extend trait SafeString.""")
  trait SafeToString[T]{
    @inline def safeToString(value: T): String
  }
  trait SafeString{
    def safeToString: String 
  }
  object SafeToString{
    @inline def apply[T:SafeToString]: SafeToString[T] = implicitly[SafeToString[T]]
    implicit val StringSafeToString: SafeToString[String] = new SafeToString[String]{
      @inline def safeToString(s: String): String = s
    }
    implicit val SafeStringSafeToString: SafeToString[SafeString] = new SafeToString[SafeString]{
      @inline def safeToString(s: SafeString): String = s.safeToString
    }
  }

  implicit class SafeSafeToString[T](val value: T) extends AnyVal{
    @inline def safeToString(implicit toString: SafeToString[T]): String = toString.safeToString( value )
  }

  implicit class SafeStringAdd[L](val s: L) extends AnyVal{
    @inline def ~[R](v: R)(implicit lToString: SafeToString[L], rToString: SafeToString[R]): String = s.safeToString + v.safeToString
  }

  implicit class SafeMkString[T](val coll: GenTraversableOnce[T]) extends AnyVal{
    @inline def safeMkString(start: String, sep: String, end: String)(implicit toString: SafeToString[T]): String =
      coll.toIterable.map(_.safeToString).mkString(start, sep, end)
    @inline def safeMkString(sep: String)(implicit toString: SafeToString[T]): String = coll.toIterable.map(_.safeToString).mkString(sep)
    @inline def safeMkString(implicit toString: SafeToString[T]): String = coll.toIterable.map(_.safeToString).mkString
  }

  implicit class SafeStringContext( val stringContext: StringContext ) extends AnyVal{
    def safe[T]( args: T* )(implicit toString: SafeToString[T]): String = {
      val process = StringContext.treatEscapes _
      val pi = stringContext.parts.iterator
      val ai = args.iterator
      val bldr = new java.lang.StringBuilder( process( pi.next() ) )
      while ( ai.hasNext ) {
        bldr append toString.safeToString( ai.next )
        bldr append process( pi.next() )
      }
      bldr.toString
    }
  }
}
