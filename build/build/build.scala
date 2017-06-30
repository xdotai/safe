import cbt._
class Build(val context: Context) extends BuildBuild{
  override def dependencies =
    super.dependencies ++ // don't forget super.dependencies here for scala-library, etc.
    Seq(
      plugins.sonatypeRelease,
      GitDependency(
        "https://github.com/xdotai/free-software-build.git",
        "f3da3a71aa65724358d63b567ed0493afda90007"
      )
    )
}
