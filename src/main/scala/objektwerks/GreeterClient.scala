package objektwerks

import akka.{Done, NotUsed}
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.grpc.GrpcClientSettings
import akka.stream.scaladsl.Source

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

object GreeterClient {
  def main(args: Array[String]): Unit = {
    implicit val sys: ActorSystem[_] = ActorSystem(Behaviors.empty, "GreeterClient")
    implicit val ec: ExecutionContext = sys.executionContext

    val client = GreeterServiceClient(GrpcClientSettings.fromConfig("objektwerks.GreeterService"))

    val names = if (args.isEmpty) List("Fred Flintstone", "Barney Rebel") else args.toList
    names.foreach(singleRequestReply)
    if (args.nonEmpty) names.foreach(streamingBroadcast)

    def singleRequestReply(name: String): Unit = {
      println(s"Single request-reply invoked for: $name")
      val reply = client.sayHello(HelloRequest(name))
      reply.onComplete {
        case Success(message) => println(s"Single request-reply: $message")
        case Failure(error) => println(s"Single request-reply error: $error")
      }
    }

    def streamingBroadcast(name: String): Unit = {
      println(s"Streaming broadcast invoked for: $name")
      val requestStream: Source[HelloRequest, NotUsed] =
        Source
          .tick(1.second, 1.second, "tick")
          .zipWithIndex
          .map { case (_, i) => i }
          .map(i => HelloRequest(s"$name-$i"))
          .mapMaterializedValue(_ => NotUsed)

      val responseStream: Source[HelloReply, NotUsed] = client.sayHelloToAll(requestStream)
      val done: Future[Done] = responseStream.runForeach(reply => println(s"$name got streaming reply: ${reply.message}"))
      done.onComplete {
        case Success(_) => println(s"Streaming broadcast done.")
        case Failure(error) => println(s"Streaming broadcast error: $error")
      }
    }
  }
}