package com.example.sqlite

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {

    private val db = DBHelper(this,null)
    private val role = mutableListOf(
        "Должность",
        "Дизайнер",
        "Тех. дизайнер",
        "Фронт\nразработчик",
        "Бэк\nразработчик",
        "Артдир"
    )
    val persons: MutableList<Person> = mutableListOf()

    private lateinit var toolbarTB: Toolbar
    private lateinit var nameET: EditText
    private lateinit var aegET: EditText
    private lateinit var roleS: Spinner
    private lateinit var saveBTN: Button
    private lateinit var getBTN: Button
    private lateinit var deleteBTN: Button
    private lateinit var personsLV: ListView

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()

        saveBTN.setOnClickListener{
            val name = nameET.text.toString()
            val age = aegET.text.toString()
            val role = roleS.getSelectedItem().toString()
            db.addPerson(name,age,role)
            clearFields()
        }

        getBTN.setOnClickListener{
            val cursor = db.getInfo()
            if(cursor!=null && cursor.moveToFirst()) {
                cursor.moveToFirst()
                persons.clear()
                do {
                    val name = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME))
                    val age = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_AGE))
                    val role = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_ROLE))
                    persons.add(Person(name,age,role))
                } while (cursor.moveToNext())
                reloadPersonLV()
            }
        }

        deleteBTN.setOnClickListener{
            db.removeAll()
            persons.clear()
            reloadPersonLV()
        }
    }

    fun reloadPersonLV(){
        val listAdapter = ListAdapter(this@SecondActivity, persons)
        personsLV.adapter = listAdapter
        listAdapter.notifyDataSetChanged()
    }

    private fun clearFields() {
        nameET.text.clear()
        aegET.text.clear()
        roleS.setSelection(0);
    }

    private fun init(){
        toolbarTB = findViewById(R.id.toolbarTB)
        setSupportActionBar(toolbarTB)

        nameET = findViewById(R.id.nameET)
        aegET = findViewById(R.id.aegET)
        roleS = findViewById(R.id.roleS)
        saveBTN = findViewById(R.id.saveBTN)
        getBTN = findViewById(R.id.getBTN)
        deleteBTN = findViewById(R.id.deleteBTN)
        personsLV = findViewById(R.id.personsLV)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, role)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        roleS.adapter=adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }
}