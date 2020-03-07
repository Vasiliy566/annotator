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

            // http://127.0.0.1:8080/annotation?vcf=chr42+9411197+9411200+C
            get("/annotation"){
                call.respondText(getAnnotation(call.parameters, annotations))
            }
            // http://127.0.0.1:8080/fastAnnotation?vcf=chr42+9411197+9411200+C
            get("/fastAnnotation"){
                call.respondText(getFastAnnotation(call.parameters))
            }
        }
    }
    if (execCmd("command -v tabix") == "")
        print("tabix not found, install it befor server running")
    else server.start(wait = true)
}
