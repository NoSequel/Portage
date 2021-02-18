import io.github.nosequel.portage.core.PortageAPI
import io.github.nosequel.portage.core.profile.ProfileHandler
import io.github.nosequel.portage.core.rank.RankHandler
import org.junit.Test
import java.util.UUID

class RankSaveTest {

    @Test
    fun test() {
        val portageAPI = PortageAPI()
        val rankHandler: RankHandler = portageAPI.handler.findOrThrow(RankHandler::class.java)
        val profileHandler: ProfileHandler = portageAPI.handler.findOrThrow(ProfileHandler::class.java)

        println(rankHandler.findDefaultRank().name)
        println(profileHandler.load(UUID.randomUUID(), "NV6").findRelevantGrant().rank.name)
    }
}