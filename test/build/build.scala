import cbt._
class Build(val context: Context) extends BaseBuild{
  override def dependencies =
    super.dependencies ++ // don't forget super.dependencies here for scala-library, etc.
    Seq(
      DirectoryDependency(projectDirectory ++ "/..")
    ) ++
    Resolver( mavenCentral, sonatypeReleases ).bind(
       "com.chuusai" %% "shapeless" % "2.3.2"
    )
}
