package io.github.nosequel.portage.core.expirable

import com.google.gson.annotations.SerializedName
import java.util.UUID

abstract class Expirable(@SerializedName("_id") val uuid: UUID, val reason: String, var duration: Long=-1L, val start: Long=System.currentTimeMillis(), val executor: UUID) {

    var expired = false;
    var expirationData: ExpirationData? = null

    /**
     * Check if the punishment is active
     *
     * @return whether the punishment is active or not
     */
    open fun isActive(): Boolean {
        if (this.duration != -1L && this.duration - System.currentTimeMillis() <= 0) {
            this.expired = true
            this.expirationData = ExpirationData("Expired", System.currentTimeMillis())
        }

        return !this.expired
    }
}