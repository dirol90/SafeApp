package net.safe.openthatdoor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.adapter.view.*

class MyAdapter (private val items : ArrayList<AdapterClass>, private val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv.text = items.get(position).header
        holder.tvBody.text = items.get(position).body
        holder.tvBody.visibility = View.GONE

        holder.tv.setOnClickListener {
            if (holder.tvBody.visibility == View.VISIBLE){
                holder.tvBody.visibility = View.GONE
            } else {
                holder.tvBody.visibility = View.VISIBLE
            }
        }
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val tv = view.tv!!
    val tvBody = view.tv_text!!
}