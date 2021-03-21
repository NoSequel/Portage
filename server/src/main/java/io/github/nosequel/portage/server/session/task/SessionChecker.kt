package io.github.nosequel.portage.server.session.task

import io.github.nosequel.portage.server.session.Session
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.function.Consumer

class SessionChecker(session: Session, logoutCallback: Consumer<Session>) {

    init {
        runBlocking {
            delay(1000)

            if (session.lastLogout - session.lastLogin <= 0) {
                logoutCallback.accept(session)
            }
        }
    }
}