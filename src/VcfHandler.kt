package com.annotator

import java.io.File
import java.io.InputStream

data class VariantInfo(
    val contigName: String,
    val leftBoundary: Int,
    val rightBoundary: Int,
    val mutation: String
)

data class SingleVariant(
    val info: VariantInfo,
    val id: String
)

class VcfHandler {
    companion object {
        fun inputParse(unparsedVariant: String): SingleVariant {
            val parsedData = unparsedVariant.split("\t")
            return SingleVariant(
                VariantInfo(
                    parsedData[0],
                    parsedData[1].toInt(),
                    parsedData[2].toInt(),
                    parsedData[3]
                ),
                parsedData[4]
            )
        }

        fun readDatabase(filename: String): Map<VariantInfo, String> {
            val annotations = mutableMapOf<VariantInfo, String>()
            val inputStream: InputStream = File(filename).inputStream()

            inputStream.bufferedReader().useLines { lines ->
                lines.forEach {
                    val variant = inputParse(it)
                    annotations[variant.info] = variant.id
                }
            }

            return annotations
        }
    }
}