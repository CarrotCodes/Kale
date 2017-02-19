package chat.willow.kale.irc.message.extension.cap

import chat.willow.kale.irc.message.IMessage
import chat.willow.kale.irc.message.IMessageParser
import chat.willow.kale.irc.message.IMessageSerialiser
import chat.willow.kale.irc.message.IrcMessage

data class CapEndMessage(val target: String? = null): IMessage {
    override val command: String = "CAP"

    companion object Factory: IMessageParser<CapEndMessage>, IMessageSerialiser<CapEndMessage> {

        override fun serialise(message: CapEndMessage): IrcMessage? {
            if (message.target != null) {
                return IrcMessage(command = "CAP", parameters = listOf(message.target, "END"))
            } else {
                return IrcMessage(command = "CAP", parameters = listOf("END"))
            }
        }

        override fun parse(message: IrcMessage): CapEndMessage? {
            if (message.parameters.size < 2) {
                return null
            }

            val target = message.parameters[0]
            @Suppress("UNUSED_VARIABLE") val subCommand = message.parameters[1]

            return CapEndMessage(target = target)
        }
    }

}