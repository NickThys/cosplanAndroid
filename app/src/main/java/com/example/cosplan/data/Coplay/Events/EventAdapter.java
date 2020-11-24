package com.example.cosplan.data.Coplay.Events;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosplan.R;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    List<Event> mEvents;
    private final LayoutInflater mInflater;
    private final Context mContext;
    private EventViewModel mEventViewModel;
    private Application mApplication;

    public EventAdapter(Context mContext, Application mApplication) {
        mInflater=LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mApplication = mApplication;
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        private final TextView mEventName,mEventDate,mEventPlace;
        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            mEventName=itemView.findViewById(R.id.TextView_EventName);
            mEventDate=itemView.findViewById(R.id.TextView_EventDate);
            mEventPlace=itemView.findViewById(R.id.TextView_EventPlace);
        }
    }

    @NonNull
    @Override
    public EventAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.event_row,parent,false  );
        return new EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.EventViewHolder holder, int position) {
        final Event mCurrentEvent=mEvents.get(position);
        holder.mEventName.setText(mCurrentEvent.mCosplayEventName);
        holder.mEventPlace.setText(mCurrentEvent.mCosplayEventPlace);
        holder.mEventDate.setText(String.format("From %s until %s", mCurrentEvent.mCosplayEventBeginDate, mCurrentEvent.mCosplayEventEndDate));
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }
    public void setEvents(List<Event> events){
        mEvents=events;
        notifyDataSetChanged();
    }
    public Event getEventAtPosition(int mPosition){return mEvents.get(mPosition);}

}
