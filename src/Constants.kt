package com.annotator

class Constants {
    companion object {
        const val VCF_FORMAT =
            "{contig name}+{inclusive zero-based position of the mutation left boundary}+{exclusive zero-based position of the mutation right boundary}+{mutation nucleotide sequence within the boundaries}+{rs-identifier}"
        const val EMPTY_PARAMETERS =
            "No parameters provided.\nplease send parameter in in format:\n?vcf=$VCF_FORMAT"
        const val VCF_PARAMETER_NOT_EXIST =
            "No vcf parameter provided.\nexpected \"?vcf=$VCF_FORMAT"
        const val WRONG_FIELDS_AMOUNT =
            "VCF parameters provided in wrong format\nvcf format should have 4 fields with + between them:\n$VCF_FORMAT"
        const val WRONG_VCF_PARAMETERS =
            "Wrong VCF parameters format\n2nd and 3d parameters should be numerical"

    }
}
