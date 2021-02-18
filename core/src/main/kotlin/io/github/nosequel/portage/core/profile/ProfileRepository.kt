package io.github.nosequel.portage.core.profile

import io.github.nosequel.portage.core.PortageAPI
import io.github.nosequel.portage.core.database.mongo.repository.DefaultMongoRepository


// todo: properly save grants.
class ProfileRepository(portageAPI: PortageAPI) : DefaultMongoRepository<Profile>(portageAPI, "profiles", Profile::class.java)