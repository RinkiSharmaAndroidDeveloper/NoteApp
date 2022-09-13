package com.example.notesapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.core.data.Note
import com.example.notesapp.databinding.ItemNoteBinding
import java.text.SimpleDateFormat
import java.util.*

class NotesListAdapter(private val list : ArrayList<Note>,private val action: ListAction) : RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(val binding : ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)

    fun updateNotes(note : List<Note>){
        list.clear()
        list.addAll(note)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
     val note = list[position]
        holder.binding.title.text =note.title
        holder.binding.content.text =note.content
        val dateFrt = SimpleDateFormat("MMM dd,HH:mm:ss")
        val resultDate = Date(note.updateTime)
        holder.binding.date.text = "Last Updated date: ${dateFrt.format(resultDate)}"
        holder.binding.noteLayout.setOnClickListener {
            action.click(note)
        }
        holder.binding.wordCount.text ="Word: ${note.wordCount}"
    }

    override fun getItemCount(): Int =list.size

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }

        }
    }

}