package com.app.atsz7.viewpagerautoscroll.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.apache.commons.text.StringEscapeUtils


var gson = Gson()

//convert a data class to a map
fun <T> T.serializeToMap(): HashMap<String, String> {
    return convert()
}

//convert a map to a data class
inline fun <reified T> HashMap<String, String>.toDataClass(): T {
    return convert()
}

private fun removeQuotesAndUnescape(uncleanJson: String): String? {
    val noQuotes = uncleanJson.replace("^\"|\"$".toRegex(), "")
    return StringEscapeUtils.unescapeJava(noQuotes)
}
//convert an object of type I to type O
inline fun <I, reified O> I.convert(): O {
    val json = gson.toJson(this)
    return gson.fromJson(json, object : TypeToken<HashMap<String , String>>() {}.type)
}
