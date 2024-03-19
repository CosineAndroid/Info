package kr.cosine.info.extension

import android.content.Context
import android.widget.EditText

fun EditText.getInput(context: Context, textScope: (String) -> Unit) {
    if (text.isEmpty()) {
        context.showToast(hint.toString())
        return
    }
    textScope(text.toString())
}