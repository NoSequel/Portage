package io.github.nosequel.portage.core.rank

import io.github.nosequel.portage.core.PortageAPI
import io.github.nosequel.portage.core.database.mongo.repository.DefaultMongoRepository

class RankRepository(portageAPI: PortageAPI) : DefaultMongoRepository<Rank>(portageAPI, "ranks", Rank::class.java)