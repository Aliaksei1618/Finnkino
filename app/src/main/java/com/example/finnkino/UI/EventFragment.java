package com.example.finnkino.UI;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finnkino.R;
import com.example.finnkino.adapter.ContentDescriptorAdapter;
import com.example.finnkino.model.Event;
import com.example.finnkino.model.Event.Actor;
import com.example.finnkino.model.Event.ContentDescriptor;
import com.example.finnkino.model.Event.Director;
import com.example.finnkino.model.Finnkino;
import com.example.finnkino.model.Show;
import com.example.finnkino.model.User;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class EventFragment extends Fragment {
    private Context context;
    private View rootView;
    private List<Event>eventList;
    private Show currentShow;
    private Event currentEvent;
    private User currentUser;

    public void setCurrentShow(Show currentShow) {
        this.currentShow = currentShow;
    }

    //@SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_event, container, false);
        context = container.getContext();
        eventList = Finnkino.getInstance().getEventList();
        currentEvent = getCurrentEvent();
        currentUser = Finnkino.getInstance().getCurrentUser();
        DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("dd.MM\nH.mm");
//        TransitionInflater transitionInflater = TransitionInflater.from(requireContext());
//        setEnterTransition(transitionInflater.inflateTransition(R.transition.slide_right));

        ImageView ivLargeImageLandscape = rootView.findViewById(R.id.iv_large_image_landscape);
        TextView tvTitle = rootView.findViewById(R.id.tv_title);
        TextView tvOriginalTitle = rootView.findViewById(R.id.tv_original_title);
        TextView tvDttmStart = rootView.findViewById(R.id.tv_dttm_start);
        TextView tvTheatreAuditorium = rootView.findViewById(R.id.tv_theatre_auditorium);
        TextView tvSpokenLanguage = rootView.findViewById(R.id.tv_spoken_language);
        TextView tvSubtitleLanguage1 = rootView.findViewById(R.id.tv_subtitle_language1);
        TextView tvSubtitleLanguage2 = rootView.findViewById(R.id.tv_subtitle_language2);
        Button btBook = rootView.findViewById(R.id.bt_book);
        Button btBuy = rootView.findViewById(R.id.bt_buy);
        btBuy.setEnabled(false);
        RatingBar myRating = rootView.findViewById(R.id.rb_my_rating);
        myRating.setVisibility(View.INVISIBLE);
        TextView imdbRating = rootView.findViewById(R.id.tv_imdb_rating);
        ImageView ratingImage = rootView.findViewById(R.id.iv_rating_image);
        TextView type = rootView.findViewById(R.id.tv_type);
        TextView genres = rootView.findViewById(R.id.tv_genres);
        TextView productionYear = rootView.findViewById(R.id.tv_production_year);
        TextView lengthInMinutes = rootView.findViewById(R.id.tv_length_in_minutes);
        TextView directorList = rootView.findViewById(R.id.tv_director_list);
        TextView actorList = rootView.findViewById(R.id.tv_actor_list);
        TextView synopsis = rootView.findViewById(R.id.tv_synopsis);

        Picasso.get().load(currentEvent.getLargeImageLandscape()).into(ivLargeImageLandscape);
        tvTitle.setText(currentShow.getTitle());
        tvOriginalTitle.setText(currentEvent.getOriginalTitle());
        tvDttmStart.setText(dtFormatter.format(currentShow.getDttmShowStart()));
        tvTheatreAuditorium.setText(currentShow.getTheatreAndAuditorium());
        tvSpokenLanguage.setText(currentShow.getSpokenLanguage());
        checkAndSetText(tvSubtitleLanguage1, currentShow.getSubtitleLanguage1());
        checkAndSetText(tvSubtitleLanguage2, currentShow.getSubtitleLanguage2());
        imdbRating.setText(currentEvent.getImdbRating());
        Picasso.get().load(currentEvent.getRatingImageUrl()).into(ratingImage);
        setContentDescriptorRecycler();
        type.setText(currentEvent.getType());
        genres.setText(currentEvent.getGenres());
        productionYear.setText(currentEvent.getProductionYear());
        lengthInMinutes.setText(currentEvent.getLengthInMinutes() + getString(R.string.tv_minute));
        StringBuilder directorBuilder = new StringBuilder();
        for (Director director : currentEvent.getDirectors()) {
            directorBuilder.append(director.getDirectorFirstName()).append(" ").append(director.getDirectorLastName()).append("\n");
        }
        directorList.setText(directorBuilder.toString());

        StringBuilder ActorBuilder = new StringBuilder();
        for (Actor actor : currentEvent.getCast()) {
            ActorBuilder.append(actor.getActorFirstName()).append(" ").append(actor.getActorLastName()).append("\n");
        }
        actorList.setText(ActorBuilder.toString());
        synopsis.setText(currentEvent.getSynopsis());

        btBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> userEventsList = currentUser.getUserEventsList();
                userEventsList.add(currentEvent.getEventID());
                currentUser.setUserEventsList(userEventsList);
                FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUserId()).child("userEventsList").setValue(userEventsList);
            }
        });
        return rootView;
    }

    public void checkAndSetText(TextView textView, CharSequence text) {
        if (text != null) {
            textView.setText(text);
        } else {
            textView.setText("");
        }
    }

    private Event getCurrentEvent() {
        return eventList.stream().filter(e -> e.getEventID().equals(currentShow.getEventID())).findFirst().get();
    }

    public void setContentDescriptorRecycler() {
        List<ContentDescriptor> contentDescriptorList = currentEvent.getContentDescriptors();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        RecyclerView contentDescriptorRecycler = rootView.findViewById(R.id.rv_content_descriptors);
        contentDescriptorRecycler.setLayoutManager(layoutManager);
        ContentDescriptorAdapter contentDescriptorAdapter = new ContentDescriptorAdapter(context, contentDescriptorList);
        contentDescriptorRecycler.setAdapter(contentDescriptorAdapter);
    }
}