package com.example.cosplan.data.Coplay.Events;

import android.app.AlertDialog;
import android.app.Application;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cosplan.R;
import com.example.cosplan.data.Coplay.Cosplay;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    List<Event> mEvents;
    private final LayoutInflater mInflater;
    private final Context mContext;
    private EventViewModel mEventViewModel;
    private Application mApplication;
    private DatePickerDialog.OnDateSetListener mStartDateSetListener, mEndDateSetListener;

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
        View itemView=holder.itemView;
        mEventViewModel=new EventViewModel(mApplication);
        switch (mCurrentEvent.mCosplayEventType) {

            case "Convention":
                itemView.findViewById(R.id.RecView_EventConvention);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateEventDialog(mCurrentEvent);
                    }
                });
                break;
            case "Photoshoot":
                itemView.findViewById(R.id.RecView_EventShoot);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateEventDialog(mCurrentEvent);
                    }
                });
                break;
            case "Charity":
                itemView.findViewById(R.id.RecView_EventCharity);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateEventDialog(mCurrentEvent);
                    }
                });
                break;
        }
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
    public void updateEventDialog(final Event TempEvent) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        final View mEventDialog = mInflater.inflate(R.layout.events_dialog, null);
        final TextView mEventTitle;
        final Spinner mEventType;
        final EditText mEventName, mEventPlace, mEventStartDate, mEventEndDate;
        final Button mEventAdd, mEventCancel;
        mEventType = mEventDialog.findViewById(R.id.Spinner_NewEventType);
        ArrayAdapter<CharSequence> mEventArrayAdapter = ArrayAdapter.createFromResource(mContext, R.array.EventType, android.R.layout.simple_spinner_item);
        mEventArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEventType.setAdapter(mEventArrayAdapter);

        mEventName = mEventDialog.findViewById(R.id.EditText_NewEventName);
        mEventPlace = mEventDialog.findViewById(R.id.EditText_NewEventPlace);
        mEventStartDate = mEventDialog.findViewById(R.id.EditText_NewEventBeginDate);
        mEventEndDate = mEventDialog.findViewById(R.id.EditText_NewEventEndDate);
        mEventAdd = mEventDialog.findViewById(R.id.Btn_NewEventAdd);
        mEventCancel = mEventDialog.findViewById(R.id.Btn_NewEventCancel);
        mEventTitle=mEventDialog.findViewById(R.id.TextView_NewEventTitle);
        mEventName.setText(TempEvent.mCosplayEventName);
        mEventPlace.setText(TempEvent.mCosplayEventPlace);
        mEventStartDate.setText(TempEvent.mCosplayEventBeginDate);
        mEventEndDate.setText(TempEvent.mCosplayEventEndDate);
        mEventType.setSelection(mEventArrayAdapter.getPosition(TempEvent.mCosplayEventType));
        mEventTitle.setText(R.string.EventUpdate);
        dialogBuilder.setView(mEventDialog);
        final Dialog dialog = dialogBuilder.create();
        dialog.show();

        //region DateListener
        //create dateSelector and add the selected date to the Edit text
        mEventStartDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    int year;
                    int month;
                    int day;
                    String mtemp = mEventStartDate.getText().toString().trim();
                    if (!checkDateFormat(mtemp)) {
                        Calendar calendar = Calendar.getInstance();
                        year = calendar.get(Calendar.YEAR);
                        month = calendar.get(Calendar.MONTH);
                        day = calendar.get(Calendar.DAY_OF_MONTH);
                    } else {
                        String mDateComlete = mEventStartDate.getText().toString();
                        String[] mDate = mDateComlete.split("/");
                        day = Integer.parseInt(mDate[0].trim());
                        month = Integer.parseInt(mDate[1].trim());
                        year = Integer.parseInt(mDate[2].trim());
                        month = month - 1;
                    }

                    DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, R.style.Theme_MaterialComponents_Light_Dialog_MinWidth, mStartDateSetListener, year, month, day);
                    datePickerDialog.getDatePicker().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    datePickerDialog.show();
                }
            }
        });
        mStartDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                mEventStartDate.setText(dayOfMonth + "/" + month + "/" + year);
            }
        };
        //create dateSelector and add the selected date to the Edit text
        mEventEndDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    int year;
                    int month;
                    int day;
                    String mtemp = mEventEndDate.getText().toString().trim();
                    if (!checkDateFormat(mtemp)) {
                        Calendar calendar = Calendar.getInstance();
                        year = calendar.get(Calendar.YEAR);
                        month = calendar.get(Calendar.MONTH);
                        day = calendar.get(Calendar.DAY_OF_MONTH);
                    } else {
                        String mDateComlete = mEventEndDate.getText().toString();
                        String[] mDate = mDateComlete.split("/");
                        day = Integer.parseInt(mDate[0].trim());
                        month = Integer.parseInt(mDate[1].trim());
                        year = Integer.parseInt(mDate[2].trim());
                        month = month - 1;
                    }

                    DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, R.style.Theme_MaterialComponents_Light_Dialog_MinWidth, mEndDateSetListener, year, month, day);
                    datePickerDialog.getDatePicker().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    datePickerDialog.show();
                }
            }
        });
        mEndDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                mEventEndDate.setText(dayOfMonth + "/" + month + "/" + year);
            }
        };
        //endregion

        mEventCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mEventAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Event mTempEvent = new Event(TempEvent.mCosplayId, TempEvent.mCosplayEventId, mEventName.getText().toString(), mEventPlace.getText().toString(), mEventStartDate.getText().toString(), mEventEndDate.getText().toString(), mEventType.getSelectedItem().toString());
                mEventViewModel.update(mTempEvent);
                dialog.dismiss();
            }
        });
    }
    public Boolean checkDateFormat(String date){
        if (date == null || !date.matches("^(1[0-9]|0[1-9]|3[0-1]|2[1-9])/(0[1-9]|1[0-2])/[0-9]{4}$"))
            return false;
        SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
        try {
            format.parse(date);
            return true;
        }catch (ParseException e){
            return false;
        }
    }
}
