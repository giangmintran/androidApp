package com.example.moneymanagement.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.moneymanagement.MainActivity;
import com.example.moneymanagement.R;
import com.example.moneymanagement.transporters.EventTransporter;

import java.text.DecimalFormat;

public class ShowItemFragment extends Fragment {

    private ImageView imageView;
    private TextView name, price, category, date;

    public ShowItemFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(null);
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle(EventTransporter.getEvent().getPlace_name());
        return inflater.inflate(R.layout.fragment_show_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        findViews(view);
        setValues();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.drawer_view, menu);
        menu.setGroupVisible(R.id.main_menu, false);
        menu.setGroupVisible(R.id.item_menu, true);
    }

    private void findViews(View view) {
        name = getView().findViewById(R.id.item_show_name);
        price = getView().findViewById(R.id.item_show_price);
        category = getView().findViewById(R.id.item_show_category);
        date = getView().findViewById(R.id.item_show_date);
        imageView = getView().findViewById(R.id.item_show_icon);
    }

    private void setValues(){
        DecimalFormat decim = new DecimalFormat("0.00");
        String final_price = decim.format(Double.parseDouble(EventTransporter.getEvent().getPrice()));

        name.setText(EventTransporter.getEvent().getPlace_name());
        price.setText(final_price + " VNĐ");
        date.setText(EventTransporter.getEvent().getDate());
        category.setText(EventTransporter.getEvent().getCategory());

        if(Double.parseDouble(EventTransporter.getEvent().getPrice()) < 0.00){
            imageView.setImageResource(R.drawable.outline_trending_down_24);
        }else{
            imageView.setImageResource(R.drawable.outline_trending_up_24);
        }

        switch (EventTransporter.getEvent().getCategory()) {
            case "Các chi phí khác":
                category.setBackgroundResource(R.color.category_1);
                break;
            case "Hóa đơn":
                category.setBackgroundResource(R.color.category_2);
                break;
            case "Thanh toán":
                category.setBackgroundResource(R.color.category_3);
                break;
            case "Ăn uống":
                category.setBackgroundResource(R.color.category_4);
                break;
            case "Di chuyển":
                category.setBackgroundResource(R.color.category_5);
                break;
            case "Căn hộ, chung cư":
                category.setBackgroundResource(R.color.category_6);
                break;
            case "Sức khỏe và vệ sinh":
                category.setBackgroundResource(R.color.category_7);
                break;
            case "Quần áo":
                category.setBackgroundResource(R.color.category_8);
                break;
            case "Giải trí":
                category.setBackgroundResource(R.color.category_9);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_page: {
                return true;
            }
            case R.id.share: {
                sendEvent();
                return true;
            }
            case R.id.edit: {
                Navigation.findNavController(getView()).navigate(R.id.edit_fragment);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sendEvent(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, EventTransporter.getEvent().toString());
        String chooserTitle = getString(R.string.send);
        Intent chosenIntent = Intent.createChooser(intent, chooserTitle);
        startActivity(chosenIntent);
    }
}
