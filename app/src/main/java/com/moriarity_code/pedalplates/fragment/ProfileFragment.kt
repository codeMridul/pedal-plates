package com.moriarity_code.pedalplates.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.moriarity_code.pedalplates.R

class ProfileFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var name: TextView
    private lateinit var email: TextView
    private lateinit var number: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        name = view.findViewById(R.id.name)
        email = view.findViewById(R.id.emailId)
        number = view.findViewById(R.id.mobileNumber)

        sharedPreferences = activity?.getSharedPreferences(
            getString(R.string.preference_file_name),
            Context.MODE_PRIVATE
        )!!
        name.text = sharedPreferences.getString("name", "profile_name")
        email.text = sharedPreferences.getString("email", "testing@test.com")
        number.text = sharedPreferences.getString("mobile_number", "000000000")
        return view
    }
}