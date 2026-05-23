package com.yourname.myorders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Blend the status bar color with the yellow header background seamlessly
        window.statusBarColor = getColor(R.color.yellow)
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        setContentView(R.layout.activity_main)

        // 1. Find the RecyclerView from activity_main.xml
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewOrders)

        // 2. Set the LayoutManager so items stack vertically
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 3. Generate sample data matching your original screenshot
        val dummyOrders = listOf(
            Order("#ORD12345", "05 Feb, 4:46 PM", "741, Gumanwara", "00, Main Rd, Shivaji Nagar, Jhansi, Uttar Pradesh 284001, India", "₹ 229.0", "CANCELLED"),
            Order("#ORD12346", "05 Feb, 4:46 PM", "741, Gumanwara", "00, Main Rd, Shivaji Nagar, Jhansi, Uttar Pradesh 284001, India", "₹ 229.0", "CANCELLED"),
            Order("#ORD12347", "05 Feb, 4:46 PM", "332, Gumanwara", "GC72+GGV, Kamrari, Madhya Pradesh 475661, India", "₹ 1515.0", "CANCELLED"),
            Order("#ORD12348", "05 Feb, 4:46 PM", "332, Gumanwara", "GC72+GGV, Kamrari, Madhya Pradesh 475661, India", "₹ 1634.0", "COMPLETED")
        )

        // 4. Attach the adapter to the RecyclerView
        recyclerView.adapter = OrdersAdapter(dummyOrders)
    }
}

// --- 1. DATA MODEL ---
data class Order(
    val orderId: String,
    val dateTime: String,
    val pickupLoc: String,
    val dropLoc: String,
    val price: String,
    val status: String
)

// --- 2. RECYCLERVIEW ADAPTER ---
class OrdersAdapter(private val orderList: List<Order>) : RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        // Inflates your item_order.xml layout for every individual card row
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]

        // Mapping properties safely to the views
        holder.tvMetaData.text = "${order.dateTime}  |  Order ID: ${order.orderId}"
        holder.tvPrice.text = order.price
        holder.tvPickup.text = order.pickupLoc
        holder.tvDrop.text = order.dropLoc
        holder.tvStatus.text = order.status

        // Adjust text color dynamically based on state
        if (order.status == "CANCELLED") {
            holder.tvStatus.setTextColor(holder.itemView.context.getColor(R.color.red))
        } else {
            holder.tvStatus.setTextColor(holder.itemView.context.getColor(R.color.green))
        }
    }

    override fun getItemCount(): Int = orderList.size

    // --- 3. VIEWHOLDER ---
    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMetaData: TextView = itemView.findViewById(R.id.tvMetaData)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val tvPickup: TextView = itemView.findViewById(R.id.tvPickup)
        val tvDrop: TextView = itemView.findViewById(R.id.tvDrop)
        val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
    }
}