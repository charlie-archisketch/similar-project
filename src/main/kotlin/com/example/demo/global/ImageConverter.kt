package com.example.demo.global

class ImageConverter {
    companion object {
        private const val PROD_CDN_URL: String = "https://resources.archisketch.com"
        private const val PROD_I_URL: String = "https://i.archisketch.com"

        private const val DEV_CDN_URL: String = "https://dev-resources.archisketch.com"
        private const val DEV_I_URL: String = "https://dev-i.archisketch.com"

        @JvmOverloads
        @JvmStatic
        fun convertImageUrl(imageUrl: String, width: Int = 512) = imageUrl.replace(PROD_CDN_URL, PROD_I_URL)
            .replace(DEV_CDN_URL, DEV_I_URL) + "?f=webp&w=$width"

        @JvmStatic
        fun recoverImageUrl(convertedImageUrl: String) = convertedImageUrl.replace(PROD_I_URL, PROD_CDN_URL)
            .replace(DEV_I_URL, DEV_CDN_URL).split('?')[0]
    }
}
