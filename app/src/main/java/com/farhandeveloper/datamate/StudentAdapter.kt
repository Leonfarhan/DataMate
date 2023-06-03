package com.farhandeveloper.datamate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    private var stdList: ArrayList<StudentModel> = ArrayList()
    private var onClickItem: ((StudentModel) -> Unit)? = null
    private var onClickDeleteItem: ((StudentModel) -> Unit)? = null

    fun addItems(items: ArrayList<StudentModel>) {
        this.stdList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (StudentModel) -> Unit) {
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (StudentModel) -> Unit) {
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_items_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val std = stdList[position]
        holder.bindView(std)
        holder.itemView.setOnClickListener { onClickItem?.invoke(std) }
        holder.bindDeleteButton(std, onClickDeleteItem)
    }

    override fun getItemCount(): Int {
        return stdList.size
    }

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var idTextView: TextView = itemView.findViewById(R.id.tvId)
        private var nameTextView: TextView = itemView.findViewById(R.id.tvName)
        private var emailTextView: TextView = itemView.findViewById(R.id.tvEmail)
        private var deleteButton: TextView = itemView.findViewById(R.id.btnDelete)

        fun bindView(std: StudentModel) {
            idTextView.text = std.id.toString()
            nameTextView.text = std.name
            emailTextView.text = std.email
        }

        fun bindDeleteButton(std: StudentModel, onClickDeleteItem: ((StudentModel) -> Unit)?) {
            deleteButton.setOnClickListener { onClickDeleteItem?.invoke(std) }
        }
    }
}