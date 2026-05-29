package com.example.todolist.presentation.main;

import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.data.mapper.CurrencyInfoService;
import com.example.todolist.domain.model.Note;
import com.example.todolist.presentation.main.AppSettings;

import java.util.ArrayList;
import java.util.List;

public class NewNoteAdapter extends RecyclerView.Adapter<NotesViewHolder> {
    public interface OnBoughtChangeListener {
        void onBoughtChanged(Note note, boolean isBought);
    }
    private OnBoughtChangeListener boughtChangeListener;
    public void setOnBoughtChangeListener(OnBoughtChangeListener listener) {
        this.boughtChangeListener = listener;
    }

    public interface OnPriceClickListener {
        void onPriceClick(Note note);
    }
    private OnPriceClickListener priceClickListener;
    public void setOnPriceClickListener(OnPriceClickListener listener) {
        this.priceClickListener = listener;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public List<Note> getNotes() {
        return new ArrayList<>(notes);
    }

    private List<Note> notes = new ArrayList<>();
    private Listner listner;

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.textViewNote.setText(note.getText());
        double price = note.getPriceRub();
        String currency = AppSettings.getSelectedCurrency(holder.itemView.getContext());
        boolean showLocal = AppSettings.isShowLocalCurrency(holder.itemView.getContext());
        String symbol = CurrencyInfoService.getCurrencySymbol(currency);

        if (showLocal && !currency.equals("RUB")) {
            holder.textViewPrice.post(() -> {
                String priceText = String.format("%.2f %s", price, symbol);
                holder.textViewPrice.setText(priceText);
            });
        } else {
            String priceText = String.format("%.2f %s", price, CurrencyInfoService.getCurrencySymbol("RUB"));
            holder.textViewPrice.setText(priceText);
        }
        holder.checkBox.setChecked(note.isBought());

        int color;
        if (price < 200) {
            color = android.graphics.Color.parseColor("#4CAF50");
        } else if (price < 500) {
            color = android.graphics.Color.parseColor("#FFEB3B");
        } else if (price < 1000) {
            color = android.graphics.Color.parseColor("#FF9800");
        } else {
            color = android.graphics.Color.parseColor("#F44336");
        }
        holder.priceIndicator.setBackgroundResource(R.drawable.price_indicator_circle);
        holder.priceIndicator.getBackground().setTint(color);

        holder.itemView.setOnClickListener(v -> {
            if (listner != null) {
                listner.setOnClickListner(note);
            }
        });

        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(note.isBought());
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (boughtChangeListener != null) {
                boughtChangeListener.onBoughtChanged(note, isChecked);
            }
        });

        holder.textViewPrice.setOnClickListener(v -> {
            if (priceClickListener != null) {
                priceClickListener.onPriceClick(note);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setListner(Listner listner) {
        this.listner = listner;
    }
}

interface Listner {
    void setOnClickListner(Note note);
}

class NotesViewHolder extends RecyclerView.ViewHolder {
    TextView textViewNote;
    TextView textViewPrice;
    CheckBox checkBox;
    View priceIndicator;

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewNote = itemView.findViewById(R.id.text_title);
        textViewPrice = itemView.findViewById(R.id.text_price);
        checkBox = itemView.findViewById(R.id.checkbox);
        priceIndicator = itemView.findViewById(R.id.price_indicator);
    }
}
