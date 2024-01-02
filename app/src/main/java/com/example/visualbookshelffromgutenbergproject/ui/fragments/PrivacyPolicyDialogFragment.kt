package com.example.visualbookshelffromgutenbergproject.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.visualbookshelffromgutenbergproject.databinding.FragmentPrivacyPolicyDialogBinding


class PrivacyPolicyDialogFragment : DialogFragment() {
    private var _binding: FragmentPrivacyPolicyDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentPrivacyPolicyDialogBinding.inflate(inflater, container, false)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // WebView'i yapılandırma
        binding.webView.loadUrl("file:///android_res/raw/privacy_policy.html")

        // Kapat butonuna tıklanınca pencereyi kapat
        binding.closeButton.setOnClickListener {
            dismiss()
        }
    }
}