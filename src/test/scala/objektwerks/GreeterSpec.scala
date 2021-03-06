package objektwerks

import akka.actor.testkit.typed.scaladsl.ActorTestKit
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.grpc.GrpcClientSettings

import com.typesafe.config.ConfigFactory

import org.scalatest.BeforeAndAfterAll
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.concurrent.duration._

class GreeterSpec
  extends AnyWordSpec
  with BeforeAndAfterAll
  with Matchers
  with ScalaFutures {

  implicit val patience = PatienceConfig(scaled(5.seconds), scaled(100.millis))
  val conf = ConfigFactory
    .parseString("akka.http.server.preview.enable-http2 = on")
    .withFallback(ConfigFactory.defaultApplication())
  val testKit = ActorTestKit(conf)

  val serverSystem: ActorSystem[_] = testKit.system
  implicit val clientSystem: ActorSystem[_] = ActorSystem(Behaviors.empty, "GreeterClient")
  val client = GreeterServiceClient(GrpcClientSettings.fromConfig("objektwerks.GreeterService"))

  val bound = new GreeterServer(serverSystem).run()
  bound.futureValue

  override def afterAll(): Unit = {
    ActorTestKit.shutdown(clientSystem)
    testKit.shutdownTestKit()
  }

  "GreeterService" should {
    "reply to single request" in {
      val reply = client.sayHello(HelloRequest("Barney Rebel"))
      reply.futureValue should ===(HelloReply("Hello, Barney Rebel"))
    }
  }
}