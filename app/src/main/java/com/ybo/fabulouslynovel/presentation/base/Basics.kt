package com.ybo.fabulouslynovel.presentation.base

import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


fun Activity.popToast(inStr:String)
{
    Toast.makeText(this,inStr, Toast.LENGTH_LONG).show()
}

fun <T : RecyclerView.ViewHolder> T.withClickListening(event: (view: View, position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(it, getAdapterPosition(), getItemViewType())
    }
    return this
}