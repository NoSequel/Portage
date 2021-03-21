package io.github.nosequel.portage.server.session

import io.github.nosequel.portage.core.handler.Handler
import java.util.Optional
import java.util.UUID
import kotlin.streams.toList

class SessionHandler : Handler {

    private val sessions: MutableSet<Session> = mutableSetOf()

    /**
     * Find a session by a unique identifier
     */
    fun find(uuid: UUID): Optional<Session> {
        return this.sessions.stream()
            .filter { it.uuid == uuid }
            .findFirst()
    }

    /**
     * Register a new session
     */
    fun register(session: Session): Session {
        return session.also {
            sessions.add(it)
        }
    }
}