package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.rsschool.android2021.databinding.FragmentSecondBinding
import kotlin.random.Random

class SecondFragment : Fragment(), OnSystemBackPressedListener {

    private val binding: FragmentSecondBinding by lazy {
        FragmentSecondBinding.inflate(layoutInflater)
    }

    private var listener: OnBackPressedListener? = null
    private var randomResult: Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnBackPressedListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val min = arguments?.getInt(MIN_VALUE_KEY) ?: 0
        val max = arguments?.getInt(MAX_VALUE_KEY) ?: 0

        binding.apply {
            randomResult = generate(min, max)
            result.text = randomResult.toString()

            back.setOnClickListener {
                listener?.onBackPressed(randomResult)
            }
        }
    }

    private fun generate(min: Int, max: Int): Int {
        return (min..max).random()
    }

    companion object {
        @JvmStatic
        fun newInstance(min: Int, max: Int): SecondFragment {
            val fragment = SecondFragment()
            val args = Bundle()
            args.putInt(MIN_VALUE_KEY, min)
            args.putInt(MAX_VALUE_KEY, max)
            fragment.arguments = args

            return fragment
        }

        private const val MIN_VALUE_KEY = "MIN_VALUE"
        private const val MAX_VALUE_KEY = "MAX_VALUE"
    }

    interface OnBackPressedListener {
        fun onBackPressed(result: Int?)
    }

    override fun onSystemBackPressed() {
        listener?.onBackPressed(randomResult)
    }
}