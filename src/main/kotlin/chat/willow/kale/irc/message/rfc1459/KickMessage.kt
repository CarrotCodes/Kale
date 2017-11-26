package chat.willow.kale.irc.message.rfc1459

import chat.willow.kale.ICommand
import chat.willow.kale.IrcMessageComponents
import chat.willow.kale.irc.CharacterCodes
import chat.willow.kale.irc.message.MessageParser
import chat.willow.kale.irc.message.MessageSerialiser
import chat.willow.kale.irc.prefix.Prefix
import chat.willow.kale.irc.prefix.PrefixParser
import chat.willow.kale.irc.prefix.PrefixSerialiser

object KickMessage : ICommand {

    override val command = "KICK"

    data class Command(val users: List<String>, val channels: List<String>, val comment: String? = null) {
        
        object Parser : MessageParser<Command>() {

            override fun parseFromComponents(components: IrcMessageComponents): Command? {
                if (components.parameters.size < 2) {
                    return null
                }

                val channels = components.parameters[0].split(delimiters = CharacterCodes.COMMA)
                val users = components.parameters[1].split(delimiters = CharacterCodes.COMMA)

                if (channels.isEmpty() || channels.size != users.size) {
                    return null
                }

                val comment = components.parameters.getOrNull(2)

                return Command(users = users, channels = channels, comment = comment)
            }
            
        }
        
        object Serialiser : MessageSerialiser<Command>(command) {

            override fun serialiseToComponents(message: Command): IrcMessageComponents {
                val channels = message.channels.joinToString(separator = CharacterCodes.COMMA.toString())
                val users = message.users.joinToString(separator = CharacterCodes.COMMA.toString())
                val comment = message.comment

                if (comment != null) {
                    return IrcMessageComponents(parameters = listOf(channels, users, comment))
                } else {
                    return IrcMessageComponents(parameters = listOf(channels, users))
                }
            }
            
        }
        
    }

    data class Message(val source: Prefix, val users: List<String>, val channels: List<String>, val comment: String? = null) {

        object Parser : MessageParser<Message>() {

            override fun parseFromComponents(components: IrcMessageComponents): Message? {
                if (components.parameters.size < 2) {
                    return null
                }

                val source = PrefixParser.parse(components.prefix ?: "") ?: return null
                val channels = components.parameters[0].split(delimiters = CharacterCodes.COMMA)
                val users = components.parameters[1].split(delimiters = CharacterCodes.COMMA)

                if (channels.isEmpty() || channels.size != users.size) {
                    return null
                }

                val comment = components.parameters.getOrNull(2)

                return Message(source = source, users = users, channels = channels, comment = comment)
            }

        }

        object Serialiser : MessageSerialiser<Message>(command) {

            override fun serialiseToComponents(message: Message): IrcMessageComponents {
                val prefix = PrefixSerialiser.serialise(message.source)
                val channels = message.channels.joinToString(separator = CharacterCodes.COMMA.toString())
                val users = message.users.joinToString(separator = CharacterCodes.COMMA.toString())
                val comment = message.comment

                if (comment != null) {
                    return IrcMessageComponents(prefix = prefix, parameters = listOf(channels, users, comment))
                } else {
                    return IrcMessageComponents(prefix = prefix, parameters = listOf(channels, users))
                }
            }

        }
        
    }

}