package com.annotator

import io.ktor.application.call
import io.ktor.http.Parameters
import io.ktor.response.respondText

class RequestHandler {

    companion object {
        fun getAnnotation(params: Parameters, annotations: Map<VariatnInfo, String> ):String {
            if (params.isEmpty()){
                return Constants.getEmptyParameters()
            }else if (!params.contains("vcf")){
                return Constants.getVcfNotExist()
            }
            else {

                var raw = params.get("vcf")!!

                var parsedData = raw.split(" ")
                if (parsedData.size != 4){
                    return Constants.getWrongFormat()
                }
                else {

                    try {
                        var unAnnotated = VariatnInfo(
                            parsedData[0],
                            parsedData[1].toInt(),
                            parsedData[2].toInt(),
                            parsedData[3]
                        )
                        return annotations.get(unAnnotated).toString()
                    } catch(e: java.lang.NumberFormatException){
                        return Constants.getWrongVcfParam()
                    }

                }
            }

        }
    }
}