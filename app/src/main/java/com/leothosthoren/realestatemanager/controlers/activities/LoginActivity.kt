package com.leothosthoren.realestatemanager.controlers.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.jakewharton.rxbinding2.widget.RxTextView
import com.leothosthoren.realestatemanager.R
import com.leothosthoren.realestatemanager.utils.retryWhenError
import com.leothosthoren.realestatemanager.utils.validateEmailAddress
import com.leothosthoren.realestatemanager.utils.validatePassword
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Email
        RxTextView.afterTextChangeEvents(enterEmail)
                .skipInitialValue()
                .map {
                    emailError.error = null
                    it.view().text.toString()
                }
                .debounce(
                        400, TimeUnit.MILLISECONDS
                ).observeOn(AndroidSchedulers.mainThread())
                .compose(validateEmailAddress)
                .compose(retryWhenError {
                    emailError.error = it.message
                })
                .subscribe()

        // Password
        RxTextView.afterTextChangeEvents(enterPassword)
                .skipInitialValue()
                .map {
                    passwordError.error = null
                    it.view().text.toString()
                }
                .debounce(
                        400, TimeUnit.MILLISECONDS
                ).observeOn(AndroidSchedulers.mainThread())
                .compose(validatePassword)
                .compose(retryWhenError {
                    passwordError.error = it.message
                })
                .subscribe()
    }

    // Open realestate activity
    private fun openActivity() {
        Toast.makeText(this, "activity open", Toast.LENGTH_SHORT).show()
        val i = Intent(this@LoginActivity, RealEstateActivity::class.java)
        startActivity(i)

    }

    //----------
    // Action
    //----------
    fun signUpButton(v: View) {
        openActivity()
    }
}

