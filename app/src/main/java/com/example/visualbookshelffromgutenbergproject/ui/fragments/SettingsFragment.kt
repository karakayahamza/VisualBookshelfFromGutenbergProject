package com.example.visualbookshelffromgutenbergproject.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.visualbookshelffromgutenbergproject.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val sharedPreferences by lazy {
        requireActivity().getPreferences(Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // SharedPreferences'tan kaydedilen tema modunu kontrol et
        val isDarkMode = sharedPreferences.getBoolean(KEY_DARK_MODE, false)

        binding.darkModeSwitch.isChecked = isDarkMode

        binding.darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Kullanıcının seçtiği tema modunu kaydet
            sharedPreferences.edit().putBoolean(KEY_DARK_MODE, isChecked).apply()
        }


        binding.showPrivacyButton.setOnClickListener {
            val fragment = PrivacyPolicyDialogFragment()
            fragment.show(requireFragmentManager(), "PrivacyPolicyDialogFragment")
        }

    }

    companion object {
        const val KEY_DARK_MODE = "dark_mode"
    }

}