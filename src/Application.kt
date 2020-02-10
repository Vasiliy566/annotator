package com.annotator
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {

    val annotations = VcfHandler.readDatabase("vcfdb/42demo.tsv")
    val server = embeddedServer(Netty, port = 8080) {
        routing {
            // example : http://127.0.0.1:8080/getAnnotation?vcf=chr42+9411197+9411200+C
            get("/getAnnotation"){
                if (call.parameters.isEmpty()){
                    call.respondText(Constants.getEmptyParameters())
                }else if (!call.parameters.contains("vcf")){
                    call.respondText(Constants.getVcfNotExist())
                }
                else {

                    var raw = call.parameters.get("vcf")!!

                    var parsedData = raw.split(" ")
                    if (parsedData.size != 4){
                        call.respondText(Constants.getWrongFormat())
                    }
                    else {

                        try {
                            var unAnnotated = VariatnInfo(
                                parsedData[0],
                                parsedData[1].toInt(),
                                parsedData[2].toInt(),
                                parsedData[3]
                            )
                            call.respondText(annotations.get(unAnnotated).toString())
                        } catch(e: java.lang.NumberFormatException){
                            call.respondText(Constants.getWrongVcfParam())
                        }

                    }
                }
            }
        }
    }
    server.start(wait = true)
}