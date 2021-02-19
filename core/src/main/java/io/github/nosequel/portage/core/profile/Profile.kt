package io.github.nosequel.portage.core.profile

import com.google.gson.annotations.SerializedName
import io.github.nosequel.portage.core.grant.Grant
import java.util.UUID

class Profile(@SerializedName("_id") val uuid: UUID, val name: String)