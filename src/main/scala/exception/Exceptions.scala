package exception

object Exceptions {
  // TODO: Expand the error tree, cover critical places
  sealed abstract class CustomException(message: String = "") extends Exception(message)

  case class InternalException(message: String) extends CustomException(message)
}
