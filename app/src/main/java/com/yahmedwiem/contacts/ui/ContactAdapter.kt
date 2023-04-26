package com.yahmedwiem.contacts.ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.yahmedwiem.contacts.ContactDiffUtils
import com.yahmedwiem.contacts.ContactUiModel
import com.yahmedwiem.contacts.databinding.ContactItemViewBinding

class ContactAdapter :
    ListAdapter<ContactUiModel, ContactAdapter.ContactViewHolder>(ContactDiffUtils) {

    var onContactClicked: (String) -> Unit = {}

    inner class ContactViewHolder(private val binding: ContactItemViewBinding) :
        ViewHolder(binding.root) {
        fun bind(contact: ContactUiModel) {
            binding.root.setOnClickListener {
                onContactClicked(contact.id)
            }
            binding.contactFullName.text = contact.name
            binding.phoneNumber.text = contact.phone
            binding.emails.text = contact.emails
            if (contact.imageUri.isNullOrEmpty()) {
                binding.letter.visibility = View.VISIBLE
                binding.image.visibility = View.GONE
                binding.letter.text = contact.name[0].toString()
            } else {
                binding.letter.visibility = View.GONE
                binding.image.visibility = View.VISIBLE
                binding.image.load(Uri.parse(contact.imageUri))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding =
            ContactItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}