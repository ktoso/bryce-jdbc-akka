package akka.support

import akka.actor.{ActorSystem, Props}
import akka.persistence.{Persistence, PersistentActor}

object Application extends App {
  val system = ActorSystem()

  Persistence(system)

  system.actorOf(Props(new PersistentActor {
    override def persistenceId: String = "the-guy"

    override def receiveRecover: Receive = {
      case x => println ("recover  : " + x + ", seqNr: " + lastSequenceNr)
    }

    override def receiveCommand: Receive = {
      case c => persist(c) { it => println("persisted: " + it + ", seqNr: " + lastSequenceNr) }
    }
  })) ! "hello world"

  readLine("QUIT?")
  system.terminate()
}
