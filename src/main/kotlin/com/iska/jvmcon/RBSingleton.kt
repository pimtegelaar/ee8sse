package com.iska.jvmcon

import javax.annotation.PostConstruct
import javax.enterprise.event.ObservesAsync
import javax.inject.Inject
import javax.inject.Singleton
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.sse.Sse
import javax.ws.rs.sse.SseBroadcaster
import javax.ws.rs.sse.SseEventSink

/**
 * Created by Jim on 11/12/2017.
 */
@Singleton
class RBSingleton {

    @Context
    private val sse: Sse? = null

    private var broadcaster: SseBroadcaster? = null

    @PostConstruct
    fun init() {
        broadcaster = sse!!.newBroadcaster()
        println("Singleton created.")
    }

    fun subscribe(eventSink: SseEventSink) {
        broadcaster!!.register(eventSink)
    }


    fun broadcast(@ObservesAsync rating: Rating) {
        RatingBroadcaster.broadcaster!!.broadcast(RatingBroadcaster.sse!!.newEventBuilder()
                .name("RatingBroadcastEvent")
                .data(rating)
                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                .build())
    }


    fun ratingsEvent(@ObservesAsync rating: Rating) {
        println(rating)
    }

}