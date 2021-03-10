package introcourse.level07

import cats.effect._
import org.http4s.HttpApp
import org.http4s.server.blaze.BlazeServerBuilder

import scala.concurrent.ExecutionContext.global

@SuppressWarnings(Array("org.wartremover.warts.Any"))
object Main {
  implicit val timer: Timer[IO] = IO.timer(global)
  implicit val cs: ContextShift[IO] = IO.contextShift(global)

  /* This is the main method for our program.
   * The scala runtime knows how to find this main
   * class and it's main method when you call `run`
   * in the sbt console.
   *
   * The serve value is represented via the `IO`
   * data type. Does this program do anything?
   *
   * Fix it by evaluating the `IO` value.
   *
   * Hints:
   * - Use unsafeRunSync()
   */
  def main(args: Array[String]): Unit = {
    val serve: IO[Unit] = BlazeServerBuilder[IO](global)
      .bindHttp(8080)
      .withHttpApp(HttpApp(Service.run))
      .serve.compile.drain
  }
}
