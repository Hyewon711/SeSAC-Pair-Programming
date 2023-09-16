package pairExample

import kotlinx.coroutines.runBlocking
import pairExample.common.ConsoleReader
import java.io.*

class EventManagement: ManagementInterface {
    var line: String? = null
    private val filePath = "src/charsFiles/eventList.dat"

    override fun menuList() {
        println("                                                ")
        println("================================================")
        println("                                                ")
        println("                   행사 관리                      ")
        println("          원하는 메뉴의 숫자를 입력해주세요           ")
        println("                 1. 등록 하기                    ")
        println("                 2. 목록 보기                    ")
        println("                 3. 검색 하기                    ")
        println("                 4. 뒤로 가기                    ")
        println("                                                ")
        println("================================================")
        println("                                                ")
    }

    override fun menuSelect() {
        line = ConsoleReader.consoleScanner()
        when(line) {
            "1" -> register()
            "2" -> read()
            "3" -> search()
            "4" -> { return }
        }
    }

    /* 행사 등록하기 */
    override fun register() = runBlocking {
        val fileOut = BufferedWriter(FileWriter(filePath, true))
        println("행사명을 입력하세요.")
        val eventName: String = ConsoleReader.consoleScanner()
        println("행사날짜를 입력하세요.")
        val eventDate: String = ConsoleReader.consoleScanner()
        println("출연진을 콤마(,)로 입력하세요. (ex:뉴진스,르세라핌,아이브)")
        val eventGuest: String = ConsoleReader.consoleScanner()

        with(fileOut) {
            write("${eventName}:${eventDate}:${eventGuest}")
            newLine()
            flush()
        }
    }

    /* 행사 목록보기 */
    override fun read() = runBlocking {
        try {
            val fileIn = BufferedReader(FileReader(filePath))
            fileIn.use { reader ->
                println("=================== 관리 목록 ===================")
                var resultLine: String? = fileIn.readLine()
                if (resultLine == null) {
                    println("                                                  ")
                    println("                                                  ")
                    println("         등록된 행사 정보가 존재하지 않습니다.          ")
                    println("                                                  ")
                    println("                                                  ")
                }
                while (resultLine != null) {
                    println(resultLine)
                    resultLine = fileIn.readLine()
                }
                println("================================================")
            }
        } catch(e: IOException) {
            println("예외 발생 : ${e.message}")
        }
    }

    /* 소속사 검색하기 */
    override fun search() {
        println("=============== 행사명을 입력하세요 ===============")
        line = ConsoleReader.consoleScanner()
        var flag = false // 일치 여부 flag
        var lineNumber = 0 // 입력한 그룹의 행 카운트
        try {
            val fileIn = BufferedReader(FileReader(filePath))
            var resultLine: String? = fileIn.readLine()
            fileIn.use {
                while (resultLine != null) {
                    val eventInfo = resultLine!!.split(":").toMutableList()
                    lineNumber++
                    /* AgentInfo[0]은 회사명 */
                    if (eventInfo[0].trim() == line){
                        flag = true
                        println("행사명: ${eventInfo[0]} | 행사일: ${eventInfo[1]} | 출연진: ${eventInfo[2]}")
                        EditEvent(eventInfo, lineNumber-1).menuList()
                        EditEvent(eventInfo, lineNumber-1).menuSelect()
                        break
                    }
                    else{
                        resultLine = fileIn.readLine()
                    }
                }
                if(!flag) {
                    println("일치하는 소속사가 존재하지 않습니다.")
                }
                println("================================================")
            }
        } catch(e: IOException) {
            println("예외 발생 : ${e.message}")
        }
    }

}