# Kale
Kotlin/JVM IRC message parsing, serialising and notifying. Provides useful abstractions with the intention of splitting message parsing and IRC state management.

[Warren](https://github.com/CarrotCodes/Warren) is the state tracking counterpart. If you're interested in making something for IRC, you probably want it instead. Kale by itself does not track any IRC state.

[Thump](https://github.com/CarrotCodes/Thump) is the primary upstream project - a bridge that lets people chat between Minecraft and IRC whilst they play.

[![codecov](https://codecov.io/gh/CarrotCodes/Kale/branch/develop/graph/badge.svg)](https://codecov.io/gh/CarrotCodes/Kale)

PR TEST DO NOT MERGE

## Features

Warren and Kale have a few interesting features:

* The responsibilities of parsing and state management are separated
* Both parsing and state management are verified by hundreds of unit tests
* Messages, and state handlers, are individually encapsulated

Planned releases (and their features) are tracked in [Projects](https://github.com/CarrotCodes/Kale/projects).

## TODO
* [RFC 1459](https://tools.ietf.org/html/rfc1459)
 * Pretty much done for majority of usage - consider being more complete
* [IRC v3](http://ircv3.net/irc/)
 * Messages for extensions listed on the IRCv3 libraries site: http://ircv3.net/software/libraries.html

## Code License
The source code of this project is licensed under the terms of the ISC license, listed in the [LICENSE](LICENSE.md) file. A concise summary of the ISC license is available at [choosealicense.org](http://choosealicense.com/licenses/isc/).

## Building
This project uses Gradle and IntelliJ IDEA for pretty easy setup and building.

The general idea:
* **Setup**: `./gradlew clean idea`
* **Building**: `./gradlew build`
* **Producing an all-in-one Jar**: `./gradlew build shadowJar`

If you run in to odd Gradle issues, doing `./gradlew clean` usually fixes it.
