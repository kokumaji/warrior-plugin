package com.dumbdogdiner.warrior.user

import com.dumbdogdiner.warrior.api.user.WarriorUser
import java.util.*

class User(val uuid: UUID) : WarriorUser<User> {

    override fun getUniqueId(): UUID {
        return uuid
    }

}