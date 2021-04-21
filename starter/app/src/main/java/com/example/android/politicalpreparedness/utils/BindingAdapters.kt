package com.example.android.politicalpreparedness.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("dateText")
fun bindTextViewToDate(textView: TextView, date: Date) {
    textView.context
    val formatter = SimpleDateFormat("EEE, MMM dd HH:mm:ss yyyy z", Locale.getDefault())
    textView.text = formatter.format(date)
}