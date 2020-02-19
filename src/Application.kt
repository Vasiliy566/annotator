package com.annotator

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {

    val server = embeddedServer(Netty, port = 8080) {
        routing {
            // example :
            // http://127.0.0.1:8089/fastAnnotation?vcf=chr42+9411197+9411200+C
            get("/fastAnnotation") {
                call.respondText(getFastAnnotation(call.parameters))
            }
        }
    }
    server.start(wait = true)
}
