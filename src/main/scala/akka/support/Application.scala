package akka.support

import java.util.UUID

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.cluster.sharding.{ClusterSharding, ClusterShardingSettings, ShardRegion}
import akka.event.LoggingReceive
import akka.persistence.PersistentActor
import akka.stream.{ActorMaterializer, Materializer}

import scala.concurrent.ExecutionContext

object TheGuy {
  final val ShardName = "TheGuyShard"
  final val PersistenceId = "TheGuy"
  final val NumberOfShards: Int = 100

  final case class EntityEnvelope(id: String, payload: Any)

  val extractEntityId: ShardRegion.ExtractEntityId = {
    case EntityEnvelope(id, payload) ⇒ (id.toString, payload)
  }

  val extractShardId: ShardRegion.ExtractShardId = {
    case EntityEnvelope(id, _) ⇒ (id.hashCode % NumberOfShards).toString
  }
}
class TheGuy extends PersistentActor {
  override val persistenceId: String = TheGuy.PersistenceId + "-" + self.path.name

  override val receiveRecover: Receive = LoggingReceive {
    case x => println(s"[$persistenceId]: recover  : $x, seqNr: $lastSequenceNr")
  }

  override val receiveCommand: Receive = LoggingReceive {
    case c => persist(c) { it =>
      sender() ! s"[$persistenceId]: persisted: $it, seqNr: $lastSequenceNr"
    }
  }
}

class TheGuyPusher(theGuyShardRegion: ActorRef, numMessages: Int = 10) extends Actor {
  def randomId: String  = UUID.randomUUID().toString

  override def receive: Receive = LoggingReceive {
    case _ =>
      context.become(received(0))
      self ! "GO_PUSH"
  }

  def received(x: Int): Receive = {
    case "GO_PUSH" =>
      for {
        i <- 1 to numMessages
        id = randomId
        j <- 1 to 10
      } yield theGuyShardRegion ! TheGuy.EntityEnvelope(id, j)

    case _ if x == (numMessages * 10) - 1=>
      println("==> Terminating Actor System")
      context.system.terminate()

    case msg =>
//      println("==> Received: " + msg)
      context.become(received(x + 1))
  }
}

object Application extends App {
  implicit val system: ActorSystem = ActorSystem("ClusterSystem")
  implicit val mat: Materializer = ActorMaterializer()
  implicit val ec: ExecutionContext = system.dispatcher
  sys.addShutdownHook(system.terminate())

  val theGuyRegion: ActorRef = ClusterSharding(system).start(
    typeName = TheGuy.ShardName,
    entityProps = Props[TheGuy],
    settings = ClusterShardingSettings(system),
    extractEntityId = TheGuy.extractEntityId,
    extractShardId = TheGuy.extractShardId
  )
  system.actorOf(Props(new TheGuyPusher(theGuyRegion, 100))) ! "PUSH"
}
