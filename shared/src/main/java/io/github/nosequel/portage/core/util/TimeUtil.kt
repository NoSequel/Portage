package io.github.nosequel.portage.core.util

import java.util.regex.Matcher
import java.util.regex.Pattern

class TimeUtil {

    companion object {

        /**
         * Parse a [String] to a [Long]
         */
        @JvmStatic
        fun parseTime(time: String): Long {
            var totalTime = 0L
            var found = false
            val matcher: Matcher = Pattern.compile("\\d+\\D+").matcher(time)
            while (matcher.find()) {
                val s: String = matcher.group()
                val value = s.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)".toRegex()).toTypedArray()[0].toLong()

                when (s.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)".toRegex()).toTypedArray()[1]) {
                    "s" -> {
                        totalTime += value
                        found = true
                    }
                    "m" -> {
                        totalTime += value * 60
                        found = true
                    }
                    "h" -> {
                        totalTime += value * 60 * 60
                        found = true
                    }
                    "d" -> {
                        totalTime += value * 60 * 60 * 24
                        found = true
                    }
                    "w" -> {
                        totalTime += value * 60 * 60 * 24 * 7
                        found = true
                    }
                    "M" -> {
                        totalTime += value * 60 * 60 * 24 * 30
                        found = true
                    }
                    "y" -> {
                        totalTime += value * 60 * 60 * 24 * 365
                        found = true
                    }
                }
            }
            return if (!found) -1 else totalTime * 1000
        }
    }
}