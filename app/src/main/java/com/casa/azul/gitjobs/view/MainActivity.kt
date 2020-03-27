package com.casa.azul.gitjobs.view

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.casa.azul.dogs.viewmodel.ListViewModel
import com.casa.azul.gitjobs.R


import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Listener {

    private lateinit var viewModel: ListViewModel
    private lateinit var navController: NavController

    private val STORAGE_CODE: Int = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            //system OS >= Marshmallow(6.0), check permission is enabled or not
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED
            ) {
                //permission was not granted, request it
                val permissions = arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                requestPermissions(permissions, STORAGE_CODE)
            } else {
                //permission already granted, call savePdf() method
                com.casa.azul.gitjobs.utils.savePdf()
            }
        } else {
            //system OS < marshmallow, call savePdf() method
//                savePdf()
        }


        navController = Navigation.findNavController(this, R.id.fragment)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        fab.setOnClickListener { view ->
            viewModel.menu_email.value = true
        }
        fab.hide()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_about -> {
                showDialog()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showDialog() {
        val alertDialog: AlertDialog? = this.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(R.string.dialogue_ok,
                    DialogInterface.OnClickListener { dialog, id ->
                        // User clicked OK button
                    })

            }
            // Set other dialog properties
            builder.setMessage("Developed by Manuel Carvalho")

            // Create the AlertDialog
            builder.create()
        }
        alertDialog?.show()
    }

    override fun unHideFAB(b: Boolean) {
        if (b) {
            fab.show()
        } else {
            fab.hide()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            STORAGE_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission from popup was granted, call savePdf() method
                    //savePdf()
                } else {
                    //permission from popup was denied, show error message
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
