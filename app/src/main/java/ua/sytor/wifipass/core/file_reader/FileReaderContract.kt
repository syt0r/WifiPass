package ua.sytor.wifipass.core.file_reader

interface FileReaderContract {

    interface FileReader {

        @Throws(Exception::class)
        suspend fun readFileContents(filePath: String, su: Boolean): String

    }

}