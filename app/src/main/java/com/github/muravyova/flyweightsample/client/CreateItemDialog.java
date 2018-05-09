package com.github.muravyova.flyweightsample.client;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.muravyova.flyweightsample.R;
import com.github.muravyova.flyweightsample.util.Colors;

public class CreateItemDialog extends DialogFragment {

    public interface OnItemCreated{
        void create(ListItem item);
    }

    private OnItemCreated mOnItemCreated;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_create_item, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setTitle("Создание");
        final SeekBar sizeSeek = view.findViewById(R.id.dialog_create_item_seek_size);
        final TextView sizeText = view.findViewById(R.id.dialog_create_item_text_size);
        final Spinner colorSpinner = view.findViewById(R.id.dialog_create_item_spinner_color);
        final Spinner typeSpinner = view.findViewById(R.id.dialog_create_item_spinner_type);
        final CardView colorCard = view.findViewById(R.id.dialog_create_item_card_color);
        sizeSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                sizeText.setText(concat(seekBar.getProgress() + 1));
            }
        });

        ArrayAdapter<String> colorAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, getColors());
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(colorAdapter);

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, getTypes());
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int color = getColor(position);
                colorCard.setCardBackgroundColor(color);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        view.findViewById(R.id.dialog_create_item_btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        view.findViewById(R.id.dialog_create_item_btn_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemCreated != null){
                    mOnItemCreated.create(new ListItem(getColor(colorSpinner.getSelectedItemPosition()),
                            getType(typeSpinner.getSelectedItemPosition()), sizeSeek.getProgress() + 1));
                    dismiss();
                }
            }
        });
    }

    private String[] getColors(){
        return new String[]{"Красный","Зеленый","Синий","Белый","Черный","Фиолетовый","Желтый","Оранжевый","Голубой","Коричневый"};
    }

    private String[] getTypes(){
        return new String[]{"Кружочек", "Квадратик"};
    }

    private ListItem.Type getType(int position){
        switch (position){
            case 0:
                return ListItem.Type.POINT;
            default:
                return ListItem.Type.SQUARE;
        }
    }

    private int getColor(int position){
        switch (position){
            case 0:
                return Colors.redColor;
            case 1:
                return Colors.greenColor;
            case 2:
                return Colors.blueColor;
            case 3:
                return 0xffffffff;
            case 4:
                return 0xff000000;
            case 5:
                return 0xffbf15b2;
            case 6:
                return 0xfffff935;
            case 7:
                return 0xffff9d2d;
            case 8:
                return 0xff42fffe;
            case 9:
                return 0xff8b4e17;
        }

        return 0;
    }

    private String concat(int c){
        return c + " px";
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemCreated){
            mOnItemCreated = (OnItemCreated) context;
        }
    }
}
