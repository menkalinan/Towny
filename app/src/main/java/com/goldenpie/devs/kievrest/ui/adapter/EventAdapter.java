package com.goldenpie.devs.kievrest.ui.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.goldenpie.devs.kievrest.R;
import com.goldenpie.devs.kievrest.models.EventModel;
import com.goldenpie.devs.kievrest.models.FilmModel;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private final LayoutInflater inflater;

    @Getter
    private ArrayList<EventModel> models = new ArrayList<>();
    @Getter
    private Context context;
    private int lastPosition = -1;
    @Getter
    @Setter
    private boolean hasNextPage = true;
    private AlertDialog dialog;
    private MaterialCalendarView materialCalendarView;

    public EventAdapter(ArrayList<EventModel> models, Context context) {
        addAll(models, true);
        this.context = context;
        this.inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        lastPosition = -1;
    }

    public void addAll(ArrayList<EventModel> eventModels, boolean refresh) {
        if (refresh) {
            getModels().clear();
            lastPosition = -1;
        }
        if (!getModels().containsAll(eventModels)) {
            getModels().addAll(eventModels);
            notifyDataSetChanged();
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.adapter_event_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final EventModel model = models.get(position);

        holder.title.setText(model.getFinalTitle());
        holder.description.setText(model.getClearDescription());

        if (model.getPhotos() != null && !model.getPhotos().isEmpty() && !TextUtils.isEmpty(model.getPhotos().get(0).getImageUrl())) {
            Glide.with(getContext())
                    .load(model.getPhotos().get(0).getThumbnails().getLargeThumbnail())
                    .placeholder(R.drawable.no_photo_preview)
                    .crossFade()
                    .into(holder.preview);
        }

        holder.isFree.setVisibility(View.GONE);
        if (model.isFree()) {
            holder.isFree.setVisibility(View.VISIBLE);
        }

        holder.adultLayout.setVisibility(View.GONE);
        if (model.getAgeRestriction() != null && model.getAgeRestriction() != 0) {
            holder.adultLayout.setVisibility(View.VISIBLE);
            holder.adultText.setText(String.format("%d%s", model.getAgeRestriction(), "+"));
            if (model.getAgeRestriction() <= 12) {
                holder.adultLayout.setBackgroundResource(R.drawable.dark_green_circle_drawable);
            } else if (model.getAgeRestriction() <= 16) {
                holder.adultLayout.setBackgroundResource(R.drawable.blue_circle_drawable);
            } else if (model.getAgeRestriction() >= 18) {
                holder.adultLayout.setBackgroundResource(R.drawable.red_circle_drawable);
            }
        }
        checkIfOpen(holder, model);

        if (model.getDates() != null && !model.getDates().isEmpty()) {
            holder.expandLayout.setVisibility(View.VISIBLE);
            holder.expandLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buildDatesDialog(model.getDates());
                }
            });
        }
        holder.cardView.setVisibility(View.VISIBLE);

        if (lastPosition < position) {
            lastPosition = position;
            YoYo.with(Techniques.FadeInUp).duration(300).playOn(holder.cardView);
        }
    }

    private void checkIfOpen(ViewHolder holder, EventModel model) {
        Calendar todayDate = Calendar.getInstance();
        Date today = new Date();
        todayDate.setTime(today);
        Date lastDate = new Date(Integer.parseInt(
                model.getDates().get(model.getDates().size() - 1).getEndDate()) * 1000L);
        Date firstDate = new Date(Integer.parseInt(
                model.getDates().get(0).getStartDate()) * 1000L);

        Calendar lastCalendar = Calendar.getInstance();
        lastCalendar.setTime(lastDate);
        Calendar firstCalendar = Calendar.getInstance();
        firstCalendar.setTime(firstDate);

        holder.isOpen.setTextColor(getContext().getResources().getColor(R.color.green));
        holder.isOpen.setText("");

        if (todayDate.after(firstCalendar) && todayDate.before(lastCalendar) && model.getDates().size() > 1) {
            holder.isOpen.setText("Проходит сейчас");
            return;
        } else if(todayDate.after(firstCalendar) && todayDate.before(lastCalendar) && model.getDates().size() == 1){
            holder.isOpen.setText("Прошло");
            holder.isOpen.setTextColor(getContext().getResources().getColor(R.color.red));
            return;
        }
        if (todayDate.before(firstCalendar)) {
            holder.isOpen.setText("Еще не началось");
            return;
        }
        if (todayDate.after(lastCalendar)) {
            holder.isOpen.setText("Прошло");
            holder.isOpen.setTextColor(getContext().getResources().getColor(R.color.red));
            return;
        }
    }

    private void buildDatesDialog(final ArrayList<EventModel.Dates> dates) {
        try {
            if (materialCalendarView == null)
                materialCalendarView = new MaterialCalendarView(getContext());

            materialCalendarView.setArrowColor(R.color.primary_dark);
            materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_NONE);
            materialCalendarView.clearSelection();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    materialCalendarView.setCurrentDate(new Date(Integer.parseInt(dates.get(0).getStartDate()) * 1000L));
                }
            }, 150L);

            for (int i = 0; i < dates.size(); i++) {
                Date time = new Date(Integer.parseInt(dates.get(i).getStartDate()) * 1000L);
                materialCalendarView.setDateSelected(time, true);
            }

            if (dialog == null)
                dialog = new AlertDialog.Builder(getContext()).setView(materialCalendarView).create();

            if (!dialog.isShowing())
                dialog.show();


        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.adp_event_item_title)
        TextView title;
        @Bind(R.id.adp_event_item_description)
        TextView description;
        @Bind(R.id.adp_event_item_image)
        ImageView preview;
        @Bind(R.id.adp_event_item_card)
        CardView cardView;
        @Bind(R.id.adp_event_item_adult_text)
        TextView adultText;
        @Bind(R.id.adp_event_item_is_open)
        TextView isOpen;
        @Bind(R.id.adp_event_item_adult_layout)
        RelativeLayout adultLayout;
        @Bind(R.id.adp_event_item_is_free)
        RelativeLayout isFree;
        @Bind(R.id.adp_event_expand_layout)
        RelativeLayout expandLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
