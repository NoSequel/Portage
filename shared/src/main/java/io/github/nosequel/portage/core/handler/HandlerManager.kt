package io.github.nosequel.portage.core.handler

import java.util.Optional
import java.util.stream.Stream

object HandlerManager {

    private val handlers: MutableMap<Class<Handler>, Handler> = mutableMapOf()

    /**
     * Find a [Handler] by a [Class]
     *
     * @return the handler optional
     */
    fun <T> find(clazz: Class<T>): Optional<T> where T : Handler {
        return Optional.of(clazz.cast(this.handlers.get(clazz)))
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
        this.handlers[handler.javaClass] = handler
    }

    /**
     * Open a new [Stream] for the [Handler] set
     */
    fun stream(): Stream<Handler> {
        return this.handlers.values.stream()
    }
}