package pairExample

import kotlinx.coroutines.runBlocking
import pairExample.common.ConsoleReader
import java.io.*

class IdolManagement: ManagementInterface {
    var line: String? = null
    private val filePath = "src/charsFiles/idolList.dat"

    override fun menuList() {
        println("                                                  ")
        println("================================================")
        println("                                                  ")
        println("                  아이돌 관리                     ")
        println("          원하는 메뉴의 숫자를 입력해주세요           ")
        println("                 1. 등록 하기                    ")
        println("                 2. 목록 보기                    ")
        println("                 3. 검색 하기                    ")
        println("                 4. 뒤로 가기                     ")
        println("                                                  ")
        println("================================================")
        println("                                                  ")
    }

    /* 아이돌 관리 메뉴 */
    override fun menuSelect() {
        line = ConsoleReader.consoleScanner()
        when(line) {
            "1" -> register()
            "2" -> read()
            "3" -> search()
            "4" -> { return }
        }
    }

    /* 아이돌 등록하기 */
    override fun register() = runBlocking {
        val fileOut = BufferedWriter(FileWriter(filePath, true))

        println("그룹명을 입력하세요.")
        val idolName: String = ConsoleReader.consoleScanner()
        println("데뷔일을 입력하세요.")
        val idolDebut: String = ConsoleReader.consoleScanner()
        println("멤버를 콤마(,)로 입력하세요. (ex:민지,하니,다니엘,해린,혜인)")
        line = ConsoleReader.consoleScanner()
        val idolMember: List<String> = line!!.split(",")
        val inputIdol = IdolInfo(idolName,idolDebut,idolMember)

        with(fileOut) {
            write("${inputIdol.idolName}:${inputIdol.idolDebut}:${inputIdol.idolMember}")
            newLine()
            flush()
        }
    }

    /* 아이돌 목록보기 */
    override fun read() = runBlocking {
        try {
            val fileIn = BufferedReader(FileReader(filePath))
            fileIn.use { reader ->
                println("=================== 관리 목록 ===================")
                var resultLine: String? = fileIn.readLine()
                if (resultLine == null) {
                    println("                                                  ")
                    println("                                                  ")
                    println("          등록된 그룹정보가 존재하지 않습니다.          ")
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

    /* 아이돌 검색하기 */
    override fun search() {
        println("=============== 그룹명을 입력하세요 ===============")
        line = ConsoleReader.consoleScanner()
        var flag = false // 일치 여부 flag
        var lineNumber = 0 // 입력한 그룹의 행 카운트
        try {
            val fileIn = BufferedReader(FileReader(filePath))
            var resultLine: String? = fileIn.readLine()
            fileIn.use {
                while (resultLine != null) {
                    val idolInfo = resultLine!!.split(":").toMutableList()
                    lineNumber++
                    /* idolInfo[0]은 그룹명 */
                    if (idolInfo[0].trim() == line){
                        flag = true
                        println("$idolInfo")
                        EditIdol(idolInfo, lineNumber-1).menuList()
                        EditIdol(idolInfo, lineNumber-1).menuSelect()
                        break
                    }
                    else{
                        resultLine = fileIn.readLine()
                    }
                }
                if(!flag) {
                    println("일치하는 그룹이 존재하지 않습니다.")
                }
                println("================================================")
            }
        } catch(e: IOException) {
            println("예외 발생 : ${e.message}")
        }
    }
}