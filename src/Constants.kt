package com.annotator

class Constants {
    companion object {
        private const val VCF_FORMAT = "{contig name}+{inclusive zero-based position of the mutation left boundary}+{exclusive zero-based position of the mutation right boundary}+{mutation nucleotide sequence within the boundaries}+{rs-identifier}"
        private const val EMPTY_PARAMETERS =
            "No parameters provided.\nplease send parameter in in format:\n?vcf=" + VCF_FORMAT
        private const val VCF_PARAMETER_NOT_EXIST =
            "No vcf parameter provided.\nexpected \"?vcf="+ VCF_FORMAT
        private const val WRONG_FIELDS_AMOUNT =
            "VCF parameters provided in wrong format\nvcf format should have 4 fields with + between them:\n"+ VCF_FORMAT
        private const val WRONG_VCF_PARAMETERS =
                "Wrong VCF parameters format\n2nd and 3d parameters should be numerical"
        fun getEmptyParameters():String {
            return EMPTY_PARAMETERS
        }
        fun getVcfNotExist():String {
            return VCF_PARAMETER_NOT_EXIST
        }
        fun getWrongFormat():String {
            return WRONG_FIELDS_AMOUNT
        }
        fun getWrongVcfParam():String {
            return WRONG_VCF_PARAMETERS
        }
    }
}
