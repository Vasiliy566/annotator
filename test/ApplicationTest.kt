package com.annotator

import io.ktor.http.Parameters
import kotlin.test.Test
import kotlin.test.assertEquals

class
ApplicationTest {

    @Test
    fun testVariantInfo() {
        val ref = VariantInfo(
            "chr42",
            9411197,
            9411200,
            "C"
        )
        val refEq = VariantInfo(
            "chr42",
            9411197,
            9411200,
            "C"
        )
        val refDiff1 = VariantInfo(
            "chr41", // diff
            9411197,
            9411200,
            "C"
        )
        val refDiff2 = VariantInfo(
            "chr42",
            9411196, // diff
            9411200,
            "C"
        )
        assertEquals(ref, refEq)
        assertEquals(ref == refDiff1, false)
        assertEquals(ref == refDiff2, false)
    }

    @Test
    fun testSingleVariant() {
        val ref = SingleVariant(
            VariantInfo(
                "chr42",
                9411197,
                9411200,
                "C"
            ),
            "rs376129767"
        )
        val refEq = SingleVariant(
            VariantInfo(
                "chr42",
                9411197,
                9411200,
                "C"
            ),
            "rs376129767"
        )
        val refDiff1 = SingleVariant(
            VariantInfo(
                "chr41", // diff
                9411197,
                9411200,
                "C"
            ),
            "rs376129767"
        )
        val refDiff2 = SingleVariant(
            VariantInfo(
                "chr42",
                9411197,
                9411200,
                "C"
            ),
            "rs376129768" // diff
        )
        assertEquals(ref, refEq)
        assertEquals(ref == refDiff1, false)
        assertEquals(ref == refDiff2, false)
    }

    @Test
    fun testVcfHandlerInputParse() {
        val rawTestData = arrayOf(
            "chr42\t9411197\t9411200\tC\trs376129767",
            "chr42\t9411201\t9411204\tT\trs1370531971",
            "chr42\t9411205\t9411208\tG\trs1321780858",
            "chr42\t9411209\t9411212\tG\trs1316595271",
            "chr42\t9411213\t9411216\tC\trs1393520178"
        )

        val parsedTestData = arrayOf(
            SingleVariant(
                VariantInfo(
                    "chr42",
                    9411197,
                    9411200,
                    "C"
                ),
                "rs376129767"
            ),

            SingleVariant(
                VariantInfo(
                    "chr42",
                    9411201,
                    9411204,
                    "T"
                ),
                "rs1370531971"
            ),

            SingleVariant(
                VariantInfo(
                    "chr42",
                    9411205,
                    9411208,
                    "G"
                ),
                "rs1321780858"
            ),
            SingleVariant(
                VariantInfo(
                    "chr42",
                    9411209,
                    9411212,
                    "G"
                ),
                "rs1316595271"
            ),
            SingleVariant(
                VariantInfo(
                    "chr42",
                    9411213,
                    9411216,
                    "C"
                ),
                "rs1393520178"
            )
        )
        for (i in 0..4)
            assertEquals(VcfHandler.inputParse(rawTestData[i]), parsedTestData[i])
    }

    @Test
    fun testIndexVsOriginPerf() {
        /* do VcfHandler.readDatabase ony one time when program starts,
        so don't need to consider this in performance results */

        val rawTestData = arrayOf(
            "chr42\t9411197\t9411200\tC",
            "chr42\t9411201\t9411204\tT",
            "chr42\t9440030\t9440033\tT",
            "chr42\t9455430\t9455433\tC",
            "chr42\t9503129\t9503132\tC"
        )
        val res = arrayOfNulls<String>(rawTestData.size)

        val startFast = System.currentTimeMillis()
        rawTestData.indices.forEach { i ->
            res[i] = getFastAnnotation(Parameters.build { append("vcf", rawTestData[i].replace('\t', ' ')) })
        }
        val endFast = System.currentTimeMillis() - startFast
        print("fast duration $endFast\n performance ${endFast / rawTestData.size} ms to find one \n")

        val annotations = VcfHandler.readDatabase("vcfdb/42.tsv")
        val startRef = System.currentTimeMillis()
        rawTestData.indices.forEach { i ->
            assertEquals(
                res[i],
                getAnnotation(Parameters.build { append("vcf", rawTestData[i].replace('\t', ' ')) }, annotations) + "\n"
            )
        }
        val endRef = System.currentTimeMillis() - startRef
        print("default duration $endRef\n performance ${endRef / rawTestData.size} ms in one operation \n")

    }
}