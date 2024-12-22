package com.sample.itunes.utils

import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import android.widget.Toast
import com.sample.itunes.application.AppController
import com.sample.itunes.application.AppController.Companion.appContext
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

object CommonUI {

    fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, duration).show()
    }

    fun capitalizeWords(input: String): String {
        return input.split("-")
            .joinToString("-") { it.replaceFirstChar { char -> char.uppercase() } }
    }

    fun View.showVisible() {
        this.visibility = View.VISIBLE
    }

    fun View.showGone() {
        this.visibility = View.GONE
    }
    fun View.showInvisible() {
        this.visibility = View.INVISIBLE
    }

    fun getFileExtension(url: String): String? {
        return url.substringAfterLast('.', "").takeIf { it.isNotEmpty() }
    }

    // Check for common root files
    fun isRooted(): Boolean {
        return checkForRootMethod1() || checkForRootMethod2() || checkForRootMethod3()
    }

    // Check for files commonly associated with root
    private fun checkForRootMethod1(): Boolean {
        val paths = listOf(
            "/system/app/Superuser.apk",
            "/system/xbin/su",
            "/system/bin/su",
            "/data/local/xbin/su"
        )
        return paths.any { File(it).exists() }
    }

    // Check if the 'su' command is available in the shell
    private fun checkForRootMethod2(): Boolean {
        return try {
            val process = Runtime.getRuntime().exec(arrayOf("which", "su"))
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val result = reader.readLine()
            result != null && result.contains("su")
        } catch (e: Exception) {
            false
        }
    }

    // Check for unsafe apps (if present)
    private fun checkForRootMethod3(): Boolean {
        val suPackages = listOf("com.noshufou.android.su", "eu.chainfire.supersu", "com.koushikdutta.superuser")
        val pm = appContext.packageManager
        for (packageName in suPackages) {
            try {
                pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
                return true
            } catch (e: PackageManager.NameNotFoundException) {
                // App is not installed
            }
        }
        return false
    }

}