package com.example.moneymanagement.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneymanagement.MainActivity;
import com.example.moneymanagement.R;
import com.example.moneymanagement.adapters.RecyclerViewAdapter;
import com.example.moneymanagement.databases.DatabaseHelper;
import com.example.moneymanagement.listeners.ClickListener;
import com.example.moneymanagement.models.Event;
import com.example.moneymanagement.transporters.MonthTransporter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ExpensesFragment extends Fragment implements ClickListener {

    private int transaction_count = 0;
    private double transaction_money = 0;
    private TextView transMoney, transCount;
    private Context context;
    private List<Event> eventList = new ArrayList<Event>();

    public ExpensesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle(MonthTransporter.getMonth());
        return inflater.inflate(R.layout.fragment_expenses, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        transMoney = getView().findViewById(R.id.transaction_money);
        transCount = getView().findViewById(R.id.transaction_count);
        getData(view);
    }

    private void getData(View view) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        eventList = databaseHelper.getAllEventsExpenses();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.item_events_list_expense);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter eventAdapter = new RecyclerViewAdapter(eventList, this, new RecyclerViewAdapter.OnQuantityChangeListener() {
            @Override
            public void onQuantityChange(double change) {
                transaction_money += change;

                DecimalFormat decim = new DecimalFormat("0.00");
                String price = decim.format(transaction_money);

                if (transaction_money > 0.00) {
                    transMoney.setTextColor(Color.parseColor("#048838"));
                    transMoney.setText(price + " VNĐ");
                } else if (transaction_money == 0.00) {
                    transMoney.setTextColor(Color.BLACK);
                    transMoney.setText(price + " VNĐ");
                } else if (transaction_money < 0.00) {
                    transMoney.setTextColor(Color.RED);
                    transMoney.setText(price + " VNĐ");
                }
            }

            @Override
            public void onTransactionChange(int change) {
                transaction_count += change;
                transCount.setText(Integer.toString(transaction_count));
            }
        });

        getFullMoneyReport();
        getFullTransCountReport();

        recyclerView.setAdapter(eventAdapter);
        eventAdapter.setEventList(eventList);
    }

    private void getFullTransCountReport(){
        transCount = getView().findViewById(R.id.transaction_count_expense);
        transaction_count = eventList.size();
        transCount.setText(Integer.toString(transaction_count));
    }

    private void getFullMoneyReport(){
        transMoney = getView().findViewById(R.id.transaction_money_expense);
        transaction_money = 0;
        for(Event event : eventList){
            double price = Double.parseDouble(event.getPrice());
            transaction_money += price;
        }

        DecimalFormat decim = new DecimalFormat("0.00");
        String price = decim.format(transaction_money);

        if(transaction_money > 0.00){
            transMoney.setTextColor(Color.parseColor("#048838"));
            transMoney.setText(price + " VNĐ");
        }else if(transaction_money == 0.00){
            transMoney.setTextColor(Color.BLACK);
            transMoney.setText(price + " VNĐ");
        }else if(transaction_money < 0.00){
            transMoney.setTextColor(Color.RED);
            transMoney.setText(price + " VNĐ");
        }
    }

    @Override
    public void onClick(Object data) {
    }
}