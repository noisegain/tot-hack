package com.itmem.tot_hack.core

import timber.log.Timber

const val APP_TAG = "TOT_HACK"

fun log(e: Any) = Timber.tag(APP_TAG).d(e.toString())
