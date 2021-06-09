package me.kokumaji.warrior.textadv

import me.kokumaji.warrior.api.translation.ConsoleColor

class TextAdventure {

    companion object {

        val printSpeed = 20L
        var isRunning = true

        var health = 100

        var roomLit = false

        var adventurer: String = ""


        @JvmStatic
        fun main(args: Array<String>) {
            executeEasterEgg()
        }

        private fun executeEasterEgg() {
            printSlow(Translation.Text.DIVIDE)
            printSlow(Translation.Text.WARRIOR_HEADER_1)
            printSlow(Translation.Text.WARRIOR_HEADER_2)
            printSlow(Translation.Text.WARRIOR_HEADER_3)
            printSlow(Translation.Text.WARRIOR_HEADER_4)
            printSlow(Translation.Text.WARRIOR_HEADER_5)
            printSlow(Translation.Text.WARRIOR_HEADER_6 + " v1.0")
            printSlow("A Text-Adventure developed by Kokumaji")
            printSlow(Translation.Text.DIVIDE)

            println(" ")
            printSlow("You wake up in a dark and cold room. Upon further inspection you feel a ${highlightText("THING")}")
            printSlow("and a ${highlightText("SOMETHING")}, you begin to wonder what it could be.")

            while(isRunning) {
                firstPrompt()
                break;
            }

            println("\n")
            printSlow("Congratulations, you found my easter egg! The Kingdom will never forget you! :)")

        }

        val availableCommands = arrayOf("help", "use", "take", "look", "inspect")
        val firstPromptObjects = mutableListOf<String>("something", "thing")
        private fun firstPrompt() {
            print("> ")
            val command = readLine()!!.split(" ")
            if(command.size == 1) return unknownAction() { firstPrompt() }
            if(command[0] !in availableCommands) return unknownAction() { firstPrompt() }

            if(command[1] !in firstPromptObjects) {
                printSlow("There is no ${highlightText(command[1].toUpperCase())}")
                return firstPrompt()
            }

            val validActions = mutableListOf("take", "look")
            if(command[0] !in validActions) {
                printSlow("I cannot ${command[0]} ${highlightText(command[1].toUpperCase())}")
                return firstPrompt()
            } else {
                when(command[0]) {
                    "look" -> {
                        printSlow("It's too dark to inspect ${highlightText(command[1].toUpperCase())}")
                    } "take" -> {
                    when(command[1]) {
                        "thing" -> {
                            if(!roomLit) {
                                printSlow("You try to reach out for ${highlightText(command[1])}.")
                                printSlow("All of the sudden you feel a sharp sting and a liquid running down")
                                printSlow("your hand... ${highlightText("-10 Health")}")

                                health -= 10
                                return firstPrompt()

                            } else {
                                printSlow("You take the shiny ${"SWORD"} and feel stronger than before.")
                                printSlow("[SWORD has been added to your inventory]")
                            }
                        } "something" -> {
                        if(roomLit) {
                            printSlow("You already took the ${highlightText("LANTERN")}")
                            return firstPrompt()
                        }
                        printSlow("You try to reach out for ${highlightText(command[1])} and notice that")
                        printSlow("it has a little switch on the side, out of curiosity you press it.")
                        printSlow(" ")
                        printSlow("Light fills the room, it's a ${highlightText("LANTERN")}!")
                        printSlow("You also notice that there is a ${highlightText("SWORD")} next to you.")
                        firstPromptObjects.add("sword")

                        printSlow("You feel smart and ready for adventures... :)")
                        printSlow("[LANTERN added to your inventory]")
                        roomLit = true

                        return firstPrompt()

                    } "sword" -> {
                        if(!roomLit) {
                            printSlow("HEY! You're not supposed to know this yet!")
                            return firstPrompt()
                        } else {
                            printSlow("You take the shiny ${"SWORD"} and feel stronger than before.")
                            printSlow("[SWORD has been added to your inventory]")
                        }
                    }
                    }
                }
                }
            }

        }

        private fun unknownAction(function: Runnable) {
            printSlow("Sorry, I don't know this action.")
            function.run()
        }

        private fun highlightText(item: String): String {
            return "${ConsoleColor.RED}${item}${ConsoleColor.RESET}"
        }

        val YES_OPTIONS = arrayOf("yes", "y", "yeah", "yuh", "yeh", "mhm", "sure")
        val NO_OPTIONS = arrayOf("no", "nope", "nah", "never")

        private fun promptStart(msg: String): Boolean {
            printSlow(msg)
            print("> ")
            val answer = readLine()!!

            if (answer.toLowerCase() in YES_OPTIONS) return true
            else if (answer.toLowerCase() in NO_OPTIONS) return false
            else {
                print("Sorry, I didn't understand you.")
                return promptStart(msg)
            }
        }

        private fun promptName(msg: String): String {
            printSlow(msg)
            print("> ")
            val name = readLine()!!

            printSlow("${name}, huh? Quite a unique name for someone like you...")
            printSlow("I like it :)")

            return name

        }

        fun printSlow(str: String) {
            for(c in str.toCharArray()) {
                print(c)
                Thread.sleep(printSpeed)
            }

            print("\n")
        }

    }

}