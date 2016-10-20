package org.romeo.slack

import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
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

    @Bean
    open fun objectMapperBuilder(): Jackson2ObjectMapperBuilder
            = Jackson2ObjectMapperBuilder().modulesToInstall(KotlinModule())

}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
