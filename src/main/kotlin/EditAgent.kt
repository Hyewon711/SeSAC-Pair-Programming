package pairExample

import pairExample.common.ConsoleReader
import java.io.*
import kotlin.system.exitProcess

class EditAgent(private val agentName: MutableList<String>, private val lineNumber: Int) : EditInterface {
    private val filePath = "src/charsFiles/agentList.dat"
    val fileIn = BufferedReader(FileReader(filePath))
    val fileOut = BufferedWriter(FileWriter(filePath, true))
    var line : String? = null

    override fun menuList() {
        println("                                                ")
        println("================================================")
        println("                                                ")
        println("                  ${agentName[0]} 관리           ")
        println("          원하는 메뉴의 숫자를 입력해주세요           ")
        println("                 1. 수정 하기                    ")
        println("                 2. 삭제 하기                    ")
        println("                 3. 뒤로 가기                    ")
        println("                                                ")
        println("================================================")
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
                println("      소속사 ${agentName[0]} 정보를 삭제했습니다.")
                println("                                                  ")

            } else {
                println("      유효하지 않은 인덱스입니다.")
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
        println("================================================")
        println("                                                ")
        println("                ${agentName[0]} 그룹 수정         ")
        println("          원하는 메뉴의 숫자를 입력해주세요           ")
        println("                  1. 회사명                      ")
        println("                  2. 주소                        ")
        println("                  3. CEO                        ")
        println("                  4. 전화번호                    ")
        println("                  5. 뒤로가기                    ")
        println("                                                ")
        println("================================================")
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
            "1" -> { /* 회사명 수정 */
                println("      수정할 회사명을 입력하세요.")
                val modifyName: String = ConsoleReader.consoleScanner()
                if (lineNumber >= 0 && lineNumber < lines.size) {
                    lines[lineNumber] = "${modifyName}:${agentName[1]}:${agentName[2]}:${agentName[3]}"
                    println("      회사명을 $modifyName 으로 수정했습니다.")
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

            "2" -> { /* 데뷔일 수정 */
                println("      수정할 주소를 입력하세요.")
                val modifyAddress: String = ConsoleReader.consoleScanner()
                if (lineNumber >= 0 && lineNumber < lines.size) {
                    lines[lineNumber] = "${agentName[0]}:${modifyAddress}:${agentName[2]}:${agentName[3]}"
                    println("      주소를 $modifyAddress 으로 수정했습니다.")
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

            "3" -> { /* CEO 수정 */
                println("      수정할 CEO를 입력하세요.")
                val modifyCEO: String = ConsoleReader.consoleScanner()
                if (lineNumber >= 0 && lineNumber < lines.size) {
                    lines[lineNumber] = "${agentName[0]}:${agentName[1]}:${modifyCEO}:${agentName[3]}"
                    println("      CEO를 $modifyCEO 으로 수정했습니다.")
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
            "4" -> { /* 전화번호 수정 */
                println("      수정할 CEO를 입력하세요.")
                val modifyTel: String = ConsoleReader.consoleScanner()
                if (lineNumber >= 0 && lineNumber < lines.size) {
                    lines[lineNumber] = "${agentName[0]}:${agentName[1]}:${agentName[2]}:${modifyTel}"
                    println("      전화번호를 $modifyTel 으로 수정했습니다.")
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
            "5" -> return
            else -> println("올바른 메뉴 번호를 선택해주세요.")
        }
    }
}
