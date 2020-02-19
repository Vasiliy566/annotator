package com.annotator

import kotlin.test.*

class ApplicationTest {
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
        assertEquals(
            VcfHandler.inputParse("chr42\t9411197\t9411200\tC\trs376129767"), SingleVariant(
                VariantInfo(
                    "chr42",
                    9411197,
                    9411200,
                    "C"
                ),
                "rs376129767"
            )
        )
    }
}
