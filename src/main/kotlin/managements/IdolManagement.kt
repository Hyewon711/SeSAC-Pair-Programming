package pairExample

import kotlinx.coroutines.runBlocking
import pairExample.common.ConsoleReader
import java.io.*

private const val filePath = "src/charsFiles/idolList.dat"
private const val filePathAgent = "src/charsFiles/agentList.dat"
private const val filePathEvent = "src/charsFiles/eventList.dat"

class IdolManagement: ManagementInterface {
    var line: String? = null

    override fun menuList() {
        println("                                                  ")
        println("=============================================================")
        println("                                                  ")
        println("                         아이돌 관리                     ")
        println("                 원하는 메뉴의 숫자를 입력해주세요           ")
        println()
        println("   1. 등록 하기    2. 목록 보기    3. 검색 하기    4. 뒤로 가기 ")
        println("                                                  ")
        println("=============================================================")

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
        // val idolAgent
        println("소속사를 입력하세요.")
        val idolAgent: String = ConsoleReader.consoleScanner()

        // 소속사 정보 파일에서 읽어오기
        val agents = readAgentsFromFile()

        // 입력받은 소속사 이름으로 소속사 객체 찾기
        val agent = agents.find { it.agentName == idolAgent }
        if (agent != null) {
            // 소속사가 존재하는 경우에만 아이돌 등록
            val idolInfo = IdolInfo(idolName, idolDebut, idolMember, idolAgent)

            with(fileOut) {
                write("${idolInfo.idolName}:${idolInfo.idolDebut}:${idolInfo.idolMember}:${idolInfo.agent}")
                newLine()
                flush()
            }
            println("아이돌이 성공적으로 등록되었습니다.")
        } else {
            println("입력한 소속사가 존재하지 않습니다. 아이돌 등록을 취소합니다.")
            return@runBlocking
        }
    }

    /* 아이돌 목록보기 */
    override fun read() = runBlocking {
        try {
            val fileIn = BufferedReader(FileReader(filePath))
            var cnt = 1
            fileIn.use { reader ->
                println("========================= 관리 목록 ==========================")
                println(" No. / 그룹명 / 데뷔일 / 멤버")
                var resultLine: String? = fileIn.readLine()
                if (resultLine == null) {
                    println("                                                  ")
                    println("                                                  ")
                    println("               등록된 그룹정보가 존재하지 않습니다.          ")
                    println("                                                  ")
                    println("                                                  ")
                }
                while (resultLine != null) {
                    var resultLineList = resultLine!!.split(":").toMutableList()
                    println(" $cnt / ${resultLineList[0]} / ${resultLineList[1]} / ${resultLineList[2]} / ${resultLineList[3]} ")
                    cnt++
                    resultLine = fileIn.readLine()
                }
                println("=============================================================")
            }
        } catch(e: IOException) {
            println("예외 발생 : ${e.message}")
        }
    }

    /* 아이돌 검색하기 */
    override fun search() {
        println("===================== 그룹명을 입력하세요 ======================")
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
                        println("그룹명: ${idolInfo[0]} | 데뷔일: ${idolInfo[1]} | 멤버: ${idolInfo[2]} | 소속사: ${idolInfo[3]}")

                        val events = readEventsFromFile() // 해당 그룹의 행사 정보 읽어오기
                        for (event in events) {
                            // 행사의 guest를 읽어온 후, String을 List로 변환한다.
                            val eventGuest = event.eventGuest.split(",").toMutableList()
                            // 변환한 리스트에서 그룹명과 일치하는 항목이 하나라도 해당된다면 해당 행사 출력
                            if (eventGuest.contains(idolInfo[0])) {
                                println("출연 행사명: ${event.eventName} | 일자: ${event.eventDate}")
                            } else {
                                println("출연 예정 행사가 없습니다.")
                            }
                        }
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
                println("=============================================================")
            }
        } catch(e: IOException) {
            println("예외 발생 : ${e.message}")
        }
    }
}

/* agentList.dat 파일에서 읽어오는 함수 */
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

/* eventList.dat 파일에서 읽어오는 함수 */
private fun readEventsFromFile(): List<EventInfo> {
    val events = mutableListOf<EventInfo>()
    try {
        val fileIn = BufferedReader(FileReader(filePathEvent))
        fileIn.use { reader ->
            var resultLine: String? = fileIn.readLine()
            while (resultLine != null) {
                val eventInfo = resultLine.split(":")
                if (eventInfo.size == 3) {
                    val event = EventInfo(eventInfo[0], eventInfo[1], eventInfo[2])
                    events.add(event)
                }
                resultLine = fileIn.readLine()
            }
        }
    } catch (e: IOException) {
        println("예외 발생 : ${e.message}")
    }
    return events
}