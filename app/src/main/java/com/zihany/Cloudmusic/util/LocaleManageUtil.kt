package com.zihany.Cloudmusic.util

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import java.util.*

class LocaleManageUtil {
    companion object {
        private val TAG = "LocaleManageUtil"
        val LOCAL_CHINA = 0
        val LOCAL_ENGLISH = 1

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
            return when (SharePreferenceUtil.getInstance(context).getSelectLanguage()) {
                0 -> Locale.CHINA
                1 -> Locale.ENGLISH
                else -> {
                    if (getSystemLocale(context).language.contains("zh") ||
                            getSystemLocale(context).country.contains("CN")) {
                        Locale.CHINA
                    }else {
                        Locale.ENGLISH
                    }
                }
            }
        }

        fun getSystemLocale(context: Context): Locale {
            return SharePreferenceUtil.getInstance(context).getSystemCurrentLocal()
        }

        fun getSelectLanguage(context: Context): String {
            return when(SharePreferenceUtil.getInstance(context).getSelectLanguage()) {
                0 -> "中文"
                1 -> "English"
                else -> "中文"
            }
        }
    }
}