package com.yahmedwiem.contacts

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.yahmedwiem.contacts.databinding.ActivityMainBinding
import com.yahmedwiem.contacts.ui.ContactAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    lateinit var binding: ActivityMainBinding
    private val contactAdapter = ContactAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestPermission()

        contactAdapter.onContactClicked = {
            val intent = Intent(Intent.ACTION_VIEW)
            val uri: Uri = Uri.withAppendedPath(
                ContactsContract.Contacts.CONTENT_URI,
                java.lang.String.valueOf(it)
            )
            intent.data = uri
            startActivity(intent)
        }

        with(binding.contactListView) {
            adapter = contactAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        binding.searchInput.doOnTextChanged { text, _, _, _ -> viewModel.filter(text.toString()) }

        viewModel.viewState.observe(this) { viewState ->
            when (viewState) {
                is MainViewState.Error -> {
                    binding.requestPermission.visibility = View.GONE
                    binding.contactListView.visibility = View.GONE
                    binding.loader.visibility = View.GONE
                    Toast.makeText(
                        this@MainActivity,
                        viewState.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                MainViewState.Loading -> {
                    binding.requestPermission.visibility = View.GONE
                    binding.contactListView.visibility = View.GONE
                    binding.loader.visibility = View.VISIBLE
                }
                is MainViewState.Success -> {
                    contactAdapter.submitList(viewState.data)
                    binding.requestPermission.visibility = View.GONE
                    binding.loader.visibility = View.GONE
                    binding.contactListView.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.READ_CONTACTS
                )
            ) {
                val builder = MaterialAlertDialogBuilder(this)
                builder.setTitle("Read Contacts permission")
                builder.setPositiveButton(android.R.string.ok, null)
                builder.setMessage("Please enable access to contacts.")
                builder.setOnDismissListener {
                    requestPermissions(arrayOf(android.Manifest.permission.READ_CONTACTS), 2023)
                }
                builder.show()
            } else {
                requestPermissions(
                    arrayOf(android.Manifest.permission.READ_CONTACTS),
                    2023
                )
            }
        } else {
            viewModel.fetchContact()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            viewModel.fetchContact()
        } else {
            binding.requestPermission.visibility = View.VISIBLE
            binding.contactListView.visibility = View.GONE
            binding.loader.visibility = View.GONE
            binding.requestPermission.setOnClickListener {
                requestPermission()
            }
            Toast.makeText(this, "Permission Requested", Toast.LENGTH_SHORT).show()
        }
    }
}