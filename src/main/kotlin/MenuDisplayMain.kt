package pairExample

import pairExample.common.ConsoleReader

class MenuDisplayMain : MenuInterface {
    override fun menuList() {
        println("                                                  ")
        println("================================================")
        println("                                                  ")
        println("            Agent Management Program            ")
        println("          원하는 메뉴의 숫자를 입력해주세요           ")
        println("                 1. 소속사 관리                   ")
        println("                 2. 아이돌 관리                   ")
        println("                 3. 행사 관리                    ")
        println("                 4. 종료하기                     ")
        println("                                                  ")
        println("================================================")
        println("                                                  ")
    }

    override fun menuSelect() {
        val line: String?
        line = ConsoleReader.consoleScanner()
        val agentManagement = AgentManagement()
        val idolManagement = IdolManagement()
        val eventManagement = EventManagement()

        when(line){
            "1" -> { /* 소속사 관리 */
                agentManagement.menuList()
                agentManagement.menuSelect()
            }
            "2" -> { /* 아이돌 관리 */
                idolManagement.menuList()
                idolManagement.menuSelect()
            }
            "3" -> { /* 행사 관리 */
                eventManagement.menuList()
                eventManagement.menuSelect()
            }
            "4" -> { /* 프로그램 종료 */
                return
            }
            else -> println("메뉴 번호를 입력해주세요.")
        }
    }

}