name := "AtoCC_TestClient"
 
scalaVersion := "2.9.1"

resolvers += "Java.net Maven2 Repository" at "http://download.java.net/maven/2/"

resolvers += "Scala Tools Snapshots" at "http://scala-tools.org/repo-snapshots"

resolvers += "Typesafe Snapshots" at "http://repo.typesafe.com/typesafe/snapshots"

resolvers += "RESTlet" at "http://maven.restlet.org"

seq(webSettings :_*)

port in container.Configuration := 8080

libraryDependencies ++= {
  val liftVersion = "2.4-M4"
  Seq(
    "net.liftweb" %% "lift-webkit" % liftVersion % "compile->default",
    "net.liftweb" %% "lift-mapper" % liftVersion % "compile->default",
    "net.liftweb" %% "lift-wizard" % liftVersion % "compile->default",
    "net.liftweb" %% "lift-widgets" % liftVersion % "compile->default"
  )
}
 
libraryDependencies ++= Seq(
  "org.eclipse.jetty" % "jetty-webapp" % "8.0.4.v20111024" % "container",
  "junit" % "junit" % "4.10" % "test->default",
  "javax.servlet" % "servlet-api" % "2.5" % "provided->default",
  "com.h2database" % "h2" % "1.3.161",
  "org.slf4j" % "slf4j-log4j12" % "1.6.1",
  "org.restlet.jse" % "org.restlet" % "2.0.10",
  "org.restlet.jse" % "org.restlet.ext.xml" % "2.0.10"
)

scalacOptions in Compile ++= Seq(
	"-deprecation"
)
