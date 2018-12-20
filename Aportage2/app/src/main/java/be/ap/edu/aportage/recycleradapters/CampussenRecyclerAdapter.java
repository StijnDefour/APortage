package be.ap.edu.aportage.recycleradapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import be.ap.edu.aportage.R;
import be.ap.edu.aportage.activities.Campussen;
import be.ap.edu.aportage.activities.Verdiepingen;
import be.ap.edu.aportage.models.Campus;

public class CampussenRecyclerAdapter extends RecyclerView.Adapter<CampussenRecyclerAdapter.ViewHolder> {

    private final Context context;

    public void setCampussenList(List<Campus> campussenList) {
        this.campussenList = campussenList;
    }

    private List<Campus> campussenList;
    private final LayoutInflater layoutInflater;

    public CampussenRecyclerAdapter(Context context, List<Campus> campussenList) {
        this.context = context;
        this.campussenList = campussenList;
        this.layoutInflater = LayoutInflater.from(this.context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = layoutInflater.inflate(R.layout.cv_campus_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Campus campus = this.campussenList.get(i);
        viewHolder.campusTitel.setText(campus.getNaam());
        viewHolder.campusAfk = campus.afkorting;

        Integer bgcolor;
        switch (campus.afkorting) {
            case "ell":
                bgcolor = ContextCompat.getColor(context, R.color.Ellerman);
                break;
            case "noo":
                bgcolor = ContextCompat.getColor(context, R.color.Noorderplaats);
                break;
            case "mei":
                bgcolor = ContextCompat.getColor(context, R.color.Meistraat);
                break;
            default:
                bgcolor = ContextCompat.getColor(context, R.color.Meistraat);
                break;
        }
        viewHolder.campusFoto.setBackgroundColor(bgcolor);
    }

    @Override
    public int getItemCount() {
        return this.campussenList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView campusFoto;
        public TextView campusTitel;
        public String campusAfk;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.campusFoto = (ImageView)itemView.findViewById(R.id.iv_campus_foto);
            this.campusTitel = (TextView)itemView.findViewById(R.id.tv_campus_titel);
            registreerOnClickListener(itemView);
        }

        public void registreerOnClickListener(final View itemView) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Verdiepingen.class);
                    intent.putExtra(context.getResources().getString(R.string.campus_intent), campusAfk);
                    context.startActivity(intent);
                    ((Campussen)context).finish();
                }
            });
        }
    }
}
