package pairExample

import pairExample.common.ConsoleReader
import java.io.*
import kotlin.system.exitProcess

class EditIdol(private val idolName: MutableList<String>, private val lineNumber: Int) : EditInterface {
    private val filePath = "src/charsFiles/idolList.dat"
    val fileIn = BufferedReader(FileReader(filePath))
    val fileOut = BufferedWriter(FileWriter(filePath, true))
    var line : String? = null

    override fun menuList() {
        println("                                                  ")
        println("================================================")
        println("                                                  ")
        println("                 ${idolName[0]} 관리             ")
        println("          원하는 메뉴의 숫자를 입력해주세요           ")
        println("                 1. 수정 하기                    ")
        println("                 2. 삭제 하기                    ")
        println("                 3. 뒤로 가기                    ")
        println("                                                  ")
        println("================================================")
        println("                                                  ")
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
                println("그룹 ${idolName[0]} 정보를 삭제했습니다.")
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
        println("                                                  ")
        println("================================================")
        println("                                                  ")
        println("              ${idolName[0]} 그룹 수정           ")
        println("          원하는 메뉴의 숫자를 입력해주세요          ")
        println("                 1. 그룹명                      ")
        println("                 2. 데뷔일                      ")
        println("                  3. 멤버                       ")
        println("                                                  ")
        println("================================================")
        println("                                                  ")
        line = ConsoleReader.consoleScanner()
        val lines = mutableListOf<String>()
        fileIn.use { reader ->
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                line?.let { lines.add(it) }
            }
        }

        when (line) {
            "1" -> { /* 그룹명 수정 */
                println("수정할 그룹명을 입력하세요.")
                val modifyName: String = ConsoleReader.consoleScanner()
                if (lineNumber >= 0 && lineNumber < lines.size) {
                    lines[lineNumber] = "${modifyName}:${idolName[1]}:${idolName[2]}"
                    println("그룹명을 $modifyName 으로 수정했습니다.")
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
                println("수정할 데뷔일을 입력하세요.")
                val modifyDebut: String = ConsoleReader.consoleScanner()
                if (lineNumber >= 0 && lineNumber < lines.size) {
                    lines[lineNumber] = "${idolName[0]}:${modifyDebut}:${idolName[2]}"
                    println("데뷔일을 $modifyDebut 으로 수정했습니다.")
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

            "3" -> { /* 멤버 목록 수정 */
                println("멤버를 콤마(,)로 입력하세요. (ex:민지,하니,다니엘,해린,혜인)")
                println("현재 저장 되어있는 값 : ${idolName[2]}")
                line = ConsoleReader.consoleScanner()
                val modifyMember: List<String> = line!!.split(",")
                if (lineNumber >= 0 && lineNumber < lines.size) {
                    lines[lineNumber] = "${idolName[0]}:${idolName[1]}:${modifyMember}"
                    println("멤버를 $modifyMember 으로 수정했습니다.")
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
            else -> println("")
        }
    }
}
