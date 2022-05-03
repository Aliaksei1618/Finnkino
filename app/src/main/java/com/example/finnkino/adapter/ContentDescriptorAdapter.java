package com.example.finnkino.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finnkino.R;
import com.example.finnkino.model.Event.ContentDescriptor;
import com.example.finnkino.model.Show;
import com.squareup.picasso.Picasso;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ContentDescriptorAdapter extends RecyclerView.Adapter<ContentDescriptorAdapter.ContentDescriptorViewHolder> {
//    DateTimeFormatter dtSheduleFormatter = DateTimeFormatter.ofPattern("H.mm");;
    Context context;
    List<ContentDescriptor> contentDescriptorList;
//    onHelperListener helperListener;

    public ContentDescriptorAdapter(Context context, List<ContentDescriptor> contentDescriptorList) {
        this.context = context;
        this.contentDescriptorList = contentDescriptorList;
    }

    @NonNull
    @Override
    // Дизайн отображения каждого элемента
    public ContentDescriptorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View contentDescriptorItems = LayoutInflater.from(context).inflate(R.layout.content_descriptor_item,parent,false);
        return new ContentDescriptorAdapter.ContentDescriptorViewHolder(contentDescriptorItems);
    }

    @Override
    // То что мы будем подставлять в дизайн
    public void onBindViewHolder(@NonNull ContentDescriptorViewHolder holder, int position) {
//        holder.textViewShowTime.setText(dtSheduleFormatter.format(showList.get(position).getDttmShowStart()));
//        holder.textViewShowPlace.setText(showList.get(position).getTheatreAndAuditorium());
//        holder.textViewShowLang.setText(showList.get(position).getSpokenLanguageISO());
//        holder.textViewShowTitle.setText(showList.get(position).getTitle());
//        holder.textViewShowRating.setText(showList.get(position).getRating());
//        String smallImagePortrait = showList.get(position).getSmallImagePortrait();
//        Picasso.get().load(smallImagePortrait).into(holder.imageViewSchedule);
//        holder.linearLayoutSchedule.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                helperListener.setEventFragment(showList.get(position));
//            }
//        });
        Picasso.get().load(contentDescriptorList.get(position).getContentDescriptorImageURL()).into(holder.contentDescriptor);

    }

//    @Override
//    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//        helperListener = (onHelperListener) context;
//
//    }

    @Override
    public int getItemCount() {
        return contentDescriptorList.size();
    }

    // С какими элементами в дизайне мы будем работать
    public static final class ContentDescriptorViewHolder extends RecyclerView.ViewHolder {
//        LinearLayout linearLayoutSchedule;
        ImageView contentDescriptor;
//        TextView    textViewShowTime,
//                textViewShowTitle,
//                textViewShowPlace,
//                textViewShowRating,
//                textViewShowLang;

        public ContentDescriptorViewHolder(@NonNull View itemView) {
            super(itemView);
//            linearLayoutSchedule = itemView.findViewById(R.id.ll_schedule);
            contentDescriptor = itemView.findViewById(R.id.iv_content_descriptor);
//            textViewShowTime = itemView.findViewById(R.id.tv_dttm_show_start);
//            textViewShowTitle = itemView.findViewById(R.id.tv_title);
//            textViewShowPlace = itemView.findViewById(R.id.tv_theatre_and_auditorium);
//            textViewShowRating = itemView.findViewById(R.id.tv_rating);
//            textViewShowLang = itemView.findViewById(R.id.tv_spoken_language_iso);
        }
    }
}
