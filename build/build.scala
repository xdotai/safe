import cbt._
import ai.x.build._
trait Shared extends MultipleScalaVersions{
  override def scalaVersions = Seq( "2.10.6", "2.11.11", "2.12.2" )
}
class TestBuild(val context: Context) extends Shared{
  override def projectDirectory = context.workingDirectory / "test"
  override def dependencies =
    super.dependencies ++ // don't forget super.dependencies here for scala-library, etc.
    Seq( new Build(context) ) ++
    Resolver( mavenCentral, sonatypeReleases ).bind(
      //"ai.x" %% "safe" % "0.1.0",
      "com.chuusai" %% "shapeless" % "2.3.2"
    )
}

class Build(val context: Context)
  extends XdotaiFreeSoftwareBuild
  with Shared
  with SonatypeRelease{ main =>
  def version = "0.1.0"
  def description = "Helpers to write type-safe Scala code using equality and Strings."
  def inceptionYear = 2016
  def developers = Seq( team.cvogt )
  override def test = new TestBuild(context)
}
