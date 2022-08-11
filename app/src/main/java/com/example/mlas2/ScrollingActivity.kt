package com.example.mlas2

import android.os.Bundle
import android.util.Patterns
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
import kotlinx.android.synthetic.main.content_scrolling.view.*

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

        nameFocusListener()
        emailFocusListener()
        numberFocusListener()
        yearFocusListener()
        //deptFocusListener()


        registerButton = findViewById(R.id.registerButton)
        registerButton.setOnClickListener {
            postEntry()
        }
    }

    /*private fun deptFocusListener() {
        binding.root.dept.setOnFocusChangeListener { _, focused ->
            if(!focused){
                binding.root.deptContainer.helperText = validateText(dept)
            }
        }
    }*/

    private fun yearFocusListener() {
        binding.root.currentYear.setOnFocusChangeListener { _, focused ->
            if(!focused){
                binding.root.currentYearContainer.helperText = validateText(currentYear)
            }
        }
    }

    private fun numberFocusListener() {
        binding.root.yourNumber.setOnFocusChangeListener { _, focused ->
            if(!focused){
                binding.root.yourNumberContainer.helperText = validatedPhone()
            }
        }
    }

    private fun emailFocusListener() {
        binding.root.yourEmail.setOnFocusChangeListener { _, focused ->
            if(!focused){
                binding.root.yourEmailContainer.helperText = validateEmail()
            }
        }
    }

    private fun nameFocusListener() {
        binding.root.name.setOnFocusChangeListener { _, focused ->
            if(!focused){
                binding.root.yourNameContainer.helperText = validateText(name)
            }
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

        val validName = binding.root.yourNameContainer.helperText == null
        val validEmail = binding.root.yourEmailContainer.helperText == null
        val validNumber = binding.root.yourNumberContainer.helperText == null
        val validYear = binding.root.currentYearContainer.helperText == null
        //val validDept = binding.root.deptContainer.helperText == null


        if(validName && validEmail && validNumber && validYear){
            realtimeDatabaseManager.addEntry(yourName, yourEmail, yourNum, yourYear, yourDept, memberText, foodText, sizeText, { showToast(getString(R.string.success)) },
                { showToast(getString(R.string.failed)) })
            resetState()
        }
        else
            invalidDetails()
    }

    private fun validateText(thisEditText: EditText): String? {
        val textText = thisEditText.text.toString()
        if(textText.isEmpty()){
            return "Enter valid ${thisEditText.hint}"
        }
        return null
    }

    private fun invalidDetails() {
        showToast(getString(R.string.incompleteCredentials))
    }

    private fun validatedPhone(): String? {
        val phoneText = binding.root.yourNumber.text.toString()
        if(!phoneText.matches(".*[0-9]*".toRegex())){
            return "Must be all digits!"
        }
        if(phoneText.length != 10){
            return "Enter a valid phone number"
        }
        return null
    }

    private fun validateEmail(): String? {
        val emailText = binding.root.yourEmail.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            return "Invalid Email Address"
        }
        return null
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