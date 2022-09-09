package com.example.notesapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.data.Note
import com.example.notesapp.databinding.FragmentListBinding
import com.example.notesapp.framework.ListViewModel

class ListFragment : Fragment(), ListAction {
    private val notesListAdapter = NotesListAdapter(arrayListOf(), this)
    private var _bindings: FragmentListBinding? = null
    private val binding get() = _bindings!!
    private lateinit var listViewModel: ListViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindings = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listViewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        binding.noteListView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = notesListAdapter
        }
        binding.floatingActionButton.setOnClickListener {
            goToNoteDetails()
        }
        observViewModel()
    }

    fun observViewModel() {
        listViewModel.fetched.observe(viewLifecycleOwner, Observer { notes ->
            binding.progressBar.visibility = View.GONE
            binding.noteListView.visibility = View.VISIBLE
            notesListAdapter.updateNotes(notes)
        })
    }

    override fun onResume() {
        super.onResume()
        listViewModel.getNotes()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindings = null
    }

    private fun goToNoteDetails(note: Note = Note("", "", 0L, 0L)) {
        val action = ListFragmentDirections.goToNoteFragment(note)
        Navigation.findNavController(binding.noteListView).navigate(action)
    }

    override fun click(note: Note) {
        goToNoteDetails(note)
    }
}