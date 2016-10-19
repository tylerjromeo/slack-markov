package org.romeo.slack

import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.core.TreeNode
import com.fasterxml.jackson.databind.MappingJsonFactory
import org.romeo.markov.Markov
import java.io.File
import java.io.FilenameFilter

/**
 * User: tylerromeo
 * Date: 10/19/16
 * Time: 11:05 AM
 *
 * reads slack json files
 */
class JsonParser(val dir: File) {

    fun buildMarkovChain(): Markov<String> {

        val retval = Markov("__start__", "__end__")
        val f = MappingJsonFactory()
        val files = dir.listFiles(object : FilenameFilter {
            override fun accept(dir: File, name: String): Boolean {
                return name.endsWith(".json")
            }
        })

        for (file in files) {
            val jp = f.createParser(file)

            var current: JsonToken

            current = jp.nextToken()
            if (current !== JsonToken.START_ARRAY) {
                println("Error: root should be array: quiting.")
                continue
            }

            while (jp.nextToken() !== JsonToken.END_ARRAY) {
                var node = jp.readValueAsTree<TreeNode>()
                if(node.get("subtype") === null && node.get("type")?.getString().equals("message")) {
                    val text = node.get("text")?.getString()
                    if(text !== null) retval.addTokens(text.split(" "))
                }
            }
        }
        return retval
    }


}

fun TreeNode.getString(): String {
    val str = this.toString()
    return str.substring(1, str.length -1)
}

fun Collection<String>.makeSentence(): String {
    return this.filter { it != "__start__" && it != "__end__" }.joinToString(separator = " ")
}

fun main(args: Array<String>) {
    val markov = JsonParser(File("./test-data")).buildMarkovChain()
    for(i in 1..1000) {
        println(markov.createSeries().makeSentence())
    }
}