package bloop.util

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

import scala.util.control.NonFatal

import bloop.Cli
import bloop.cli.Commands
import bloop.cli.CommonOptions
import bloop.engine.Run
import bloop.io.Environment.lineSeparator

import caseapp.core.Arg
import caseapp.core.util.Formatter
import caseapp.core.util.NameOps._

object CommandsDocGenerator {
  private final val docsContentDelimiter =
    "<!-- This file was generated by bloop.util.CommandsDocGenerator (see developer docs) -->"
  private val NL = lineSeparator
  def main(args: Array[String]): Unit = {
    def generateHTML(commandExamples: Map[String, Seq[String]]): Seq[String] = {
      Commands.RawCommand.help.messages.map {
        case (commandName, messages) =>
          val examples =
            commandExamples.getOrElse(commandName.mkString(" "), Nil).reverse.map { example =>
              s"  * <samp>$example</samp>"
            }

          val argsOption = messages.argsNameOption.map(" <" + _ + ">").mkString
          val progName = "bloop"
          val b = new StringBuilder
          b ++= NL
          b ++= s"## `$progName $commandName$argsOption`"
          b ++= NL
          b ++= NL
          b ++= "#### Usage"
          b ++= NL
          b ++= NL
          b ++= s"<dl>"
          b ++= NL
          b ++= optionsMessage(messages.args)
          b ++= NL
          b ++= s"</dl>"
          b ++= NL
          if (examples.nonEmpty) {
            b ++= NL
            b ++= s"#### Examples${NL}"
            b ++= NL
            b ++= examples.mkString(NL)
          }
          b.result()
      }
    }

    def error(msg: String): Unit = {
      println(s"Error: ${msg}")
      sys.exit(1)
    }

    parseExamples match {
      case Left(msg) => error(msg)
      case Right(commandExamples) if (args.headOption.contains("--test")) =>
        val generation = generateHTML(commandExamples)
        assert(!generation.isEmpty, s"Generation of HTML yielded empty map! ${generation}")
      case Right(commandExamples) if (args.headOption.contains("--out")) =>
        args.tail.headOption match {
          case Some(userPath) =>
            try {
              val outputPath = Paths.get(userPath).toAbsolutePath
              val oldContents = new String(Files.readAllBytes(outputPath), StandardCharsets.UTF_8)
              oldContents.split(s"\\Q$docsContentDelimiter\\E") match {
                case Array(header, _) =>
                  val generated = generateHTML(commandExamples).mkString(NL)
                  val newContents = header + docsContentDelimiter + lineSeparator + generated
                  Files.write(outputPath, newContents.getBytes(StandardCharsets.UTF_8))
                  ()
                case _ =>
                  error(s"Could not split at '$docsContentDelimiter' in file ${outputPath}")
              }
            } catch {
              case NonFatal(t) =>
                t.printStackTrace()
                error(s"Unexpected error when writing docs to ${userPath}")
            }
          case None =>
            error(s"Expected second argument after `--out`")
        }
      case Right(commandExamples) =>
        if (args.size > 0) error(s"Unexpected arguments: ${args.mkString(" ")}")
        else println(generateHTML(commandExamples).mkString(NL))
    }
  }

  def optionsMessage(args: Seq[Arg]): String = {
    val options = args.collect {
      case arg if !arg.noHelp =>
        val names = (arg.name +: arg.extraNames).distinct
        val message = arg.helpMessage.map(_.message).getOrElse("")
        val cmdNames = names
          .map("<code>" + _.option(Formatter.DefaultNameFormatter) + "</code>")
          .mkString(" or ")
        val description =
          if (message.isEmpty) message
          else s"${NL}  <dd><p>${message}</p></dd>"
        s"  <dt>$cmdNames</dt>$description"
    }

    options.mkString(NL)
  }

  private val ExampleProjectName: String = "foobar"
  private val ExampleProjectName2: String = "baz"
  private val ExampleMainClass: String = "com.acme.Main"
  private final val CommandExamples = {
    val tmp = java.nio.file.Files.createTempDirectory("tmp")
    List(
      "bloop projects",
      "bloop projects --dot-graph",
      "bloop clean",
      s"bloop clean $ExampleProjectName",
      s"bloop clean $ExampleProjectName $ExampleProjectName2",
      s"bloop clean $ExampleProjectName --propagate",
      s"bloop bsp --protocol local --socket ${tmp.resolve("socket").toString} --pipe-name \\\\.\\pipe\\my-name-pipe",
      "bloop bsp --protocol tcp --host 127.0.0.1 --port 5101",
      s"bloop compile $ExampleProjectName",
      s"bloop compile $ExampleProjectName --cascade",
      s"bloop compile $ExampleProjectName $ExampleProjectName2",
      s"bloop compile $ExampleProjectName -w",
      s"bloop compile $ExampleProjectName --reporter bloop",
      s"bloop compile $ExampleProjectName --reporter scalac",
      s"bloop test $ExampleProjectName",
      s"bloop test $ExampleProjectName --cascade",
      s"bloop test $ExampleProjectName $ExampleProjectName2",
      s"bloop test $ExampleProjectName -w",
      s"bloop test $ExampleProjectName --propagate",
      s"bloop test $ExampleProjectName --propagate -w",
      s"bloop test $ExampleProjectName --only com.acme.StringSpecification",
      s"bloop test $ExampleProjectName --only com.acme.StringSpecification -- -J-Xmx4g",
      s"bloop console $ExampleProjectName",
      s"bloop console $ExampleProjectName --exclude-root",
      s"bloop run $ExampleProjectName",
      s"bloop run $ExampleProjectName -m $ExampleMainClass -- -J-Xmx4g arg1 arg2",
      s"bloop run $ExampleProjectName -O debug -- arg1",
      s"bloop run $ExampleProjectName -m $ExampleMainClass -O release -w",
      s"bloop link $ExampleProjectName",
      s"bloop link $ExampleProjectName --main $ExampleMainClass",
      s"bloop link $ExampleProjectName -O debug -w",
      s"bloop link $ExampleProjectName -O release -w",
      s"bloop link $ExampleProjectName --main $ExampleMainClass -w"
    )
  }

  private def commandName(cmd: Commands.Command): Option[String] = {
    cmd match {
      case _: Commands.About => Some("about")
      case _: Commands.Projects => Some("projects")
      case _: Commands.Clean => Some("clean")
      case _: Commands.Bsp => Some("bsp")
      case _: Commands.Compile => Some("compile")
      case _: Commands.Test => Some("test")
      case _: Commands.Console => Some("console")
      case _: Commands.Run => Some("run")
      case _: Commands.Link => Some("link")
      case _ => None
    }
  }

  case class Example(projectName: String, examples: List[String])
  def parseExamples: Either[String, Map[String, List[String]]] = {
    val parsed = CommandExamples.foldLeft[Either[String, List[Example]]](Right(Nil)) {
      case (l @ Left(_), _) => l
      case (Right(prevExamples), example) =>
        Cli.parse(example.split(" ").tail, CommonOptions.default) match {
          case Run(cmd: Commands.Command, _) =>
            commandName(cmd) match {
              case Some(name) =>
                Right(Example(name, List(example)) :: prevExamples)
              case None => Right(prevExamples)
            }

          case a => Left(s"Example $example yielded unrecognized action $a")
        }
    }

    parsed.map(_.groupBy(_.projectName).mapValues(_.flatMap(_.examples)))
  }
}
