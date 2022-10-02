package com.itmem.tot_hack.domain.repository

import com.itmem.tot_hack.domain.model.User

interface CurrentUserProvider {
    val currentUser: User
    fun swapUser()
    fun setUserCnt(cnt: Int)
}