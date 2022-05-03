package com.example.finnkino.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finnkino.R;
import com.example.finnkino.model.Show;
import com.squareup.picasso.Picasso;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {
    DateTimeFormatter dtSheduleFormatter = DateTimeFormatter.ofPattern("H.mm");
    Context context;
    List<Show> showList;
    onHelperListener helperListener;

    public ScheduleAdapter(Context context, List<Show> showList) {
        this.context = context;
        this.showList = showList;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View scheduleItems = LayoutInflater.from(context).inflate(R.layout.schedule_item,parent,false);
        return new ScheduleAdapter.ScheduleViewHolder(scheduleItems);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvDttmShowStart.setText(dtSheduleFormatter.format(showList.get(position).getDttmShowStart()));
        holder.tvTheatreAndAuditorium.setText(showList.get(position).getTheatreAndAuditorium());
        holder.tvSpokenLanguageIso.setText(showList.get(position).getSpokenLanguageISO());
        holder.tvTitle.setText(showList.get(position).getTitle());
        holder.tvRating.setText(showList.get(position).getRating());
        holder.tvPresentationMethod.setText(showList.get(position).getPresentationMethod());
        String smallImagePortrait = showList.get(position).getSmallImagePortrait();
        Picasso.get().load(smallImagePortrait).into(holder.ivSmallImagePortrait);
        holder.llSchedule.setOnClickListener(view -> helperListener.setEventFragment(showList.get(position)));
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        helperListener = (onHelperListener) context;
    }

    @Override
    public int getItemCount() {
        return showList.size();
    }

    public static final class ScheduleViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llSchedule;
        ImageView ivSmallImagePortrait;
        TextView tvDttmShowStart,
                tvTitle,
                tvTheatreAndAuditorium,
                tvRating,
                tvSpokenLanguageIso,
                tvPresentationMethod;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            llSchedule = itemView.findViewById(R.id.ll_schedule);
            ivSmallImagePortrait = itemView.findViewById(R.id.iv_small_image_portrait);
            tvDttmShowStart = itemView.findViewById(R.id.tv_dttm_show_start);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTheatreAndAuditorium = itemView.findViewById(R.id.tv_theatre_and_auditorium);
            tvRating = itemView.findViewById(R.id.tv_rating);
            tvSpokenLanguageIso = itemView.findViewById(R.id.tv_spoken_language_iso);
            tvPresentationMethod = itemView.findViewById(R.id.tv_presentation_method);
        }
    }
}
