package com.github.muravyova.flyweightsample.client;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.github.muravyova.flyweightsample.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CreateItemDialog.OnItemCreated{

    private static final int RESULT_LOAD_IMG = 123;

    private ListAdapter mAdapter;
    private SeekBar mSeekBar;
    private MyPictureView mPictureView;

    private final ListAdapter.OnItemClickListener mListener = new ListAdapter.OnItemClickListener() {
        @Override
        public void onClick(int position) {
            mAdapter.selectPosition(position, mSeekBar.getProgress());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView particlesList = findViewById(R.id.activity_main_list_particles);
        particlesList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new ListAdapter(ListItem.getItems(), mListener);
        particlesList.setAdapter(mAdapter);
        mPictureView = findViewById(R.id.activity_main_image);
        mSeekBar = findViewById(R.id.activity_main_seek);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mAdapter.changeSelectedItem(seekBar.getProgress());
            }
        });

        findViewById(R.id.activity_main_btn_apply).setOnClickListener(this);
        findViewById(R.id.activity_main_add).setOnClickListener(this);
    }

    private void handleOnApplyClick(){
        List<ListItem> items = mAdapter.getData();
        if (checkIfEmpty(items)){
            Toast.makeText(getApplicationContext(), "Нечего нарисовать :)", Toast.LENGTH_SHORT).show();
            mPictureView.clear();
            return;
        }
        mPictureView.setItems(items);
    }

    private void handleOnImagePickClick(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    }

    private void handleAddItemClick(){
        CreateItemDialog dialog = new CreateItemDialog();
        dialog.show(getSupportFragmentManager(), CreateItemDialog.class.getSimpleName());
    }

    private boolean checkIfEmpty(List<ListItem> items){
        for (ListItem item : items){
            if (item.getCount() != 0){
                return false;
            }
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.activity_main_btn_apply:
                handleOnApplyClick();
                break;
            case R.id.activity_main_add:
                handleAddItemClick();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_pick_image){
            handleOnImagePickClick();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                mPictureView.setImageBitmap(selectedImage);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Что-то пошло не так", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void create(ListItem item) {
        mAdapter.addItem(item);
    }
}
