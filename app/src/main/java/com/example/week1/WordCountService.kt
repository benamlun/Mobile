package com.example.week1

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class WordCountService : Service() {
    private val binder = LocalBinder()

    inner class LocalBinder : Binder() {
        fun getService(): WordCountService = this@WordCountService
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    fun countWords(note: String): Int {
        if (note.isBlank()) return 0
        return note.trim().split("\\s+".toRegex()).size
    }
}