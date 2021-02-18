package io.github.nosequel.portage.core.grant

import io.github.nosequel.portage.core.PortageAPI
import io.github.nosequel.portage.core.database.mongo.repository.DefaultMongoRepository

class GrantRepository(portageAPI: PortageAPI) : DefaultMongoRepository<Grant>(portageAPI, "grants", Grant::class.java)