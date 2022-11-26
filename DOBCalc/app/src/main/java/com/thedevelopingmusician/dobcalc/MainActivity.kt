package com.thedevelopingmusician.dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.thedevelopingmusician.dobcalc.databinding.ActivityMainBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Place today's date as the default value in the tvSelectedDate
        binding.tvSelectedDate.text = sdf.format(System.currentTimeMillis())

        val btnDatePicker : Button = findViewById(R.id.btnDatePicker)
//        tvSelectedDate = findViewById(R.id.tvSelectedDate)
//        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)

        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }

    private fun clickDatePicker(){

        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener{ _, selectedYear, selectedMonth, selectedDayOfMonth ->
                Toast.makeText(this, "Year was $selectedYear," +
                        " month was ${selectedMonth + 1}, day of month was $selectedDayOfMonth",
                    Toast.LENGTH_LONG).show()

                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"

                binding.tvSelectedDate.text = selectedDate

                val theDate = sdf.parse(selectedDate)
                //If theDate is not empty, run this code.
                theDate?.let {
                    val selectedDateInMinutes = theDate.time / 60000 //Converting milliseconds to minutes
                    val currentDate: Date? = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / 60000

                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes

                        binding.tvAgeInMinutes.text = differenceInMinutes.toString()
                    }
                }
            },
            year,
            month,
            day
        )
        dpd.datePicker.maxDate = System.currentTimeMillis() - 8640000 //Num of Millis in hour * 24
        dpd.show()


    }
}