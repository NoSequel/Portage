package io.github.nosequel.portage.server.session.task

import io.github.nosequel.portage.server.session.Session
import io.github.nosequel.portage.server.session.SessionActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.function.Consumer

class SessionChecker(session: Session, logoutCallback: Consumer<Session>) {

    init {
        GlobalScope.launch {
            delay(1200)

            if (session.lastLogin - session.lastLogout <= 0) {
                logoutCallback.accept(session)
                session.lastActivity = SessionActivity.DISCONNECT
            }
        }
    }
}