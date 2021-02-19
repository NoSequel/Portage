package io.github.nosequel.portage.core.handler

import java.util.Optional
import java.util.stream.Stream

class HandlerManager(vararg handlers: Handler) {

    private val handlers: MutableSet<Handler> = mutableSetOf()

    companion object {
        lateinit var instance: HandlerManager
    }

    init {
        instance = this
        if (handlers.isNotEmpty()) {
            handlers.forEach { this.register(it) }
        }
    }

    /**
     * Find a [Handler] by a [Class]
     *
     * @return the handler optional
     */
    fun <T> find(clazz: Class<T>): Optional<T> where T : Handler {
        return this.handlers.stream()
            .filter { it.javaClass == clazz }
            .map { clazz.cast(it) }
            .findAny()
    }

    /**
     * Find a [Handler] by a [Class]
     *
     * @return the handler
     * @throws NullPointerException if there is no handler with the specified class
     */
    fun <T> findOrThrow(clazz: Class<T>): T where T : Handler {
        return this.find(clazz)
            .orElseThrow { NullPointerException("No handler found with class ${clazz.name}") }
    }

    /**
     * Register a [Handler] object
     */
    fun <T> register(handler: T) where T : Handler {
        this.handlers.add(handler)
    }

    /**
     * Open a new [Stream] for the [Handler] set
     */
    fun stream(): Stream<Handler> {
        return this.handlers.stream()
    }
}