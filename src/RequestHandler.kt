package com.annotator
import io.ktor.http.Parameters

fun getAnnotation(params: Parameters, annotations: Map<VariantInfo, String> ):String {
    if (params.isEmpty()){
        return Constants.getEmptyParameters()
    }else if (!params.contains("vcf")){
        return Constants.getVcfNotExist()
    }
    else {
        val raw = params["vcf"]!!
        val parsedData = raw.split(" ")
        if (parsedData.size != 4){
            return Constants.getWrongFormat()
        }
        else {

            return try {
                val unAnnotated = VariantInfo(
                    parsedData[0],
                    parsedData[1].toInt(),
                    parsedData[2].toInt(),
                    parsedData[3]
                )
                annotations[unAnnotated].toString()
            } catch(e: java.lang.NumberFormatException){
                Constants.getWrongVcfParam()
            }

        }
    }

}