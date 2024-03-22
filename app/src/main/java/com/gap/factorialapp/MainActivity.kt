package com.gap.factorialapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.gap.factorialapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        observeViewModel()
        binding.btnCalculate.setOnClickListener {
            viewModel.calculateFactorial(binding.etValue.text.toString())
        }
    }

    private fun observeViewModel() {

        viewModel.state.observe(this) {
            when(it) {
               is Error -> {
                    Toast.makeText(this, "value is not empty", Toast.LENGTH_SHORT).show()
                }
               is Progress -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnCalculate.isEnabled = false
                }
               is Factorial -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.btnCalculate.isEnabled = true
                    binding.tvFactorial.text = it.factorial
                }
            }
        }
    }
}