package com.casa.azul.gitjobs.utils

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "Helpers"

fun saveTimeStampPrefs(time: Long, context: Context){
    val TIME_STAMP = "time_stamp"
    val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    prefs.edit(commit = true){
        putLong(TIME_STAMP, time)
    }
}

fun savePdf() {
    //create object of Document class
    val mDoc = Document()
    //pdf file name
    val mFileName = SimpleDateFormat(
        "yyyyMMdd_HHmmss",
        Locale.getDefault()
    ).format(System.currentTimeMillis())
    //pdf file path
    val mFilePath =
        Environment.getExternalStorageDirectory().toString() + "/" + mFileName + ".pdf"
    try {
        //create instance of PdfWriter class
        PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))

        //open the document for writing
        mDoc.open()

        //get text from EditText i.e. textEt
        val mText = "Test string"
        //add author of the document (metadata)
        mDoc.addAuthor("Manuel Carvalho")

        //add paragraph to the document
        mDoc.add(Paragraph(mText))

        //close document
        mDoc.close()

        //show file saved message with file name and path
        //Toast.makeText(this, "$mFileName.pdf\nis saved to\n$mFilePath", Toast.LENGTH_SHORT)
        // .show()
    } catch (e: Exception) {
        //if anything goes wrong causing exception, get and show exception message
        Log.d(TAG, e.message)
        //Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
    }


}
