package com.itmem.tot_hack.data

import com.itmem.tot_hack.domain.model.User
import com.itmem.tot_hack.domain.repository.CurrentUserProvider
import javax.inject.Inject

class CurrentUserProviderImpl @Inject constructor() : CurrentUserProvider {

    private val users = listOf(
        User(name = "Max Trofimov"),
        User(name = "Asad Trahmani"),
        User(name = "Alexey Myasnikov"),
        User(name = "Denis Zhimoedov"),
        User(name = "Ilya Ponomarenko"),
        User(name = "Tarance Leontev"),
        User(name = "Kirill Sannikov"),
    )

    private var cur = 0

    private var userCnt = 2

    override val currentUser: User
        get() = users[cur % userCnt]

    override fun swapUser() {
        cur++
    }

    override fun setUserCnt(cnt: Int) {
        userCnt = cnt
    }
}