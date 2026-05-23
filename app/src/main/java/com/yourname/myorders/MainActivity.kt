package com.yourname.myorders

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // 1. Enable Edge-to-Edge infrastructure
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 2. Dynamic Status Bar Height Calculation
        val statusBarBg = findViewById<View>(R.id.statusBarBackground)

        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { _, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())

            // This safely changes the height of your custom status view to match the exact hardware notch/height
            val params = statusBarBg.layoutParams
            params.height = statusBarInsets.top
            statusBarBg.layoutParams = params

            insets
        }

        // 3. Set background color property explicitly to your target amber-yellow hex format
        statusBarBg.setBackgroundColor(Color.parseColor("#FFC703"))

        // 4. Force system status icons (clock, battery, wifi) to dark mode appearance
        // This keeps them crisp and readable against your light #FFC703 background view
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.decorView.windowInsetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        // 5. Setup RecyclerView Container list layout mapping
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewOrders)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dummyOrders = listOf(
            Order("#ORD12345", "05 Feb, 4:46 PM", "741, Gumanwara", "00, Main Rd, Shivaji Nagar, Jhansi, Uttar Pradesh 284001, India", "₹ 229.0", "CANCELLED"),
            Order("#ORD12346", "05 Feb, 4:46 PM", "741, Gumanwara", "00, Main Rd, Shivaji Nagar, Jhansi, Uttar Pradesh 284001, India", "₹ 229.0", "CANCELLED"),
            Order("#ORD12347", "05 Feb, 4:46 PM", "332, Gumanwara", "GC72+GGV, Kamrari, Madhya Pradesh 475661, India", "₹ 1515.0", "CANCELLED"),
            Order("#ORD12348", "05 Feb, 4:46 PM", "332, Gumanwara", "GC72+GGV, Kamrari, Madhya Pradesh 475661, India", "₹ 1634.0", "COMPLETED")
        )
        recyclerView.adapter = OrdersAdapter(dummyOrders)
    }
}

// --- DATA MODEL ---
data class Order(val orderId: String, val dateTime: String, val pickupLoc: String, val dropLoc: String, val price: String, val status: String)

// --- RECYCLERVIEW ADAPTER ---
class OrdersAdapter(private val orderList: List<Order>) : RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]
        holder.tvMetaData.text = "${order.dateTime}  |  Order ID: ${order.orderId}"
        holder.tvPrice.text = order.price
        holder.tvPickup.text = order.pickupLoc
        holder.tvDrop.text = order.dropLoc
        holder.tvStatus.text = order.status
        if (order.status == "CANCELLED") {
            holder.tvStatus.setTextColor(holder.itemView.context.getColor(R.color.red))
        } else {
            holder.tvStatus.setTextColor(holder.itemView.context.getColor(R.color.green))
        }
    }
    override fun getItemCount(): Int = orderList.size
    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMetaData: TextView = itemView.findViewById(R.id.tvMetaData)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val tvPickup: TextView = itemView.findViewById(R.id.tvPickup)
        val tvDrop: TextView = itemView.findViewById(R.id.tvDrop)
        val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
    }
}