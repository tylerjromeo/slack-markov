package org.romeo.slack

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * User: tylerromeo
 * Date: 10/19/16
 * Time: 4:13 PM
 *
 */
@RestController
class SlackBotController @Autowired constructor(val channelGenerator: ChannelGenerator) {

    @RequestMapping("/")
    fun getMessageForChannel(@RequestParam token: String,
                             @RequestParam("channel_name") requestChannel: String,
                             @RequestParam("text") channel: String?): SlackResponse {
        //TODO check token
        return SlackResponse(text = channelGenerator.generateMessage(channel ?: requestChannel))
    }

}

data class SlackResponse(
        @JsonProperty("response_type") val responseType: String = "in_channel",
        val text: String
)