package com.example.android.politicalpreparedness.utils

import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.android.politicalpreparedness.R
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("dateText")
fun bindTextViewToDate(textView: TextView, date: Date?) {
    date?.let {
        textView.context
        val formatter = SimpleDateFormat("EEE, MMM dd HH:mm:ss yyyy z", Locale.getDefault())
        textView.text = formatter.format(date)
    }
}

