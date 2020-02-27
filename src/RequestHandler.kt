package com.annotator
import io.ktor.http.Parameters
import java.io.IOException
import java.util.*

@Throws(IOException::class)
fun execCmd(cmd: String?): String? {
    val proc = Runtime.getRuntime().exec(cmd)
    val `is` = proc.inputStream
    val s = Scanner(`is`).useDelimiter("\\A")
    val `val`: String?
    `val` = if (s.hasNext()) {
        s.next()
    } else {
        ""
    }
    return `val`
}
fun getAnnotation(params: Parameters, annotations: Map<VariantInfo, String> ):String {
    if (params.isEmpty()){
        return Constants.EMPTY_PARAMETERS
    }else if (!params.contains("vcf")){
        return Constants.VCF_PARAMETER_NOT_EXIST
    }
    else {
        val raw = params["vcf"]!!
        val parsedData = raw.split(" ")
        if (parsedData.size != 4){
            return Constants.WRONG_FIELDS_AMOUNT
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
                Constants.WRONG_VCF_PARAMETERS
            }

        }
    }
}

fun getFastAnnotation(params: Parameters):String{
    if (params.isEmpty()){
        return Constants.EMPTY_PARAMETERS
    }else if (!params.contains("vcf")){
        return Constants.VCF_PARAMETER_NOT_EXIST
    }
    else {
        val raw = params["vcf"]!!
        val parsedData = raw.split(" ")
        return if (parsedData.size != 4){
            Constants.WRONG_FIELDS_AMOUNT
        } else {
            try {
                val res = execCmd("tabix vcfdb/42.tsv.gz ${parsedData[0]}:${parsedData[1].toInt()}-${parsedData[2].toInt()}")
                return res?.split("\t")?.last() ?: "Variant not exist"
            } catch(e: java.lang.NumberFormatException){
                Constants.WRONG_VCF_PARAMETERS
            }

        }
    }
}