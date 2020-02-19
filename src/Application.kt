package com.annotator
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    val annotations = VcfHandler.readDatabase("vcfdb/42demo.tsv")
    val server = embeddedServer(Netty, port = 8080) {
        routing {
            // example : http://127.0.0.1:8080/annotation?vcf=chr42+9411197+9411200+C
            get("/annotation"){
              call.respondText(getAnnotation(call.parameters, annotations))
            }
        }
    }
    server.start(wait = true)
}
