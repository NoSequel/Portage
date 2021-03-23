package io.github.nosequel.portage.core.punishments

import io.github.nosequel.portage.core.PortageAPI
import io.github.nosequel.portage.core.database.mongo.repository.DefaultMongoRepository

class PunishmentRepository(val portageAPI: PortageAPI) : DefaultMongoRepository<Punishment>(portageAPI, "punishments", Punishment::class.java)