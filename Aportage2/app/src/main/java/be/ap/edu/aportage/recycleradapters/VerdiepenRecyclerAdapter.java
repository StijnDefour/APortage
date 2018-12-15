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
import be.ap.edu.aportage.activities.Lokalen;
import be.ap.edu.aportage.models.Verdiep;

public class VerdiepenRecyclerAdapter extends RecyclerView.Adapter<VerdiepenRecyclerAdapter.ViewHolder> {

    private final Context context;
    private final LayoutInflater layoutInflater;
    private List<Verdiep> verdiepenLijst;
    private final String afkorting_campus;

    public VerdiepenRecyclerAdapter(Context context, List<Verdiep> verdiepenLijst, String afk) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(this.context);
        this.verdiepenLijst = verdiepenLijst;
        this.afkorting_campus = afk;
    }

    public void setVerdiepenLijst(List<Verdiep> lijst){
        this.verdiepenLijst = lijst;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = layoutInflater.inflate(R.layout.cv_verdiep_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Verdiep verdiep = verdiepenLijst.get(i);
        viewHolder.verdiepNummer.setText(verdiep.getVerdiepNaam());
        viewHolder.afk = this.afkorting_campus;

    }

    @Override
    public int getItemCount() {
        return this.verdiepenLijst.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView verdiepNummer;
        private String afk;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.verdiepNummer = (TextView)itemView.findViewById(R.id.tv_verdiep_nummer);
            registreerOnClickListener(itemView);
        }

        public void registreerOnClickListener(View itemView) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Lokalen.class);
                    intent.putExtra(context.getResources().getString(R.string.verdieping_intent), verdiepNummer.getText());
                    intent.putExtra(context.getResources().getString(R.string.campus_intent), afk);
                    context.startActivity(intent);
                }
            });
        }
    }
}
