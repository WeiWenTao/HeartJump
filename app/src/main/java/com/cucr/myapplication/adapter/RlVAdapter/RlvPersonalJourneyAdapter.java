package com.cucr.myapplication.adapter.RlVAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cucr.myapplication.R;

/**
 * Created by 911 on 2017/6/27.
 */
public class RlvPersonalJourneyAdapter extends RecyclerView.Adapter<RlvPersonalJourneyAdapter.PersonaJourneylHolder> {
    private Context context;
    public RlvPersonalJourneyAdapter(Context context) {
        this.context = context;
    }

    @Override
    public PersonaJourneylHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rlv_journey,parent,false);
        PersonaJourneylHolder holder = new PersonaJourneylHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PersonaJourneylHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }


    class PersonaJourneylHolder extends RecyclerView.ViewHolder{

        public PersonaJourneylHolder(View itemView) {
            super(itemView);
        }
    }
}
