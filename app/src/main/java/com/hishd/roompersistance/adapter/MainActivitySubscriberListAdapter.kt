package com.hishd.roompersistance.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hishd.roompersistance.databinding.ListSubscriberItemBinding
import com.hishd.roompersistance.persistence.Subscriber

class MainActivitySubscriberListAdapter: RecyclerView.Adapter<MainActivitySubscriberListAdapterViewHolder>() {

    private var subscriberList: List<Subscriber> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainActivitySubscriberListAdapterViewHolder {
        val view = ListSubscriberItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainActivitySubscriberListAdapterViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MainActivitySubscriberListAdapterViewHolder,
        position: Int
    ) {
        holder.bindView(subscriberList[position])
    }

    override fun getItemCount(): Int {
        return subscriberList.count()
    }

    fun setData(subscriberList: List<Subscriber>) {
        this.subscriberList = subscriberList
        notifyDataSetChanged()
    }
}

class MainActivitySubscriberListAdapterViewHolder(private val binding: ListSubscriberItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bindView(subscriber: Subscriber) {
        binding.lblSubscriberID.text = "ID : ${subscriber.id}"
        binding.lblSubscriberName.text = "Name : ${subscriber.name}"
        binding.lblSubscriberEmail.text = "Email : ${subscriber.email}"
    }
}