package pairExample

import pairExample.common.ConsoleReader
import java.io.*
import kotlin.system.exitProcess

class EditEvent(private val eventName: MutableList<String>, private val lineNumber: Int) : EditInterface {
    private val filePath = "src/charsFiles/eventList.dat"
    val fileIn = BufferedReader(FileReader(filePath))
    val fileOut = BufferedWriter(FileWriter(filePath, true))
    var line : String? = null

    override fun menuList() {
        println("                                                ")
        println("=============================================================")
        println("                                                ")
        println("                         ${eventName[0]} 관리             ")
        println("                원하는 메뉴의 숫자를 입력해주세요              ")
        println("                                                  ")
        println("          1. 수정 하기    2. 삭제 하기    3. 뒤로 가기")

        println("                                                ")
        println("=============================================================")
        println("                                                ")
    }

    override fun menuSelect() {
        line = ConsoleReader.consoleScanner()
        when(line){
            "1" -> update()
            "2" -> delete()
            "3" -> return
        }
    }

    override fun delete() {
        try {
            val lines = mutableListOf<String>()
            fileIn.use { reader ->
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    line?.let { lines.add(it) }
                }
            }

            // 지정된 인덱스의 행 삭제
            if (lineNumber >= 0 && lineNumber < lines.size) {
                lines.removeAt(lineNumber)
                println("                                                  ")
                println("=============================================================")
                println("              행사 [ ${eventName[0]} ] 정보를 삭제했습니다.     ")
                println("=============================================================")
                println("                                                  ")

            } else {
                println("유효하지 않은 인덱스입니다.")
                exitProcess(1)
            }

            // .dat 파일을 다시 리커버한다.
            BufferedWriter(FileWriter(filePath)).use { writer ->
                for (line in lines) {
                    writer.write(line)
                    writer.newLine()
                }
            }
        } catch (e: IOException) {
            println("파일을 읽거나 쓸 때 오류가 발생했습니다: ${e.message}")
        }
    }

    override fun update() {
        println("                                                ")
        println("=============================================================")
        println("                                                ")
        println("                      ${eventName[0]} 그룹 수정           ")
        println("                원하는 메뉴의 숫자를 입력해주세요              ")
        println("                                                ")
        println("             1. 행사명    2. 행사일    3. 출연진")
        println("=============================================================")
        println("                                                ")
        line = ConsoleReader.consoleScanner()
        val lines = mutableListOf<String>()
        fileIn.use { reader ->
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                line?.let { lines.add(it) }
            }
        }

        when (line) {
            "1" -> { /* 행사명 수정 */
                println("수정할 행사명을 입력하세요.      ")
                val modifyName: String = ConsoleReader.consoleScanner()
                val previousName = eventName[0]
                if (lineNumber >= 0 && lineNumber < lines.size) {
                    lines[lineNumber] = "${modifyName}:${eventName[1]}:${eventName[2]}"
                    println("회사명을 [ $previousName -> $modifyName ] 으로 수정했습니다.      ")
                } else {
                    println("유효하지 않은 인덱스입니다.")
                    exitProcess(1)
                }
                // .dat 파일을 다시 씀
                BufferedWriter(FileWriter(filePath)).use { writer ->
                    for (line in lines) {
                        writer.write(line)
                        writer.newLine()
                    }
                }
            }

            "2" -> { /* 행사일 수정 */
                println("수정할 행사일을 입력하세요.      ")
                val modifyDate: String = ConsoleReader.consoleScanner()
                val previousDate = eventName[1]
                if (lineNumber >= 0 && lineNumber < lines.size) {
                    lines[lineNumber] = "${eventName[0]}:${modifyDate}:${eventName[2]}"
                    println("행사일을 [$previousDate -> $modifyDate ] 으로 수정했습니다.      ")
                } else {
                    println("유효하지 않은 인덱스입니다.")
                    exitProcess(1)
                }
                // .dat 파일을 다시 씀
                BufferedWriter(FileWriter(filePath)).use { writer ->
                    for (line in lines) {
                        writer.write(line)
                        writer.newLine()
                    }
                }
            }

            "3" -> { /* 출연진 수정 */
                println("출연진을 콤마(,)로 입력하세요. (ex:뉴진스,르세라핌,아이브)")
                println("현재 출연진 : ${eventName[2]}")
                line = ConsoleReader.consoleScanner()
                val previousMember = eventName[2]
                val modifyGuest: List<String> = line!!.split(",")
                if (lineNumber >= 0 && lineNumber < lines.size) {
                    lines[lineNumber] = "${eventName[0]}:${eventName[1]}:${modifyGuest}"
                    println("출연진을 [ $previousMember -> $modifyGuest ] 으로 수정했습니다.")
                } else {
                    println("유효하지 않은 인덱스입니다.")
                    exitProcess(1)
                }
                // .dat 파일을 다시 씀
                BufferedWriter(FileWriter(filePath)).use { writer ->
                    for (line in lines) {
                        writer.write(line)
                        writer.newLine()
                    }
                }
            }
            "4" -> return
            else -> println("올바른 메뉴 번호를 선택해주세요.")
        }
    }
}
