package introcourse.level07

import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funspec.AsyncFunSpec
import org.http4s.HttpApp
import org.http4s.Request
import org.http4s.Uri
import cats.effect.IO

class ServiceTest extends AsyncFunSpec with TypeCheckedTripleEquals {

  describe("GET /ping") {
    it("should respond with pong") {
      val service = HttpApp(Service.run)
      val request = Request[IO](uri = Uri(path = "/ping"))

      service(request)
        .flatMap(response => response.as[String])
        .map(body => assert(body === "pong"))
        .unsafeToFuture()
    }
  }

  describe("GET /unknown") {
    it("should respond with 404") {
      val service = HttpApp(Service.run)
      val request = Request[IO](uri = Uri(path = "/unknown"))

      service(request)
        .map(response => assert(response.status.code === 404))
        .unsafeToFuture()
    }
  }

  describe("userNames") {
    it("should return user names, separated by newlines") {
      val usernames = Service.userNames(List(User("Vincent", 30), User("Jules", 35)))
      assert(usernames === "Vincent\nJules")
    }

    it("should handle empty lists") {
      val usernames = Service.userNames(List())
      assert(usernames === "")
    }
  }

  describe("users") {
    it("should respond with 200") {
      val response = Service.users()

      response
        .map(response => assert(response.status.code === 200))
        .unsafeToFuture()
    }

    it("should return users") {
      val response = Service.users()

      response
        .flatMap(response => response.as[String])
        .map(body => assert(body === "Vincent\nJules\nMia\nWinston"))
        .unsafeToFuture()
    }
  }

  describe("usersWithLog") {
    it("should respond with 200") {
      val request = Request[IO](uri = Uri(path = "/users"))
      val response = Service.usersWithLog(request)

      response
        .map(response => assert(response.status.code === 200))
        .unsafeToFuture()
    }

    it("should return users") {
      val request = Request[IO](uri = Uri(path = "/users"))
      val response = Service.usersWithLog(request)

      response
        .flatMap(response => response.as[String])
        .map(body => assert(body === "Vincent\nJules\nMia\nWinston"))
        .unsafeToFuture()
    }
  }

  describe("GET /users") {
    it("should respond with a list of user names") {
      val service = HttpApp(Service.run)
      val request = Request[IO](uri = Uri(path = "/users"))

      service(request)
        .flatMap(response => response.as[String])
        .map(body => assert(body === "Vincent\nJules\nMia\nWinston"))
        .unsafeToFuture()
    }
  }
}
