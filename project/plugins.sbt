addSbtPlugin("org.scalameta"  % "sbt-scalafmt"  % "2.5.2")
addSbtPlugin("ch.epfl.scala"  % "sbt-scalafix"  % "0.11.0")
addSbtPlugin("org.xerial.sbt" % "sbt-pack"      % "0.14")
addSbtPlugin("com.eed3si9n"   % "sbt-buildinfo" % "0.11.0")

// workaround for scala-xml dependency conflict
libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always
