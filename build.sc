import mill._, scalalib._, scalafmt._

object language extends SbtModule with ScalafmtModule {
  def scalaVersion = "2.13.3"

  object test extends Tests {
    def ivyDeps = Agg(
      ivy"org.scalactic::scalactic:3.2.2",
      ivy"org.scalatest::scalatest:3.2.2"
    )
    def testFrameworks = Seq("org.scalatest.tools.Framework")
  }
}
