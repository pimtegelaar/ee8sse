package com.iska.jvmcon

import java.util.*
import javax.annotation.PreDestroy
import javax.ws.rs.sse.OutboundSseEvent
import javax.ws.rs.sse.SseEventSink
import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.sse.SseBroadcaster
import javax.ws.rs.sse.Sse
import javax.inject.Singleton
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType


object RatingBroadcaster {

    var broadcaster:SseBroadcaster? = null
    var sse:Sse? = null

    fun setBroadcasteer(broadcaster:SseBroadcaster, sse:Sse) {
        this.broadcaster = broadcaster
        this.sse = sse
    }

    fun fireRating(rating: Rating) {
        broadcaster!!.broadcast(sse!!.newEventBuilder()
                .name("RatingBroadcastEvent")
                .data(rating)
                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                .build())
    }
}