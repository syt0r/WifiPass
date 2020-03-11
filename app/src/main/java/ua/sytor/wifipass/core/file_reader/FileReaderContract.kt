package ua.sytor.wifipass.core.file_reader

interface FileReaderContract {

    interface FileReader {

        @Throws(Exception::class)
        fun checkIsFileExists(filePath: String, su: Boolean): Boolean

        @Throws(Exception::class)
        fun readFileContents(filePath: String, su: Boolean): String

    }

}