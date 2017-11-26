package chat.willow.kale.irc.message.rfc1459

import chat.willow.kale.ICommand
import chat.willow.kale.IrcMessageComponents
import chat.willow.kale.irc.CharacterCodes
import chat.willow.kale.irc.message.MessageParser
import chat.willow.kale.irc.message.MessageSerialiser
import chat.willow.kale.irc.prefix.Prefix
import chat.willow.kale.irc.prefix.PrefixParser
import chat.willow.kale.irc.prefix.PrefixSerialiser

object JoinMessage : ICommand {

    override val command = "JOIN"

    data class Command(val channels: List<String>, val keys: List<String>? = null) {

        object Parser : MessageParser<Command>() {

            override fun parseFromComponents(components: IrcMessageComponents): Command? {
                if (components.parameters.isEmpty()) {
                    return null
                }

                val unsplitChannels = components.parameters[0]
                val channels = unsplitChannels.split(delimiters = CharacterCodes.COMMA).filterNot(String::isEmpty)

                if (components.parameters.size < 2) {
                    return Command(channels)
                } else {
                    val unsplitKeys = components.parameters[1]
                    val keys = unsplitKeys.split(delimiters = CharacterCodes.COMMA).filterNot(String::isEmpty)

                    return Command(channels, keys)
                }
            }
        }

        object Serialiser : MessageSerialiser<Command>(command) {

            override fun serialiseToComponents(message: Command): IrcMessageComponents {
                val channels = message.channels.joinToString(separator = CharacterCodes.COMMA.toString())

                if (message.keys == null || message.keys.isEmpty()) {
                    return IrcMessageComponents(parameters = listOf(channels))
                } else {
                    val keys = message.keys.joinToString(separator = CharacterCodes.COMMA.toString())

                    return IrcMessageComponents(parameters = listOf(channels, keys))
                }
            }
        }

    }

    data class Message(val source: Prefix, val channels: List<String>) {

        object Parser : MessageParser<Message>() {

            override fun parseFromComponents(components: IrcMessageComponents): Message? {
                if (components.parameters.isEmpty()) {
                    return null
                }

                val source = PrefixParser.parse(components.prefix ?: "") ?: return null
                val unsplitChannels = components.parameters[0]
                val channels = unsplitChannels.split(delimiters = CharacterCodes.COMMA).filterNot(String::isEmpty)

                return Message(source, channels)
            }
        }

        object Serialiser : MessageSerialiser<Message>(command) {

            override fun serialiseToComponents(message: Message): IrcMessageComponents {
                val prefix = PrefixSerialiser.serialise(message.source)
                val channels = message.channels.joinToString(separator = CharacterCodes.COMMA.toString())

                return IrcMessageComponents(prefix = prefix, parameters = listOf(channels))
            }
        }

    }

}