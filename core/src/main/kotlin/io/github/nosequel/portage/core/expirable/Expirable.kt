package io.github.nosequel.portage.core.expirable

import com.google.gson.annotations.SerializedName
import io.github.nosequel.portage.core.PortageConstants
import java.util.UUID

abstract class Expirable(@SerializedName("_id") val uuid: UUID, val reason: String, val executor: UUID=PortageConstants.consoleUuid, val duration: Long=-1L, val start: Long=System.currentTimeMillis()) {

    private var expired = false;
    var expirationData: ExpirationData? = null

    /**
     * Check if the punishment is active
     *
     * @return whether the punishment is active or not
     */
    open fun isActive(): Boolean {
        if (this.duration != -1L && this.duration - System.currentTimeMillis() <= 0) {
            this.expire("Expired")
        }

        return this.expired
    }

    /**
     * Expire a [Expirable] object
     */
    fun expire(reason: String) {
        this.expirationData = ExpirationData(reason, System.currentTimeMillis())
    }
}