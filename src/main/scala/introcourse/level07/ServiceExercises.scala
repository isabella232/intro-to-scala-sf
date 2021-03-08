package introcourse.level07

import cats.effect.IO
import org.http4s._
import org.http4s.dsl.io._

@SuppressWarnings(Array("org.wartremover.warts.Any"))
object Service {
  /* The service already has ping endpoint.
   *
   * You can try it by running the service:
   * > sbt run
   *
   * Try calling the service at http://localhost:8080/ping
   *
   * Look at the status parameter of the Response class: `Status.Ok`.
   * This is an implemented via an ADT. Why?
   */
  def ping(): IO[Response[IO]] = IO(Response(Status.Ok).withEntity("pong"))

  /* The run method contains our "routes". It maps requests to responses.
   *
   * We use the HTTP framework http4s, which has a specific DSL for pattern matching
   * on request objects. Compare it with the `::` operator for matching lists.
   *
   * Add appropriate handling for routes that are not found.
   *
   * Hints:
   * - Use a response with a status code that indicates a resource that wasn't found.
   * - All responses are wrapped in an IO type. Why?
   */
  def run(request: Request[IO]): IO[Response[IO]] = request match {
    case GET -> Root / "ping" => ping()
    case _ => ???
  }

  /* Our service is going to return a list of user names. Let's start by writing
   * a function that takes in a list of users, and returns the names of all users,
   * separated by new lines.
   *
   * Hints:
   * - The newline character is represented as "\n"
   * - You can use foldLeft or mkString to concatenate the list items
   */
  def userNames(users: List[User]): String = ???

  /* Now, let's implement the response for the users endpoint.
   * You can use the `Database` object.
   * It has a `getUsers()` method which returns a list of `User` objects.
   *
   * Note, that calls to the database can return an error.
   * Inspect the return type of the `getUsers()` method.
   * All calls to the Database are wrapped in an `IO` type. Why?
   *
   * Implement this method as follows:
   * - If the database call succeeds, convert the result by using the
   *   `userNames` method and return a response with an HTTP OK status.
   * - If the database calls fails, return an HTTP Internal Server Error.
   *
   * Hints:
   * - You can `map` over the `IO` type, just like you would map over a list,
   *   option, either, etc.
   * - Use pattern matching to handle the `Either` type
   */
  def users(): IO[Response[IO]] = ???

  /* We are now extending our service with support for logging.
   * The following method will print some request and response information to the
   * standard output. The behavior is wrapped in an IO. Why?
   */
  def log(request: Request[IO], response: Response[IO]) = IO {
    println(s"${request.uri.path}: ${response.status.code}")
  }

  /* Now write a new implementation for the users endpoint.
   * It should reuse the existing `users` method, but add a call to
   * logging.
   *
   * Once you complete the method, replace the `users` call in the `run` method with the
   * new `usersWithLog` method.
   *
   * Hints:
   * - Use a for comprehension
   */
  def usersWithLog(request: Request[IO]): IO[Response[IO]] = ???

  /* Finally, let's add a route to the `run` method to handle GET requests to the
   * "users" endopint. Hook it up to the `usersWithLog` method.
   *
   * Restart the service (press CTRL-C, then type `run` in an SBT console).
   * Try hitting http://localhost:8080/users and see if it returns a list
   * of users.
   */
}
