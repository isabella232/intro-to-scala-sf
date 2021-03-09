package introcourse.level07

import cats.effect.{IO, IOApp, ExitCode}
import org.http4s.HttpApp
import org.http4s.server.blaze.BlazeServerBuilder

@SuppressWarnings(Array("org.wartremover.warts.Any"))
object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    val server = BlazeServerBuilder[IO](executionContext)
      .bindHttp(8080)
      .withHttpApp(HttpApp(Service.run))
      .resource

    server.use(_ => IO.never)
      .map(_ => ExitCode.Success)
  }
}
