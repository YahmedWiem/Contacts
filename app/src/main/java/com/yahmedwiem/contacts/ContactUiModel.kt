package com.yahmedwiem.contacts

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil

data class ContactUiModel(
    val id: String,
    val name: String,
    val phone: String,
    val emails: String,
    val imageUri: String
)

object ContactDiffUtils : DiffUtil.ItemCallback<ContactUiModel>() {
    override fun areItemsTheSame(oldItem: ContactUiModel, newItem: ContactUiModel) =
        newItem.id == oldItem.id

    override fun areContentsTheSame(oldItem: ContactUiModel, newItem: ContactUiModel) =
        newItem == oldItem
}
