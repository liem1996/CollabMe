package com.example.collabme.model;

import android.content.Context;

import com.example.collabme.objects.MyApplication;
import com.example.collabme.objects.Payment;
import com.example.collabme.objects.tokensrefresh;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * this model is for payment the function in this model are :
 * 1.add a payment for an specific offer
 */
public class ModelPayment {

    public static final ModelPayment instance2 = new ModelPayment();
    public com.example.collabme.objects.tokensrefresh tokensrefresh = new tokensrefresh();


    public interface AddingPayemnt{
        void onComplete(int code);

    }


    public void addPayment(Payment payment, AddingPayemnt addingPayemnt) {
        tokensrefresh.retroServer();

        Map<String, Object> map = payment.toJson();
        String tockenacsses = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses", "");

        Call<Payment> call = tokensrefresh.retrofitInterface.executenewPayment(map, "Bearer " + tockenacsses);
        call.enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {
                if (response.code() == 200) {
                    addingPayemnt.onComplete(200);
                } else if (response.code() == 403) {
                    tokensrefresh.changeAcssesToken();
                    String tockennew = tokensrefresh.gettockenAcsses();
                    Call<Payment> call1 = tokensrefresh.retrofitInterface.executenewPayment(map, "Bearer " + tockennew);
                    call1.enqueue(new Callback<Payment>() {
                        @Override
                        public void onResponse(Call<Payment> call, Response<Payment> response1) {
                            if (response1.code() == 200) {
                                addingPayemnt.onComplete(200);
                            } else {
                                addingPayemnt.onComplete(400);
                            }
                        }

                        @Override
                        public void onFailure(Call<Payment> call, Throwable t) {
                            addingPayemnt.onComplete(400);
                        }
                    });
                } else {
                    addingPayemnt.onComplete(400);
                }
            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {
                addingPayemnt.onComplete(400);
            }
        });
    }
}
