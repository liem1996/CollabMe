package com.example.collabme.model;

import android.content.Context;
import android.util.Log;

import com.example.collabme.objects.MyApplication;
import com.example.collabme.objects.Offer;
import com.example.collabme.objects.tokensrefresh;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * this model is for search the function in this model are :
 * 1.get list of offers by free search
 * 2.get list of offers by parameters from the user that is connect
 */

public class ModelSearch {

    public static final ModelSearch instance = new ModelSearch();
    public com.example.collabme.objects.tokensrefresh tokensrefresh = new tokensrefresh();

    /**
     *
     *
     * interfaces
     */

    public interface getOfferFromFreeSearchListener{
        void onComplete(List<Offer> offers);
    }
    public interface getOfferFromSpecificSearchListener{
        void onComplete(List<Offer> offers);
    }


    /**
     *
     *
     * functions
     */
    public void getOfferFromFreeSearch(String freesearch, ModelSearch.getOfferFromFreeSearchListener getOfferFromFreeSearchListener) {

        tokensrefresh.retroServer();

        String tokenAccess = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses","");

        Call<List<Offer>> call = tokensrefresh.retrofitInterface.getOfferFromFreeSearch(freesearch,"Bearer "+tokenAccess);
        call.enqueue(new Callback<List<Offer>>() {
            @Override
            public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response) {
                if(response.code()==200) {
                    getOfferFromFreeSearchListener.onComplete(response.body());
                }
                else if (response.code() == 403) {
                    tokensrefresh.changeAcssesToken();
                    String tockennew = tokensrefresh.gettockenAcsses();
                    Call<List<Offer>> call1 = tokensrefresh.retrofitInterface.getOfferFromFreeSearch(freesearch,"Bearer "+tockennew);
                    call1.enqueue(new Callback<List<Offer>>() {
                        @Override
                        public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response1) {
                            if(response1.code()==200){
                                getOfferFromFreeSearchListener.onComplete(response.body());
                            }else{
                                getOfferFromFreeSearchListener.onComplete(null);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Offer>> call, Throwable t) {
                            getOfferFromFreeSearchListener.onComplete(null);
                        }
                    });
                } else {
                    getOfferFromFreeSearchListener.onComplete(null);
                }
            }
            @Override
            public void onFailure(Call<List<Offer>> call, Throwable t) {
                Log.d("TAG","failed in free search"+t);

                getOfferFromFreeSearchListener.onComplete(null);

            }
        });
    }

    public void getOfferFromSpecificSearch(String description, String headline, String fromdate, String todate, String fromprice,
                                           String toprice, String user, String[] professions, ModelSearch.getOfferFromSpecificSearchListener getOfferFromSpecificSearchListener) {

        tokensrefresh.retroServer();

        String tokenAccess = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getString("tokenAcsses","");

        HashMap<String, Object> map = new HashMap<>();
        map.put("professions",professions);
        map.put("description",description);
        map.put("headline",headline);
        map.put("fromdate",fromdate);
        map.put("todate",todate);
        map.put("fromprice",fromprice);
        map.put("toprice",toprice);
        map.put("user",user);

        Call<List<Offer>> call = tokensrefresh.retrofitInterface.getOfferFromSpecificSearch(map,"Bearer "+tokenAccess);
        call.enqueue(new Callback<List<Offer>>() {
            @Override
            public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response) {
                if(response.code()==200) {
                    getOfferFromSpecificSearchListener.onComplete(response.body());
                }
                else if (response.code() == 403) {
                    tokensrefresh.changeAcssesToken();
                    String tockennew = tokensrefresh.gettockenAcsses();
                    Call<List<Offer>> call1 = tokensrefresh.retrofitInterface.getOfferFromSpecificSearch(map,"Bearer "+tockennew);
                    call1.enqueue(new Callback<List<Offer>>() {
                        @Override
                        public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response1) {
                            if(response1.code()==200){
                                getOfferFromSpecificSearchListener.onComplete(response.body());
                            }else{
                                getOfferFromSpecificSearchListener.onComplete(null);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Offer>> call, Throwable t) {
                            getOfferFromSpecificSearchListener.onComplete(null);
                        }
                    });
                } else {
                    getOfferFromSpecificSearchListener.onComplete(null);
                }
            }
            @Override
            public void onFailure(Call<List<Offer>> call, Throwable t) {
                Log.d("TAG","failed in specific search"+t);

                getOfferFromSpecificSearchListener.onComplete(null);

            }
        });
    }


}
