package pairExample

fun main() {
    val menuDisplayMain = MenuDisplayMain()

    while (true) {
        menuDisplayMain.menuList()
        menuDisplayMain.menuSelect()
    }
}