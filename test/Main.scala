package ai.x.safe
package test
import conversions._
import shapeless.test.illTyped
object Main{
  def main( args: Array[String] ): Unit = {
    def foo[T](t: T) = assert( 1 != t ) // wrap in def to avoid warning
    foo("test")

    illTyped(""" 1 === "test" """)
    illTyped(""" (1: Any) === "test" """)
    assert(1 === 1)
    assert(1 !== 2)

    assert(
      !Seq(1).contains("test")
    )
    assert(
      Seq(1).contains(1)
    )

    illTyped(
      """ Seq(1).safeContains("test") """
    )
    illTyped(
      """ Seq((1: Any)).safeContains("test") """
    )
    assert(
      Seq(1).safeContains(1)
    )

    illTyped(
      """ !Set(1).contains("test") """
    )
    assert(
      Set(1).contains(1)
    )

    illTyped(
      """ !Set(1).safeContains("test") """
    )
    illTyped(
      """ Set((1: Any)).safeContains("test") """
    )
    assert(
      Set(1).safeContains(1)
    )

    assert(
      "ab57" === ("a" ~ "b" ~ 5 ~ 7l)
    )

    assert(
      "6ab57" === (6 ~ "a" ~ "b" ~ 5 ~ 7l)
    )


    illTyped(""" "a" ~ Some("a") """)
    illTyped(""" Some("a") ~ "a" """)

    assert(
      "abc" === List("a","b","c").safeMkString
    )

    assert(
      "aSome(5)c" === List("a",Option(5),"c").mkString
    )

    assert(
      "5" === 5.safeToString
    )

    assert(
      "aSome(b)c" === s"a${Some("b")}c"
    )

    illTyped(
      """ safe"a${Some("b")}c" """
    )

    assert(
      "abc" === safe"a${"b"}c"
    )

    println("Success")
  }
}
