package com.pd.shopinglist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pd.shopinglist.R
import com.pd.shopinglist.databinding.FragmentNoteBinding


class NoteFragment : BaseFragment() {
    private lateinit var binding: FragmentNoteBinding
    override fun onCkickNew() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = NoteFragment()
    }
}