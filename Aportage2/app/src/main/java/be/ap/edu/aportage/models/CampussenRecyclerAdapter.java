package be.ap.edu.aportage.models;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import be.ap.edu.aportage.R;

public class CampussenRecyclerAdapter extends RecyclerView.Adapter<CampussenRecyclerAdapter.ViewHolder> {

    private final Context context;
    private final List<Campus> campussenList;
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
        //viewHolder.campusFoto.setImageURI(Uri.parse("https://www.ap.be/sites/default/files/2018-10/Meistraat.jpg"));
    }

    @Override
    public int getItemCount() {
        return this.campussenList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView campusFoto;
        public TextView campusTitel;
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
                    Intent intent = new Intent(context, be.ap.edu.aportage.Verdiepingen.class);
                    intent.putExtra("campus_titel", campusTitel.getText());
                    context.startActivity(intent);
                }
            });
        }
    }
}
