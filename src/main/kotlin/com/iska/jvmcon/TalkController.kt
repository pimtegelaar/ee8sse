import com.iska.jvmcon.Rating
import com.iska.jvmcon.Talk
import java.util.*
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.sse.Sse
import javax.ws.rs.sse.SseEventSink


@Path("talks")
class TalkController {

    @GET
    fun talks(): List<Talk> = listOf(Talk("12345", "The fallacies of Doom"),
            Talk(",", "Exploring Java 9 Modularization"),
            Talk("", "Kotlin 102 - Beyond the basics"))

    @GET
    @Path("/{id}/ratings")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    fun eventStream(@PathParam("id") id: String, @Context eventSink: SseEventSink, @Context sse: Sse) {

        (1..50).forEach {
            eventSink.send(sse.newEventBuilder()
                    .data(Rating(id, (Math.random() * 10).toInt() + 1, Date()))
                    .mediaType(MediaType.APPLICATION_JSON_TYPE)
                    .build())
        }
        eventSink.close()
    }
}