package com.example.foodfusion.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodfusion.Activites.MainActivity
import com.example.foodfusion.ViewModel.HomeViewModel
import com.example.foodfusion.adapters.MealsAdapter
import com.example.foodfusion.databinding.FragmentFavoritesBinding
import com.google.android.material.snackbar.Snackbar


class FavoritesFragment: Fragment(){
       private lateinit var binding:FragmentFavoritesBinding
       private lateinit var viewModel: HomeViewModel
       private lateinit var favouritesAdapter:MealsAdapter

       override fun onCreate(savedInstanceState: Bundle?) {
           super.onCreate(savedInstanceState)

           viewModel = (activity as MainActivity).viewModel
       }

       override fun onCreateView(
           inflater: LayoutInflater,
           container: ViewGroup?,
           savedInstanceState: Bundle?
       ): View? {
           binding = FragmentFavoritesBinding.inflate(inflater)
           return binding.root
       }

       override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
           super.onViewCreated(view, savedInstanceState)

           prepareRecyclerView()
           observeFavourites()

           val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
               ItemTouchHelper.UP or ItemTouchHelper.DOWN,
               ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
           ){
               override fun onMove(
                   recyclerView: RecyclerView,
                   viewHolder: ViewHolder,
                   target:RecyclerView.ViewHolder
               ) = true

               override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
//                   val position = viewHolder.adapterPosition
//                   viewModel.deleteMeal(favouritesAdapter.differ.currentList[position])
//
//                   Snackbar.make(requireView(),"Meal deleted",Snackbar.LENGTH_LONG).setAction(
//                       "Undo",
//                       View.OnClickListener {
//                           viewModel.insertMeal(favouritesAdapter.differ.currentList[position])
//                       }
//                   ).show()
//               }
                   val position = viewHolder.adapterPosition
                   val meal = favouritesAdapter.differ.currentList[position]
                   viewModel.deleteMeal(meal)

                   Snackbar.make(requireView(), "Meal deleted", Snackbar.LENGTH_LONG).setAction(
                       "Undo"
                   ) {
                       viewModel.insertMeal(meal)
                   }.show()
               }

           }
           ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavourites)
       }

       private fun prepareRecyclerView() {
           favouritesAdapter = MealsAdapter()
           binding.rvFavourites.apply {
               layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
               adapter = favouritesAdapter
           }
       }

       private fun observeFavourites() {
           viewModel.observeFavouriteMealsLiveData().observe(requireActivity(), Observer { meals->
              favouritesAdapter.differ.submitList(meals)
           })
       }

   }