package pairExample

import interfaces.MenuInterface
import pairExample.common.ConsoleReader

class MenuDisplayMain : MenuInterface {
    override fun menuList() {
        println("                                                  ")
        println("=============================================================")
        println("                                                  ")
        println("\t\t\t     Agent Management Program")
        println("\t\t       원하는 메뉴의 숫자를 입력해주세요")
        println("                                                  ")
        println("             1. 소속사 관리      2. 아이돌 관리")
        println("                                                  ")
        println("              3. 행사 관리       4. 종료하기")
        println("                                                  ")
        println("=============================================================")
        println("                                                  ")
    }

    override fun menuSelect() : String {
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

            }
            else -> println("메뉴 번호를 입력해주세요.")
        }
        return line
    }

}