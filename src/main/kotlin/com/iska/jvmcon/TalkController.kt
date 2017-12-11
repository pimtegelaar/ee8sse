import com.iska.jvmcon.Rating
import com.iska.jvmcon.RatingBroadcaster
import com.iska.jvmcon.Talk
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.sse.Sse
import javax.ws.rs.sse.SseBroadcaster
import javax.ws.rs.sse.SseEventSink

@Path("/talks")
@Produces(MediaType.APPLICATION_JSON)
class TalkController {

    @GET
    fun talks() = listOf(Talk("1", "Bla"), Talk("1", "Bla"), Talk("1", "Bla"), Talk("1", "Bla"))

    @GET
    @Path("/{id}/ratings")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    fun eventStream(@PathParam("id") id: String, @Context eventSink: SseEventSink, @Context sse: Sse) {

        if(RatingBroadcaster.broadcaster !is SseBroadcaster)
            RatingBroadcaster.setBroadcasteer(sse.newBroadcaster(),sse)

        RatingBroadcaster.broadcaster!!.register(eventSink)

        (1..10).forEach {
            RatingBroadcaster.fireRating(Rating(id, (Math.random() * 10).toInt() + 1, Date()))
        }

//        eventSink.close()
    }

    @GET
    @Path("/test")
    fun eventStream(): String {
        if(RatingBroadcaster.broadcaster is SseBroadcaster ) {
            (1..10).forEach {
                RatingBroadcaster.fireRating(Rating("BOE", (Math.random() * 10).toInt() + 1, Date()))
            }
            return "OK"
        } else return "NOK"
    }

}