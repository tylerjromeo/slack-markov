package org.romeo.markov

import java.util.*

/**
 * User: tylerromeo
 * Date: 10/18/16
 * Time: 8:26 PM
 *
 *
 */
class Markov<T> {

    private val rnd: Random
    private val data: HashMap<T, Vector<T>> = HashMap()
    private val startToken: T
    private val endToken: T

    constructor(startToken: T, endToken: T) {
        this.rnd = Random()
        this.startToken = startToken
        this.endToken = endToken
    }

    constructor(startToken: T, endToken: T, seed: Long) {
        this.rnd = Random(seed)
        this.startToken = startToken
        this.endToken = endToken
    }


    /**
     * add a series of [tokens] to the markov data set.
     */
    fun addTokens(tokens: Collection<T>) {
        if (tokens.isEmpty()) return;
        data.getOrPut(startToken, { Vector() }).add(tokens.first())
        for (i in tokens.indices) {
            val prefix = tokens.elementAt(i)
            val suffix = if (i + 1 >= tokens.size) endToken else tokens.elementAt(i + 1)
            val suffixes = data.getOrPut(prefix, { Vector() })
            suffixes.add(suffix)
        }
    }

    /**
     * based on the data included in the markov set, create a new series
     * @return a generated series of data
     */
    fun createSeries(): Collection<T> {
        val retval = LinkedList<T>()
        var token = startToken
        retval.add(token)
        while (token != endToken) {
            val nextTokens = data[token]
            if (nextTokens == null || nextTokens.isEmpty()) {
                token = endToken
            } else {
                token = nextTokens.randomElement(rnd)
            }
            retval.add(token)
        }
        return retval
    }

}

fun <T> Collection<T>.randomElement(r: Random): T {
    return this.elementAt(r.nextInt(this.size))
}


//fun main(args: Array<String>) {
//    val m = Markov("__start__", "__end__")
//    m.addTokens(listOf("do", "be", "do"))
//    m.addTokens(listOf("do", "da", "be", "do"))
//    m.addTokens(listOf("do", "do"))
//    print(m.createSeries())
//}