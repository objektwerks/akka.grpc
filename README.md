Akka gRpc
---------
>Akka gRpc client, Akka-Http server and tests. See: https://developer.lightbend.com/guides/akka-grpc-quickstart-scala/

Error
-----
>For Apple M1 chip, build breaks with this dependency error:
* ```protoc-3.15.6-osx-aarch_64.exe: not found```
>See these issues for more details:
1. https://github.com/os72/protoc-jar/issues/93
2. https://github.com/scalapb/ScalaPB/issues/1024

Resolution
----------
>For Apple M1 chip:
1. brew install protobuf { Note version, i.e., 3.17.3 }
2. Add ```PB.protocVersion := "3.17.3"``` line to build.sbt

Build
-----
1. sbt clean compile

Test
----
1. sbt clean test
