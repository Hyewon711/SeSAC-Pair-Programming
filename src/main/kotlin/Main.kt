package pairExample

fun main() {

    val menuDisplayMain = MenuDisplayMain()

    while (true) {
        menuDisplayMain.menuList()

        if (menuDisplayMain.menuSelect() == "4") {
            println("프로그램을 종료합니다.")
            break
        }
    }
}