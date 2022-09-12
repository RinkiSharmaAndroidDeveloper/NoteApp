package com.example.notesapp.presentation

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.core.data.Note
import com.example.notesapp.R
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
        //To creating the delete menu
        val menuHost: MenuHost = requireActivity()

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
                if (noteData.id == 0L) {
                    note.creationTime = time
                    noteViewModel.saveNote(note)
                } else {
                    note.creationTime = noteData.creationTime
                    note.id = noteData.id
                    noteData.updateTime = time
                    noteViewModel.updateCrrentNote(note)
                }

            } else {
                Navigation.findNavController(it).popBackStack()
            }
        }

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.note_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.delete_note -> {
                        if (context != null && noteData.id != 0L) {
                            AlertDialog.Builder(context!!)
                                .setTitle("Delete note")
                                .setMessage("Are you sure you want to delete this note?")
                                .setPositiveButton("Yes") { dialogInterface, i ->
                                    noteViewModel.deleteNote(noteData)
                                }
                                .setNegativeButton("Cancel") { dialogInterface, i -> }
                                .create()
                                .show()
                        }
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
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
            if (it) {
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