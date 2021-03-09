package introcourse.level07

import cats.effect.IO

case class User(name: String, age: Int)

sealed trait DatabaseError
case object ConnectionTimeout extends DatabaseError

object Database {
  val users = List(
    User("Vincent", 30),
    User("Jules", 35),
    User("Mia", 27),
    User("Winston", 42)
  )

  def getUsers(): IO[Either[DatabaseError, List[User]]] = IO {
    Right(users)
  }
}
