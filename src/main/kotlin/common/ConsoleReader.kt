package pairExample.common

import java.io.BufferedReader
import java.util.Scanner

class ConsoleReader {
    companion object{
        private lateinit var consoleInput: BufferedReader
        private lateinit var scanner: Scanner

        fun consoleScanner() : String {
            if(!this::scanner.isInitialized) {
                scanner = Scanner(System.`in`)
            }
            return scanner.nextLine()
        }
    }
}