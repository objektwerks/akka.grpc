Akka gRpc
---------
>Akka gRpc client, server and tests.
>See: https://developer.lightbend.com/guides/akka-grpc-quickstart-scala/

Install
-------
1. brew install protobuf  { Installs protobuf: stable 3.17.3 }

Warning
-------
>Build is broken out-of-the-box due to this dependency error:
* ```protoc-3.15.6-osx-aarch_64.exe: not found```
>See these issues for more details:
1. https://github.com/os72/protoc-jar/issues/93 for more details.
2. See: https://github.com/scalapb/ScalaPB/issues/1024 for even more details.
>Resolution:
1. Add ```PB.protocVersion := "3.17.3"``` to build.sbt

Build
-----
1. sbt clean compile

Test
----
1. sbt clean test