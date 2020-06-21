package com.moriarity_code.pedalplates.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.moriarity_code.pedalplates.R
import com.moriarity_code.pedalplates.adapter.OrderHistoryParentAdapter
import com.moriarity_code.pedalplates.model.OrderHistoryChild
import com.moriarity_code.pedalplates.model.OrderHistoryParent
import org.json.JSONException

class OrderHistoryFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var recyclerAdapter: OrderHistoryParentAdapter
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var progressLayout: RelativeLayout
    private lateinit var progressBar: ProgressBar

    val orderList = arrayListOf<OrderHistoryParent>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order_history, container, false)

        progressLayout = view.findViewById(R.id.rlProgressLayout)
        progressBar = view.findViewById(R.id.progressBar)
        progressLayout.visibility = View.VISIBLE
        recyclerView = view.findViewById(R.id.recyclerParent)

        layoutManager = LinearLayoutManager(activity as Context)
        sharedPreferences = requireActivity().getSharedPreferences(
            getString(R.string.preference_file_name),
            Context.MODE_PRIVATE
        )
        val userId = sharedPreferences.getString("user_id", "0000")
        println(userId)
        val queue = Volley.newRequestQueue(activity as Context)
        val url = "http://13.235.250.119/v2/orders/fetch_result/$userId"
        val jsonObjectRequest = object : JsonObjectRequest(
            Method.GET,
            url,
            null,
            Response.Listener { response ->
                try {
                    val it = response.getJSONObject("data")
                    val success = it.getBoolean("success")
                    if (success) {
                        val data = it.getJSONArray("data")
                        for (i in 0 until data.length()) {
                            val order = data.getJSONObject(i)

                            val food = order.getJSONArray("food_items")
                            val foodList = arrayListOf<OrderHistoryChild>()
                            for (j in 0 until food.length()) {
                                val item = food.getJSONObject(j)
                                val childObject = OrderHistoryChild(
                                    item.getString("food_item_id"),
                                    item.getString("name"),
                                    item.getString("cost")
                                )
                                foodList.add(childObject)
                            }
                            val parentObject = OrderHistoryParent(
                                order.getString("order_id"),
                                order.getString("restaurant_name"),
                                order.getString("total_cost"),
                                order.getString("order_placed_at"),
                                foodList
                            )
                            orderList.add(parentObject)
                        }
                        progressLayout.visibility = View.GONE
                        recyclerAdapter = OrderHistoryParentAdapter(activity as Context, orderList)
                        recyclerView.adapter = recyclerAdapter
                        recyclerView.layoutManager = layoutManager
                    } else {
                        Toast.makeText(
                            context,
                            "You haven't ordered yet !!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: JSONException) {
                    Toast.makeText(
                        context,
                        e.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            Response.ErrorListener {
                Toast.makeText(
                    context,
                    it.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val header = HashMap<String, String>()
                header["Content-Type"] = "application/json"
                header["token"] = "ff94a4233d0da6"
                return header
            }
        }
        queue.add(jsonObjectRequest)
        return view
    }
}