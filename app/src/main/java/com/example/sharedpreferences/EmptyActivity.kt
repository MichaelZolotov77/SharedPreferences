package com.example.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sharedpreferences.databinding.ActivityEmptyBinding

const val APP_PREFERENCES = "APP_PREFERENCES"
const val PREF_SOME_TEST_VALUE = "PREF_SOME_TEST_VALUE"

class EmptyActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEmptyBinding

    private lateinit var preferences: SharedPreferences

    private val preferencesListener = SharedPreferences.OnSharedPreferenceChangeListener {preferences, key ->
        if (key == PREF_SOME_TEST_VALUE) {
            binding.currentValueFromSharedPreferencesTextView.text = preferences.getString(key, "")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmptyBinding.inflate(layoutInflater).also { setContentView(it.root) }

       preferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val currentValue = preferences.getString(PREF_SOME_TEST_VALUE, "")
        binding.valueEditText.setText(currentValue)
        binding.currentValueFromSharedPreferencesTextView.text = currentValue

        binding.saveButton.setOnClickListener {
            val value = binding.valueEditText.text.toString()
            preferences.edit()
                .putString(PREF_SOME_TEST_VALUE, value)
                .apply()
        }

        preferences.registerOnSharedPreferenceChangeListener(preferencesListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        preferences.unregisterOnSharedPreferenceChangeListener(preferencesListener)
    }
}