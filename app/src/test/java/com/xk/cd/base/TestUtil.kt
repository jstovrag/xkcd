package com.xk.cd.base

import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.xk.cd.data.models.comic.Comic
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.reflect.Type

object TestUtil {

    private const val mocksPath = "../app/src/test/java/com/xk/cd/mocks/"
    private const val comic = "comic.json"

    fun createComic(): Comic = loadJson(comic, object : TypeToken<Comic>() {}.type)

    private inline fun <reified T> loadJson(file: String, type: Type): T {
        val sb = StringBuilder()
        BufferedReader(InputStreamReader(FileInputStream(mocksPath + file))).use {
            var line = it.readLine()
            while (line != null) {
                sb.append(line)
                line = it.readLine()
            }
        }
        return Gson().fromJson(sb.toString(), type)
    }
}
