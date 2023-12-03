package algonquin.cst2335.dictionaryapi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DefinitionAdapter extends RecyclerView.Adapter<DefinitionAdapter.DefinitionViewHolder> {

    private List<Definition> definitions;

    public DefinitionAdapter() {
        definitions = new ArrayList<>();
    }

    @NonNull
    @Override
    public DefinitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_definition, parent, false);
        return new DefinitionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DefinitionViewHolder holder, int position) {
        Definition currentDefinition = definitions.get(position);
        holder.textViewWord.setText(currentDefinition.word);
    }

    @Override
    public int getItemCount() {
        return definitions.size();
    }

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
        notifyDataSetChanged();
    }

    static class DefinitionViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewWord;

        public DefinitionViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewWord = itemView.findViewById(R.id.textViewWord);
        }
    }
}