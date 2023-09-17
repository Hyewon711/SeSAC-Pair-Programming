package pairExample

import pairExample.common.ConsoleReader
import java.io.*
import kotlin.system.exitProcess

private const val filePathAgent = "src/charsFiles/agentList.dat"
class EditIdol(private val idolName: MutableList<String>, private val lineNumber: Int) : EditInterface {
    private val filePath = "src/charsFiles/idolList.dat"

    val fileIn = BufferedReader(FileReader(filePath))
    val fileOut = BufferedWriter(FileWriter(filePath, true))
    var line : String? = null

    override fun menuList() {
        println("                                                  ")
        println("=============================================================")
        println("                                                  ")
        println("                         [${idolName[0]}]            ")
        println("                원하는 메뉴의 숫자를 입력해주세요           ")

        println("          1. 수정 하기    2. 삭제 하기    3. 뒤로 가기")
        println("                                                  ")
        println("=============================================================")
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
                println("      그룹 ${idolName[0]} 정보를 삭제했습니다.")
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
        println("=============================================================")
        println("                                                  ")
        println("                      ${idolName[0]} 그룹 수정             ")
        println("                원하는 메뉴의 숫자를 입력해주세요            ")

        println("      1. 그룹명  2. 데뷔일  3. 멤버  4. 소속사  5. 뒤로 가기")
        println("                                                  ")
        println("=============================================================")
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
                val previousName = idolName[0]
                if (lineNumber >= 0 && lineNumber < lines.size) {
                    lines[lineNumber] = "${modifyName}:${idolName[1]}:${idolName[2]} : ${idolName[3]}"
                    println("그룹명을 [ $previousName -> $modifyName ]으로 수정했습니다.")
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
                val previousDebut = idolName[1]
                if (lineNumber >= 0 && lineNumber < lines.size) {
                    lines[lineNumber] = "${idolName[0]}:${modifyDebut}:${idolName[2]}: ${idolName[3]}"
                    println("데뷔일을 [ $previousDebut -> $modifyDebut ] 으로 수정했습니다.")
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
                val previousMember = idolName[2]
                line = ConsoleReader.consoleScanner()
                val modifyMember: List<String> = line!!.split(",")
                if (lineNumber >= 0 && lineNumber < lines.size) {
                    lines[lineNumber] = "${idolName[0]}:${idolName[1]}:${modifyMember}:${idolName[3]}"
                    println("멤버를 [$previousMember -> $modifyMember] 으로 수정했습니다.")
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
            "4" -> { /* 소속사 수정 */
                val agents = readAgentsFromFile()
                val previousAgent = idolName[3]
                println("      수정할 소속사 이름을 입력하세요 (현재 소속사 이름 : ${idolName[3]})")
                val modifyAgentName: String = ConsoleReader.consoleScanner()
                val agent = agents.find { it.agentName == modifyAgentName }

                if(agent != null){
                    if (lineNumber >= 0 && lineNumber < lines.size && agent != null) {
                        lines[lineNumber] = "${idolName[0]}:${idolName[1]}:${idolName[2]}:${modifyAgentName}"
                        println("소속사를 [ $previousAgent -> $modifyAgentName ] 으로 수정했습니다.")
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
                } else {
                    println("등록되지 않은 소속사 이름입니다.")
                    return
                }



            }
            "5" -> return
            else -> println("올바른 메뉴 번호를 선택해주세요.")
        }
    }
}

private fun readAgentsFromFile(): List<AgentInfo> {
    val agents = mutableListOf<AgentInfo>()
    try {
        val fileIn = BufferedReader(FileReader(filePathAgent))
        fileIn.use { reader ->
            var resultLine: String? = fileIn.readLine()
            while (resultLine != null) {
                val agentInfo = resultLine.split(":")
                if (agentInfo.size == 4) {
                    val agent = AgentInfo(agentInfo[0], agentInfo[1], agentInfo[2], agentInfo[3])
                    agents.add(agent)
                }
                resultLine = fileIn.readLine()
            }
        }
    } catch (e: IOException) {
        println("예외 발생 : ${e.message}")
    }
    return agents
}
