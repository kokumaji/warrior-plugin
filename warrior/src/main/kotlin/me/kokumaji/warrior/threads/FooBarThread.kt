package me.kokumaji.warrior.threads

import me.kokumaji.HibiscusAPI.HibiscusPlugin
import me.kokumaji.HibiscusAPI.threads.HibiscusThread
import me.kokumaji.HibiscusAPI.threads.ThreadOptions

class FooBarThread(plugin: HibiscusPlugin?) : HibiscusThread("fooBarThread", plugin, ThreadOptions.REPEAT_TASK, ThreadOptions.RESTART_ON_FAILURE) {

    private val outputStrings = arrayOf("foo", "bar", "baz")
    private var pointer = 0

    override fun getInterval(): Long {
        return 40L
    }

    override fun execute() {
        
        if(pointer > 2) pointer = 0
        else pointer++

        println(outputStrings[pointer])
    }

}
