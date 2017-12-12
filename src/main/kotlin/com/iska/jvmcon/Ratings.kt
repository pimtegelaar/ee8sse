package com.iska.jvmcon

import java.util.*
import javax.enterprise.event.Event
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.sse.Sse
import javax.ws.rs.sse.SseBroadcaster
import javax.ws.rs.sse.SseEventSink

@Path("/ratings")
class Ratings @Inject constructor(val ratingBroadcaster: RBSingleton, val ratingEvent: Event<Rating>) {

    @GET
    @Path("/subscribe")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    fun eventStream(@Context eventSink: SseEventSink, @Context sse: Sse) {
        ratingBroadcaster.subscribe(eventSink)
    }



    @GET
    @Path("/new")
    fun postRating(){
        val rating = Rating("Onze ISKA", 10, Date())
        ratingBroadcaster.broadcast(rating)

        ratingEvent.fireAsync(rating)
    }

}