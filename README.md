Akka gRpc
---------
>Project contains Akka gRpc feature tests.

Warning
-------
1. Currently broken due to this dependency error: ```protoc-3.15.6-osx-aarch_64.exe: not found```
2. See: https://github.com/os72/protoc-jar/issues/93 for more details.
3. See: https://github.com/scalapb/ScalaPB/issues/1024 for even more details.
4. Homebrew: brew install protobuf  { Installs protobuf: stable 3.17.3 }