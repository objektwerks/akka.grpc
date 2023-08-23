Akka gRpc
---------
>Akka gRpc client, Akka-Http server and tests. See: https://developer.lightbend.com/guides/akka-grpc-quickstart-scala/

Install
----------
1. brew install protobuf@3 { Note version, i.e., 3.20.3 }
2. Add ```PB.protocVersion := "3.20.3"``` line to build.sbt

Build
-----
1. sbt clean compile

Test
----
1. sbt clean test