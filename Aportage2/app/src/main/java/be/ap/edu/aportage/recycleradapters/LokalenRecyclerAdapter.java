package be.ap.edu.aportage.recycleradapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import be.ap.edu.aportage.R;
import be.ap.edu.aportage.activities.Meldingen;
import be.ap.edu.aportage.models.Lokaal;

public class LokalenRecyclerAdapter extends RecyclerView.Adapter<LokalenRecyclerAdapter.ViewHolder> {

    private final Context context;
    private final List<Lokaal> lokalenList;
    private final LayoutInflater layoutInflater;
    private final String afkorting_campus;
    private final String verdieping;

    public LokalenRecyclerAdapter(Context context, List<Lokaal> lokalenList, String afk, String verdieping) {
        this.context = context;
        this.lokalenList = lokalenList;
        this.layoutInflater = LayoutInflater.from(this.context);
        this.afkorting_campus = afk;
        this.verdieping = verdieping;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = layoutInflater.inflate(R.layout.listitem_lokaal, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        int lokaalNummer = this.lokalenList.get(i).mNr;
        String lokaalNummerString = String.format("%03d", lokaalNummer);
        viewHolder.lokaal_s = lokaalNummerString;
        viewHolder.verdiepTitel.setText(this.verdieping+"."+lokaalNummerString);
        viewHolder.campus_s = this.afkorting_campus;
        viewHolder.verdieping_s = this.verdieping;
    }

    @Override
    public int getItemCount() {
        return this.lokalenList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView verdiepTitel;
        private String campus_s;
        private String verdieping_s;
        private String lokaal_s;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            verdiepTitel = itemView.findViewById(R.id.tv_verdiep_titel);

            registreerOnClickListener(itemView);
        }

        public void registreerOnClickListener(View itemView) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Meldingen.class);

                    Log.d("test", campus_s + verdieping_s + verdiepTitel.getText().toString());

                    intent.putExtra("campus_afk", campus_s );
                    intent.putExtra("verdiep_nr", verdieping_s);
                    intent.putExtra("lokaal_nr", lokaal_s);
                    context.startActivity(intent);
                }
            });
        }
    }
}
