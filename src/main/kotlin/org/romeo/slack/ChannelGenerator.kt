package org.romeo.slack

import org.romeo.markov.Markov
import java.io.File
import java.io.FileNotFoundException
import java.util.*

/**
 * User: tylerromeo
 * Date: 10/19/16
 * Time: 2:30 PM
 * class to generate messages for channels. Uses one markov chain per channel, generated on demand and cached.
 * @property dir a directory containing a subdirectory of json files named for each channel
 */
class ChannelGenerator {

    private val generators: HashMap<String, Markov<String>>
    private val path: String

    @Throws(FileNotFoundException::class)
    constructor(dir: File) {
        if(!dir.exists()) throw FileNotFoundException("$dir does not exist")
        this.path = dir.path
        generators = HashMap()
    }

    /**
     * generates a message based on the data from that channel.
     * @param channelName must match a directory that contains json files for the channel
     * @throws FileNotFoundException if the directory with that channel name doesn't exist
     */
    @Throws(FileNotFoundException::class)
    fun generateMessage(channelName: String): String {
        return generators.getOrPut(channelName, {
           JsonParser(File(path, channelName)).buildMarkovChain()
        }).createSeries().makeSentence()
    }
}

fun main(args: Array<String>) {
    println("type a channel name:")
    val channelGenerator = ChannelGenerator(File("data"))
    while(true) {
        val fileName = readLine()
        if(fileName !== null) {
            println(try {
                channelGenerator.generateMessage(fileName)
            } catch (e: FileNotFoundException) {
                e.message
            })
        }
    }
}
