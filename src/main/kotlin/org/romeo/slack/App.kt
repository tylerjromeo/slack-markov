package org.romeo.slack

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import java.io.File

/**
 * User: tylerromeo
 * Date: 10/19/16
 * Time: 4:26 PM
 *
 */
@SpringBootApplication
open class Application {

    @Bean
    open fun channelGenerator(): ChannelGenerator {
        return ChannelGenerator(File("Data"))
    }

}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
