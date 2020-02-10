package com.annotator

import java.io.File
import java.io.InputStream
// example
//{contig name}\t
// {inclusive zero-based position of the mutation left boundary}\t
// {exclusive zero-based position of the mutation right boundary}\t
// {mutation nucleotide sequence within the boundaries}
// \t{rs-identifier}
data class VariatnInfo(
    val contigName: String,
    val leftBondary: Int,
    val rightBondary: Int,
    val mutation: String
)
data class SingleVariant(
    val info: VariatnInfo,
    val id: String
    )

class VcfHandler {

    companion object {
        fun inputParse(unparsedVariant : String):SingleVariant {
            val parsedData = unparsedVariant.split("\t")
            val sv = SingleVariant(
                VariatnInfo(parsedData[0],
                    parsedData[1].toInt(),
                    parsedData[2].toInt(),
                    parsedData[3]),
                parsedData[4]);
            return sv
        }
        fun readDatabase(filename: String): Map<VariatnInfo, String>{
            val annotations = mutableMapOf <VariatnInfo, String> ()
            val inputStream: InputStream = File(filename).inputStream()
            val lineList = mutableListOf<String>()

            inputStream.bufferedReader().useLines {
                    lines -> lines.forEach{
                        val variant = inputParse(it)
                        annotations[variant.info] = variant.id
                    }
            }


            return annotations

        }
    }
}