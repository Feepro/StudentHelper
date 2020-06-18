package com.feepro.studenthelper.ui.homework

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.feepro.studenthelper.R

class HomeworkFragment : Fragment() {

    private lateinit var homeworkViewModel: HomeworkViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeworkViewModel =
            ViewModelProviders.of(this).get(HomeworkViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_homework, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        homeworkViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
