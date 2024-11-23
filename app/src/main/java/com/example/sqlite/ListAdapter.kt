package com.example.sqlite

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ListAdapter(context: Context, personList: MutableList<Person>) :
    ArrayAdapter<Person>(context, R.layout.person_list_item, personList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val person = getItem(position)
        if (view == null) view =
            LayoutInflater.from(context).inflate(R.layout.person_list_item, parent, false)

        val nameTV = view?.findViewById<TextView>(R.id.nameTV)
        val ageTV = view?.findViewById<TextView>(R.id.ageTV)
        val roleTV = view?.findViewById<TextView>(R.id.roleTV)

        nameTV?.text = person?.name
        ageTV?.text = person?.age
        roleTV?.text = person?.role

        return view!!
    }
}