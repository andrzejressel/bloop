package build

object Dependencies {
  val Scala210Version = "2.10.7"
  val Scala211Version = "2.11.12"
  val Scala212Version = "2.12.7"

  val nailgunVersion = "6992a3bf"
  // Used to download the python client instead of resolving
  val nailgunCommit = "dc1dd806"

  val zincVersion = "1.2.1+106-0dad4a69"
  val bspVersion = "2.0.0-M1" 
  val scalazVersion = "7.2.20"
  val coursierVersion = "1.1.0-M3"
  val lmVersion = "1.0.0"
  val configDirsVersion = "10"
  val caseAppVersion = "1.2.0-faster-compile-time"
  val sourcecodeVersion = "0.1.4"
  val sbtTestInterfaceVersion = "1.0"
  val sbtTestAgentVersion = "1.0.4"
  val junitVersion = "0.11"
  val graphvizVersion = "0.2.2"
  val directoryWatcherVersion = "0.8.0+6-f651bd93"
  val mavenApiVersion = "3.5.2"
  val mavenAnnotationsVersion = "3.5"
  val mavenScalaPluginVersion = "3.2.2"
  val gradleVersion = "3.0"
  val groovyVersion = "2.5.0"
  val ipcsocketVersion = "1.0.0"
  val monixVersion = "2.3.3"
  val circeVersion = "0.9.3"
  val nuprocessVersion = "1.2.4"
  val shapelessVersion = "2.3.3-lower-priority-coproduct"
  val scalaNativeVersion = "0.3.7"
  val scalaJs06Version = "0.6.25"
  val scalaJs10Version = "1.0.0-M5"
  val millVersion = "0.2.6"
  val xxHashVersion = "1.3.0"
  val ztVersion = "1.13"
  val difflibVersion = "1.3.0"

  import sbt.librarymanagement.syntax.stringToOrganization
  val zinc = "ch.epfl.scala" %% "zinc" % zincVersion
  val bsp = "ch.epfl.scala" %% "bsp4s" % bspVersion
  val nailgun = "ch.epfl.scala" % "nailgun-server" % nailgunVersion

  val configDirectories = "io.github.soc" % "directories" % configDirsVersion
  val libraryManagement = "org.scala-sbt" %% "librarymanagement-ivy" % lmVersion
  val scalazCore = "org.scalaz" %% "scalaz-core" % scalazVersion
  val scalazConcurrent = "org.scalaz" %% "scalaz-concurrent" % scalazVersion
  val coursier = "io.get-coursier" %% "coursier" % coursierVersion
  val coursierCache = "io.get-coursier" %% "coursier-cache" % coursierVersion
  val coursierScalaz = "io.get-coursier" %% "coursier-scalaz-interop" % coursierVersion
  val shapeless = "ch.epfl.scala" %% "shapeless" % shapelessVersion
  val caseApp = "ch.epfl.scala" %% "case-app" % caseAppVersion
  val sourcecode = "com.lihaoyi" %% "sourcecode" % sourcecodeVersion
  val sbtTestInterface = "org.scala-sbt" % "test-interface" % sbtTestInterfaceVersion
  val sbtTestAgent = "org.scala-sbt" % "test-agent" % sbtTestAgentVersion

  val scalacheck = "org.scalacheck" %% "scalacheck" % "1.13.4"
  val junit = "com.novocode" % "junit-interface" % junitVersion
  val graphviz = "guru.nidi" % "graphviz-java" % graphvizVersion
  val directoryWatcher = "ch.epfl.scala" % "directory-watcher" % directoryWatcherVersion
  val difflib = "com.googlecode.java-diff-utils" % "diffutils" % difflibVersion

  import sbt.Provided
  val mavenCore = "org.apache.maven" % "maven-core" % mavenApiVersion % Provided
  val mavenPluginApi = "org.apache.maven" % "maven-plugin-api" % mavenApiVersion
  val mavenPluginAnnotations = "org.apache.maven.plugin-tools" % "maven-plugin-annotations" % mavenAnnotationsVersion % Provided
  val mavenScalaPlugin = "net.alchim31.maven" % "scala-maven-plugin" % mavenScalaPluginVersion

  val gradleCore = "org.gradle" % "gradle-core" % gradleVersion % Provided
  val gradleWorkers = "org.gradle" % "gradle-workers" % gradleVersion % Provided
  val gradleDependencyManagement = "org.gradle" % "gradle-dependency-management" % gradleVersion % Provided
  val gradleToolingApi = "org.gradle" % "gradle-tooling-api" % gradleVersion % Provided
  val groovy = "org.codehaus.groovy" % "groovy" % groovyVersion % Provided

  val ipcsocket = "org.scala-sbt.ipcsocket" % "ipcsocket" % ipcsocketVersion
  val monix = "io.monix" %% "monix" % monixVersion

  val circeDerivation = "io.circe" %% "circe-derivation" % "0.9.0-M3"
  val circeParser = "io.circe" %% "circe-parser" % circeVersion
  val circeCore = "io.circe" %% "circe-core" % circeVersion
  val circeGeneric = "io.circe" %% "circe-generic" % circeVersion
  val nuprocess = "com.zaxxer" % "nuprocess" % nuprocessVersion

  val scalaNativeTools = "org.scala-native" %% "tools" % scalaNativeVersion
  val scalaJsTools06 = "org.scala-js" %% "scalajs-tools" % scalaJs06Version
  val scalaJsSbtTestAdapter06 = "org.scala-js" %% "scalajs-sbt-test-adapter" % scalaJs06Version
  val scalaJsEnvs06 = "org.scala-js" %% "scalajs-js-envs" % scalaJs06Version

  val scalaJsLinker10 = "org.scala-js" %% "scalajs-linker" % scalaJs10Version
  val scalaJsIO10 = "org.scala-js" %% "scalajs-io" % scalaJs10Version
  val scalaJsEnvs10 = "org.scala-js" %% "scalajs-js-envs" % scalaJs10Version
  val scalaJsEnvNode10 = "org.scala-js" %% "scalajs-env-nodejs" % scalaJs10Version
  val scalaJsEnvJsdomNode10 = "org.scala-js" %% "scalajs-env-jsdom-nodejs" % scalaJs10Version
  val scalaJsSbtTestAdapter10 = "org.scala-js" %% "scalajs-sbt-test-adapter" % scalaJs10Version
  val scalaJsLogging10 = "org.scala-js" %% "scalajs-logging" % scalaJs10Version

  val mill = "com.lihaoyi" %% "mill-scalalib"	% millVersion % Provided
  val xxHashLibrary = "net.jpountz.lz4" % "lz4" % xxHashVersion
  val zt = "org.zeroturnaround" % "zt-zip" % ztVersion
}