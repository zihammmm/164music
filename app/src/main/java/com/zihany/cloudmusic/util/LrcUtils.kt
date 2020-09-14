package com.zihany.cloudmusic.util

import android.animation.ValueAnimator
import android.text.TextUtils
import android.text.format.DateUtils
import com.zihany.cloudmusic.song.bean.LrcEntry
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

class LrcUtils {
    companion object {
        const val TAG = "LrcUtils"

        fun parseLrc(lrcTexts: Array<String>): MutableList<LrcEntry>? {
            if (lrcTexts.size != 2 || TextUtils.isEmpty(lrcTexts[0])) {
                return null
            }
            val mainEntryList = parseLrc(lrcTexts[0])
            val secondEntryList = parseLrc(lrcTexts[1])

            if (mainEntryList != null && secondEntryList != null) {
                for (mainEntry: LrcEntry in mainEntryList) {
                    for (secondEntry: LrcEntry in secondEntryList) {
                        if (mainEntry.time == secondEntry.time) {
                            mainEntry.secondText = secondEntry.text
                        }
                    }
                }
            }
            return mainEntryList
        }

        private fun parseLrc(lrcText: String): MutableList<LrcEntry>? {
            val entryList: MutableList<LrcEntry> = ArrayList()
            val array = lrcText.split("\\n")
            for (line: String in array) {
                val list = parseLine(line)
                list?.let {
                    if (it.isNotEmpty()) {
                        entryList.addAll(it)
                    }
                }
            }
            entryList.sort()
            return entryList
        }

        private fun parseLine(line: String): MutableList<LrcEntry>? {
            val lineMatcher = Pattern.compile("((\\[\\d\\d:\\d\\d\\.\\d{2,3}\\])+)(.+)")
                    .matcher(line.trim())
            if (!lineMatcher.matches()) {
                return null
            }
            val times = lineMatcher.group(1)
            val text = lineMatcher.group(3)
            val entryList: MutableList<LrcEntry> = ArrayList()

            val timeMatcher = Pattern.compile("\\[(\\d\\d):(\\d\\d)\\.(\\d{2,3})\\]")
                    .matcher(times)
            while (timeMatcher.find()) {
                val min = timeMatcher.group(1).toLong()
                val sec = timeMatcher.group(2).toLong()
                val milString = timeMatcher.group(3)
                var mil = milString.toLong()
                if (milString.length == 2) {
                   mil *= 10
                }
                val time = min * DateUtils.MINUTE_IN_MILLIS + sec * DateUtils.SECOND_IN_MILLIS + mil
                entryList.add(LrcEntry(time, text))
            }
            return entryList
        }

        fun formatTime(milli: Long): String {
            val m = milli / DateUtils.MINUTE_IN_MILLIS
            val s = (milli / DateUtils.SECOND_IN_MILLIS) % 60
            val mm = String.format(Locale.getDefault(), "%02d", m)
            val ss = String.format(Locale.getDefault(), "%02d", s)
            return "$mm;$ss"
        }

        fun resetDurationScale() {
            try {
                val field = ValueAnimator::class.java.getDeclaredField("sDurationScale")
                field.isAccessible = true
                field.setFloat(null, 1f)
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}