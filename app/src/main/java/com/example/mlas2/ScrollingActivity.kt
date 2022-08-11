package com.example.mlas2

import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.mlas2.databinding.ActivityScrollingBinding
import kotlinx.android.synthetic.main.content_scrolling.*

class ScrollingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrollingBinding

    private val realtimeDatabaseManager by lazy { DatabaseManager() }

    private lateinit var yourNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var numberEditText: EditText
    private lateinit var yearEditText: EditText
    private lateinit var deptEditText: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))

        registerButton = findViewById(R.id.registerButton)
        registerButton.setOnClickListener {
            postEntry()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if(item.itemId == R.id.action_settings){
            showInfo()
        }
        return true
    }

    private fun showInfo() {
        val dialogTitle = getString(R.string.about_title, BuildConfig.VERSION_NAME)
        val dialogMessage = getString(R.string.about_message)

        val builder = AlertDialog.Builder(this)
        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.create().show()

    }
    private fun postEntry() {
        yourNameEditText = findViewById(R.id.name)
        emailEditText = findViewById(R.id.yourEmail)
        numberEditText = findViewById(R.id.yourNumber)
        yearEditText = findViewById(R.id.currentYear)
        deptEditText = findViewById(R.id.dept)

        val checkedMembershipRadioButton = rMembership.checkedRadioButtonId
        val member = findViewById<RadioButton>(checkedMembershipRadioButton)

        val checkedFoodRadioButton = rFoodChoice.checkedRadioButtonId
        val food = findViewById<RadioButton>(checkedFoodRadioButton)

        val checkedTshirtSizeRadioButton = rgTshirtSize.checkedRadioButtonId
        val size = findViewById<RadioButton>(checkedTshirtSizeRadioButton)

        val yourName = yourNameEditText.text.toString()
        val yourEmail = emailEditText.text.toString()
        val yourNum = numberEditText.text.toString()
        val yourYear = yearEditText.text.toString()
        val yourDept = deptEditText.text.toString()
        val memberText = member.text.toString()
        val foodText = food.text.toString()
        val sizeText = size.text.toString()
        realtimeDatabaseManager.addEntry(yourName, yourEmail, yourNum, yourYear, yourDept, memberText, foodText, sizeText, { showToast(getString(R.string.success)) },
            { showToast(getString(R.string.failed)) })
        resetState()
    }

    private fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show()
    }

    private fun resetState() {
        yourNameEditText.text.clear()
        emailEditText.text.clear()
        numberEditText.text.clear()
        yearEditText.text.clear()
        deptEditText.text.clear()
    }

}