package com.zihany.cloudmusic.util

import android.content.Context
import android.content.res.Configuration
import com.zihany.cloudmusic.base.SELECTED_LANGUAGE
import com.zihany.cloudmusic.base.SYSTEM_CURRENT_LOCAL
import java.util.*

const val TAG = "LocaleManageUtil"
const val LOCAL_CHINA = 0
const val LOCAL_ENGLISH = 1
var selectLanguage by PreferenceUtils(SELECTED_LANGUAGE, LOCAL_CHINA)

var systemCurrentLocal: Locale by PreferenceUtils(SYSTEM_CURRENT_LOCAL, Locale.CHINESE)

fun Context.setLocal(): Context {
    return updateResources(getSetLanguageLocale())
}

private fun Context.updateResources(locale: Locale): Context {
    Locale.setDefault(locale)

    val res = resources
    val config = Configuration(res.configuration)
    config.setLocale(locale)
    return createConfigurationContext(config)
}

fun Context.getSetLanguageLocale(): Locale {
    return when (selectLanguage) {
        0 -> Locale.CHINA
        1 -> Locale.ENGLISH
        else -> {
            if (systemCurrentLocal.language.contains("zh") ||
                    systemCurrentLocal.country.contains("CN")) {
                Locale.CHINA
            }else {
                Locale.ENGLISH
            }
        }
    }
}

class LocaleManageUtil {
    companion object {

        fun setLocal(context: Context): Context {
            return updateResources(context, getSetLanguageLocale(context))
        }

        private fun updateResources(context: Context, locale: Locale): Context {
            Locale.setDefault(locale)

            val res = context.resources
            val config = Configuration(res.configuration)
            config.setLocale(locale)
            return context.createConfigurationContext(config)
        }

        fun getSetLanguageLocale(context: Context): Locale {
            return when (selectLanguage) {
                0 -> Locale.CHINA
                1 -> Locale.ENGLISH
                else -> {
                    if (getSystemLocale().language.contains("zh") ||
                            getSystemLocale().country.contains("CN")) {
                        Locale.CHINA
                    }else {
                        Locale.ENGLISH
                    }
                }
            }
        }

        fun getSystemLocale(): Locale {
            return systemCurrentLocal
        }

        fun getSelectLanguage(): String {
            return when(selectLanguage) {
                0 -> "中文"
                1 -> "English"
                else -> "中文"
            }
        }
    }
}