package org.romeo.slack

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
                             @RequestParam("text") channel: String?): String {
        //TODO check token
        return channelGenerator.generateMessage(channel ?: requestChannel)
    }

}