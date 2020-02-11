package com.annotator

import java.io.File
import java.io.InputStream

data class VariatnInfo(
    val contigName: String,
    val leftBondary: Int,
    val rightBondary: Int,
    val mutation: String
) {
    fun equals(other: VariatnInfo): Boolean {
        if (
            other.contigName.equals(contigName) &&
            other.leftBondary.equals(leftBondary)  &&
            other.rightBondary.equals(rightBondary) &&
            other.mutation.equals(mutation)
        ) {
            return true
        }
        return false
    }
}

data class SingleVariant(
    val info: VariatnInfo,
    val id: String
) {

    fun equals(other: SingleVariant): Boolean {
        if ((other.info.equals(info)) && (other.id.equals(id))) {
            return true
        }
        return false
    }
}


class VcfHandler {

    companion object {
        fun inputParse(unparsedVariant: String): SingleVariant {
            val parsedData = unparsedVariant.split("\t")
            val sv = SingleVariant(
                VariatnInfo(
                    parsedData[0],
                    parsedData[1].toInt(),
                    parsedData[2].toInt(),
                    parsedData[3]
                ),
                parsedData[4]
            )
            return sv
        }

        fun readDatabase(filename: String): Map<VariatnInfo, String> {
            val annotations = mutableMapOf<VariatnInfo, String>()
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