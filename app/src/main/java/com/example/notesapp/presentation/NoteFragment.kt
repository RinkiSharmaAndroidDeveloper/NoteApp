package com.example.notesapp.presentation

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.core.data.Note
import com.example.notesapp.databinding.FragmentNoteBinding

import com.example.notesapp.framework.NoteViewModel


class NoteFragment : Fragment() {
    private var _bindings: FragmentNoteBinding? = null
    private val binding get() = _bindings!!
    private lateinit var noteViewModel: NoteViewModel
    private var note = Note("", "", 0L, 0L)
    private var noteData = Note("", "", 0L, 0L)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _bindings = FragmentNoteBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        arguments?.let {
           noteData = NoteFragmentArgs.fromBundle(it).note
        }

        if (noteData.id != 0L) {
            binding.titleView.setText(noteData.title)
            binding.contentView.setText(noteData.content)
        }
        binding.checkButton.setOnClickListener {
            if (binding.titleView.toString() != "" || binding.contentView.toString() != "") {
                val time = System.currentTimeMillis()
                note.title = binding.titleView.text.toString()
                note.content = binding.contentView.text.toString()
                note.updateTime = time
                if (note.creationTime == 0L) {
                    note.creationTime = time
                    noteViewModel.saveNote(note)
                }else{
                    noteViewModel.getCurrentNote(note)
                }
                
            } else {
                Navigation.findNavController(it).popBackStack()
            }
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        noteViewModel.saved.observe(viewLifecycleOwner, Observer {
            if (it) {
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(binding.titleView).popBackStack()
                hideKeyboard()
            } else {
                Toast.makeText(context, "Something went wrong,Please try again", Toast.LENGTH_SHORT)
                    .show()
            }
        })

        noteViewModel.currentNote.observe(viewLifecycleOwner, Observer {
         if(it){
             Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
             Navigation.findNavController(binding.titleView).popBackStack()
             hideKeyboard()
         }

        })
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.titleView.windowToken, 0)
    }
}