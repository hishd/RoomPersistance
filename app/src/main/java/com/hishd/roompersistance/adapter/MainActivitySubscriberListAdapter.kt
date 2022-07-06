package com.hishd.roompersistance.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hishd.roompersistance.databinding.ListSubscriberItemBinding
import com.hishd.roompersistance.persistence.Subscriber

class MainActivitySubscriberListAdapter(private val subscriberList: List<Subscriber>): RecyclerView.Adapter<MainActivitySubscriberListAdapterViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainActivitySubscriberListAdapterViewHolder {
        val view = ListSubscriberItemBinding.inflate(LayoutInflater.from(parent.context))
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

}

class MainActivitySubscriberListAdapterViewHolder(private val binding: ListSubscriberItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bindView(subscriber: Subscriber) {
        binding.lblSubscriberID.text = "ID : ${subscriber.id}"
        binding.lblSubscriberName.text = "Name : ${subscriber.name}"
        binding.lblSubscriberEmail.text = "Email : ${subscriber.email}"
    }
}