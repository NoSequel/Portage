package io.github.nosequel.portage.core.handler

import java.util.Optional

class HandlerManager(vararg handlers: Handler) {

    private val handlers: MutableSet<Handler> = mutableSetOf()

    init {
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
        return clazz.cast(this.find(clazz)
            .orElseThrow { NullPointerException("No handler found with class ${clazz.name}") })
    }

    /**
     * Register a [Handler] object
     */
    fun <T> register(handler: T) where T : Handler {
        this.handlers.add(handler)
    }
}