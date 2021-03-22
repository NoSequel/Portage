package io.github.nosequel.portage.server.connectivity

import io.github.nosequel.portage.core.handler.Handler
import io.github.nosequel.portage.server.connectivity.impl.DefaultConnectivityListener

class ConnectivityHandler : Handler {

    var listener = DefaultConnectivityListener()

}