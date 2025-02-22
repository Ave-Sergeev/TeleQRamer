addSbtPlugin("org.scalameta"  % "sbt-scalafmt"        % "2.5.4")
addSbtPlugin("ch.epfl.scala"  % "sbt-scalafix"        % "0.14.2")
addSbtPlugin("org.xerial.sbt" % "sbt-pack"            % "0.20")
addSbtPlugin("com.github.sbt" % "sbt-native-packager" % "1.11.1")

// workaround for scala-xml dependency conflict
libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always
