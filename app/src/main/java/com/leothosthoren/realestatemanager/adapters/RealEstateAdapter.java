package com.leothosthoren.realestatemanager.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.leothosthoren.realestatemanager.R;
import com.leothosthoren.realestatemanager.entities.RealEstate;
import com.leothosthoren.realestatemanager.utils.HelperSingleton;
import com.leothosthoren.realestatemanager.views.RealEstateViewHolder;

import java.util.ArrayList;
import java.util.List;

public class RealEstateAdapter extends RecyclerView.Adapter<RealEstateViewHolder> {

    // VAR
    private List<RealEstate> realEstateList;
    private RequestManager glide;
    private Listener callback;

    public RealEstateAdapter(RequestManager glide, Listener callback) {
        this.realEstateList = new ArrayList<>();
        this.glide = glide;
        this.callback = callback;
    }

    @NonNull
    @Override
    public RealEstateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_item_real_estate_layout, parent, false);
        return new RealEstateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RealEstateViewHolder viewHolder, int position) {
        viewHolder.mRadioButtonToCheck.setVisibility(HelperSingleton.getInstance().getIsVisible() ? View.VISIBLE : View.GONE);
        viewHolder.updateWithRealEstate(this.realEstateList.get(position), this.glide, this.callback);
    }

    @Override
    public int getItemCount() {
        return realEstateList.size();
    }

    public RealEstate getRealEstate(int position) {
        return this.realEstateList.get(position);
    }

    //Interface
    public interface Listener {
        void onClickCheckButton(int position);

    }

    public void updateData(List<RealEstate> realEstates) {
        this.realEstateList = realEstates;
        this.notifyDataSetChanged();
    }
}
