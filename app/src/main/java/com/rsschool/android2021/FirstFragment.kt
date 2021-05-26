package com.rsschool.android2021

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.rsschool.android2021.databinding.FragmentFirstBinding
import java.lang.ClassCastException
import java.lang.NumberFormatException

class FirstFragment : Fragment(), OnReceiveResultListener {

    private val binding: FragmentFirstBinding by lazy {
        FragmentFirstBinding.inflate(layoutInflater)
    }

    private var listener: OnGenerateClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnGenerateClickListener
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

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        updateResult(result)
        binding.let { b ->
            b.generate.setOnClickListener {
                val min = b.minValue.getIntValue()
                val max = b.maxValue.getIntValue()
                if (min != null && max != null && validData(min, max)) {
                    listener?.onGenerateClick(min, max)
                } else {
                    Toast
                        .makeText(context, getString(R.string.invalid_data), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateResult(result: Int?) {
        binding.previousResult.text = getString(R.string.previous_result) + " " +
                (result?.toString() ?: getString(R.string.unknown))
        val text = binding.previousResult.text
    }

    private fun validData(min: Int, max: Int) : Boolean {
        return when {
            max < min || min < 0 -> false
            else -> true
        }
    }

    private fun EditText.getIntValue() : Int? {
        val str = this.text.toString()
        return try {
            str.toInt()
        } catch (e: NumberFormatException) {
            null
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }

    interface OnGenerateClickListener {
        fun onGenerateClick(min: Int, max: Int)
    }

    override fun onReceiveResult(result: Int) {
        arguments?.putInt(PREVIOUS_RESULT_KEY, result)
    }
}