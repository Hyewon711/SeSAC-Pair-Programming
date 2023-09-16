package pairExample

import kotlinx.coroutines.runBlocking
import pairExample.common.ConsoleReader
import java.io.*

class AgentManagement: ManagementInterface {
    var line: String? = null
    private val filePath = "src/charsFiles/agentList.dat"

    override fun menuList() {
        println("                                                ")
        println("================================================")
        println("                                                ")
        println("                  소속사 관리                     ")
        println("          원하는 메뉴의 숫자를 입력해주세요           ")
        println("                 1. 등록 하기                    ")
        println("                 2. 목록 보기                    ")
        println("                 3. 검색 하기                    ")
        println("                 4. 뒤로 가기                    ")
        println("                                                ")
        println("================================================")
        println("                                                ")
    }

    /* 소속사 관리 메뉴 */
    override fun menuSelect() {
        line = ConsoleReader.consoleScanner()
        when(line) {
            "1" -> register()
            "2" -> read()
            "3" -> search()
            "4" -> { return }
        }
    }

    /* 소속사 등록하기 */
    override fun register() = runBlocking {
        val fileOut = BufferedWriter(FileWriter(filePath, true))
        println("회사명을 입력하세요.")
        val agentName: String = ConsoleReader.consoleScanner()
        println("회사 주소를 입력하세요.")
        val address: String = ConsoleReader.consoleScanner()
        println("회사 CEO를 입력하세요.")
        val agentCeo: String = ConsoleReader.consoleScanner()
        println("회사 전화번호를 입력하세요.")
        val agentTel: String = ConsoleReader.consoleScanner()

        with(fileOut) {
            write("${agentName}:${address}:${agentCeo}:${agentTel}")
            newLine()
            flush()
        }
    }

    /* 소속사 목록보기 */
    override fun read() = runBlocking {
        try {
            val fileIn = BufferedReader(FileReader(filePath))
            fileIn.use { reader ->
                println("=================== 관리 목록 ===================")
                var resultLine: String? = fileIn.readLine()
                if (resultLine == null) {
                    println("                                                  ")
                    println("                                                  ")
                    println("        등록된 소속사 정보가 존재하지 않습니다.           ")
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
        println("=============== 회사명을 입력하세요 ===============")
        line = ConsoleReader.consoleScanner()
        var flag = false // 일치 여부 flag
        var lineNumber = 0 // 입력한 그룹의 행 카운트
        try {
            val fileIn = BufferedReader(FileReader(filePath))
            var resultLine: String? = fileIn.readLine()
            fileIn.use {
                while (resultLine != null) {
                    val agentInfo = resultLine!!.split(":").toMutableList()
                    lineNumber++
                    /* AgentInfo[0]은 회사명 */
                    if (agentInfo[0].trim() == line){
                        flag = true
                        println("$agentInfo")
                        EditAgent(agentInfo, lineNumber-1).menuList()
                        EditAgent(agentInfo, lineNumber-1).menuSelect()
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