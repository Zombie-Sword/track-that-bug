# track-that-bug
A bug tracker application

# Toolchain

You'll need to install npm and OpenJDK 11 before building the project.

Currently, we support Windows and Linux development; YMMV on other
platforms.

# Development

To get started, first build the project.

    ./gradlew build     # Linux
<i></i>

    gradlew.cmd build   % Windows

Then, run it.

    ./gradlew bootRun   # Linux
<i></i>

    gradlew.cmd bootRun   % Windows

The webapp will start up on http://localhost:8080/
