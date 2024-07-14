package com.example.foodfusion.fragments



import android.content.Intent
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodfusion.Activites.CategoryMealsActivity
import com.example.foodfusion.Activites.MainActivity
import com.example.foodfusion.Activites.MealActivity
import com.example.foodfusion.R
import com.example.foodfusion.ViewModel.HomeViewModel
import com.example.foodfusion.adapters.CategoriesAdapter
import com.example.foodfusion.adapters.MostPopularAdapter
import com.example.foodfusion.databinding.FragmentHomeBinding
import com.example.foodfusion.fragments.bottomsheet.MealBottomSheetFragment
import com.example.foodfusion.pojo.MealsByCategory


import com.example.foodfusion.pojo.Meal



class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel:HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemsAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    companion object{
        const val MEAL_ID = "com.example.foodfusion.fragments.idMeal"
        const val MEAL_NAME = "com.example.foodfusion.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.foodfusion.fragments.thumbMeal"
        const val CATEGORY_NAME = "com.example.foodfusion.fragments.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        popularItemsAdapter = MostPopularAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList>{
//            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
//                if(response.body() != null){
//                    val randomMeal: Meal = response.body()!!.meals[0]
//                    Glide.with(this@HomeFragment)
//                        .load(randomMeal.strMealThumb)
//                        .into(binding.randomImg)
//
//                }else{
//                    return
//                }
//            }
//
//            override fun onFailure(call: Call<MealList>, t: Throwable) {
//               Log.d("HomeFragment", t.message.toString())
//            }
//
//        })

        preparePopularItemsRecyclerView()

        viewModel.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        viewModel.getPopularItems()
        observePopularItemsLiveData()
        onPopularItemClick()

        prepareCategoriesRecyclerView()
        viewModel.getCategories()
        observeCategoriesLiveData()

        onCategoryClick()

        onPopularItemLongClick()

        onSearchIconClick()


    }

    private fun onSearchIconClick() {
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun onPopularItemLongClick() {
        popularItemsAdapter.onLongItemClick = {meal->
            val mealBottomSheetFragment = MealBottomSheetFragment.newInstance(meal.idMeal)
            mealBottomSheetFragment.show(childFragmentManager,"Meal Info")
        }
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemCLick={ category ->

            val intent = Intent(activity,CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.categoriesRecycle.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer { categories->
                categoriesAdapter.setCategoryList(categories)


        })
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = { Meal->
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,Meal.idMeal)
            intent.putExtra(MEAL_NAME,Meal.strMeal)
            intent.putExtra(MEAL_THUMB,Meal.strMealThumb)
            startActivity(intent)

        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.popularMeal.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemsAdapter
        }
    }


    private fun observePopularItemsLiveData() {
        viewModel.observePopularItemsLiveData().observe(viewLifecycleOwner,
            { mealList->
                popularItemsAdapter.setMeals(mealList = mealList as ArrayList<MealsByCategory>)
            })
    }

    private fun onRandomMealClick() {
        binding.randomfood.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        viewModel.observeRandomMealLivedata().observe(viewLifecycleOwner,
            { meal ->
                Glide.with(this@HomeFragment)
                    .load(meal!!.strMealThumb)
                    .into(binding.randomImg)
                this.randomMeal = meal
            })
    }
}


