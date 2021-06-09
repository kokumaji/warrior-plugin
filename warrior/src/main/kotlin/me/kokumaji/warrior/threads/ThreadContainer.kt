package me.kokumaji.warrior.threads

import me.kokumaji.HibiscusAPI.HibiscusPlugin

class ThreadContainer<T: Runnable>(val plugin: HibiscusPlugin) {

    private val taskMap: MutableMap<String, T> = HashMap()

    fun get(identifier: String): T? {
        if(taskMap[identifier] == null) return null
        return taskMap[identifier]
    }

    fun get(clazz: Class<T>): T {
        return taskMap.values.stream()
            .filter { it.javaClass == clazz }
            .findFirst().get()
    }
}
