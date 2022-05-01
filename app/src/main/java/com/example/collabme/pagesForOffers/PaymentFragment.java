package com.example.collabme.pagesForOffers;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.collabme.Activites.LoginActivity;
import com.example.collabme.R;
import com.example.collabme.model.ModelOffers;
import com.example.collabme.model.ModelPayment;
import com.example.collabme.model.Modelauth;
import com.example.collabme.objects.Offer;
import com.example.collabme.objects.Payment;
import com.example.collabme.status.DoneStatusFragmentArgs;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Optional;

public class PaymentFragment extends Fragment {

    private CheckoutViewModel model;
    String offerId;
    String headline;
    int price;
    Payment payment;
    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 991;
    private static final long SHIPPING_COST_CENTS = 90 * PaymentsUtil.CENTS_IN_A_UNIT.longValue();

    public static final String clientKey = "AUCAIbvyRUFuhNZi_Zqt4GOLb1X6jixH47Z1ln7ym_SbzghlfUyQjofK_vBL4MR3DPXADPswqvS8q63b";
    public static final int PAYPAL_REQUEST_CODE = 123;

    // Paypal Configuration Object
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(clientKey);

    // A client for interacting with the Google Pay API.
    private PaymentsClient paymentsClient;
    //private View googlePayButton;

    private JSONArray garmentList;
    private JSONObject selectedGarment;

    TextView cardNumber, expDate, cvv, id, name, offer, bankAccount;
    Button backBtn, submit;
    ImageView logout, googlePayButton, payPal;

    ActivityResultLauncher<IntentSenderRequest> resolvePaymentForResult = registerForActivityResult(
            new ActivityResultContracts.StartIntentSenderForResult(),
            result -> {
                switch (result.getResultCode()) {
                    case Activity.RESULT_OK:
                        Intent resultData = result.getData();
                        if (resultData != null) {
                            PaymentData paymentData = PaymentData.getFromIntent(result.getData());
                            if (paymentData != null) {
                                handlePaymentSuccess(paymentData);
                            }
                        }
                        break;

                    case Activity.RESULT_CANCELED:
                        // The user cancelled the payment attempt
                        break;
                }
            });

    @Override
    public void onDestroyView() {
        getActivity().stopService(new Intent(this.getContext(), PayPalService.class));
        super.onDestroyView();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        cardNumber = view.findViewById(R.id.fragment_payment_card_number_et);
        expDate = view.findViewById(R.id.fragment_payment_exp_et);
        cvv = view.findViewById(R.id.fragment_payment_cvv_et);
        id = view.findViewById(R.id.fragment_payment_id_et);
        name = view.findViewById(R.id.fragment_payment_name_et);
        offer = view.findViewById(R.id.fragment_payment_offer_et);
        bankAccount = view.findViewById(R.id.fragment_payment_bank_account_et);
        logout = view.findViewById(R.id.fragment_payment_logoutBtn);
        offerId = DoneStatusFragmentArgs.fromBundle(getArguments()).getOfferid();
        googlePayButton = view.findViewById(R.id.googlePayButton);
        payPal = view.findViewById(R.id.payment_PayPal_btn);
        backBtn = view.findViewById(R.id.fragment_payment_back_btn);
        submit = view.findViewById(R.id.fragment_payment_submit_btn);


        ModelOffers.instance.getOfferById(offerId, new ModelOffers.GetOfferListener() {
            @Override
            public void onComplete(Offer offer1) {
                headline = offer1.getHeadline();
                price = offer1.getPrice();
                offer.setText(headline);
            }
        });


        model = new ViewModelProvider(this).get(CheckoutViewModel.class);
        model.canUseGooglePay.observe(this.getActivity(), this::setGooglePayAvailable);


        // creating Payment Client (different from google code, not by methods)

        Wallet.WalletOptions walletOptions = new Wallet.WalletOptions.Builder()
                .setEnvironment(WalletConstants.ENVIRONMENT_TEST).setTheme(WalletConstants.THEME_DARK)
                .build();

        // creating payment client
        paymentsClient = Wallet.getPaymentsClient(this.getContext(), walletOptions);
        possiblyShowGooglePayButton();

        googlePayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPayment(view);
            }
        });

        payPal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPayment();
            }
        });

        Intent intent = new Intent(this.getContext(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        getActivity().startService(intent);

        backBtn.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add payment
                if (checkValidDate()) {
                    payment = new Payment(cardNumber.getText().toString(), expDate.getText().toString(), cvv.getText().toString(),
                            id.getText().toString(), name.getText().toString(), offerId, bankAccount.getText().toString());

                    ModelPayment.instance2.addPayment(payment, new ModelPayment.AddingPayemnt() {
                        @Override
                        public void onComplete(int code) {
                            if (code == 200) {
                                //   Model.instance.Login(userConnected.getUsername(), userConnected.getPassword(), code1 -> { });
                                Toast.makeText(getActivity(), "Payment Was Added", Toast.LENGTH_LONG).show();
                                ModelOffers.instance.getOfferById(offerId, new ModelOffers.GetOfferListener() {
                                    @Override
                                    public void onComplete(Offer offer) {
                                        offer.setStatus("Close");
                                        ModelOffers.instance.editOffer(offer, new ModelOffers.EditOfferListener() {
                                            @Override
                                            public void onComplete(int code) {
                                                Navigation.findNavController(v).navigate(PaymentFragmentDirections.actionPaymentFragmentToCloseStatusfragment(offerId));
                                            }
                                        });
                                    }
                                });
                            } else {
                                Toast.makeText(getActivity(), "not added", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Modelauth.instance2.logout(new Modelauth.logout() {
                    @Override
                    public void onComplete(int code) {
                        if (code == 200) {
                            toLoginActivity();
                        } else {

                        }
                    }
                });
            }
        });
        return view;
    }

    private void getPayment() {
        int amount = price;// TODO:: take the real amount

        // Creating a paypal payment on below line
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "USD", "Payment",
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this.getContext(), PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        // Putting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        // Starting the intent activity for result
        // the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
        Navigation.findNavController(getView()).navigate(PaymentFragmentDirections.actionPaymentFragmentToCloseStatusfragment(offerId));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // If the result is from paypal
        if (requestCode == PAYPAL_REQUEST_CODE) {

            // If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {

                // Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                // if confirmation is not null
                if (confirm != null) {
                    try {
                        // Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);

                        // on below line we are extracting json response and displaying it in a text view.
                        JSONObject payObj = new JSONObject(paymentDetails);
                        String payID = payObj.getJSONObject("response").getString("id");
                        String state = payObj.getJSONObject("response").getString("state");

                    } catch (JSONException e) {
                        // handling json exception on below line
                        Log.e("Error", "a failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // on below line we are checking the payment status.
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                // on below line when the invalid paypal config is submitted.
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void possiblyShowGooglePayButton() {

        final Optional<JSONObject> isReadyToPayJson = Optional.ofNullable(PaymentsUtil.getIsReadyToPayRequest());
        if (!isReadyToPayJson.isPresent()) {
            return;
        }

        // The call to isReadyToPay is asynchronous and returns a Task. We need to provide an
        // OnCompleteListener to be triggered when the result of the call is known.
        IsReadyToPayRequest request = IsReadyToPayRequest.fromJson(isReadyToPayJson.get().toString());
        Task<Boolean> task = paymentsClient.isReadyToPay(request);
        task.addOnCompleteListener(this.getActivity(),
                new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            setGooglePayAvailable(task.getResult());
                        } else {
                            Log.w("isReadyToPay failed", task.getException());
                        }
                    }
                });
    }

    private void setGooglePayAvailable(boolean available) {
        if (available) {
            googlePayButton.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this.getContext(), "googlepay_status_unavailable", Toast.LENGTH_LONG).show();
        }
    }

    private void handleError(int statusCode, @Nullable String message) {

        Log.e("loadPaymentData failed",
                String.format(Locale.getDefault(), "Error code: %d, Message: %s", statusCode, message));
    }

    private void handlePaymentSuccess(PaymentData paymentData) {

        // Token will be null if PaymentDataRequest was not constructed using fromJson(String).
        final String paymentInfo = paymentData.toJson();
        if (paymentInfo == null) {
            return;
        }

        try {
            JSONObject paymentMethodData = new JSONObject(paymentInfo).getJSONObject("paymentMethodData");
            // If the gateway is set to "example", no payment information is returned - instead, the
            // token will only consist of "examplePaymentMethodToken".

            final JSONObject tokenizationData = paymentMethodData.getJSONObject("tokenizationData");
            final String token = tokenizationData.getString("token");
            final JSONObject info = paymentMethodData.getJSONObject("info");
            final String billingName = info.getJSONObject("billingAddress").getString("name");
            final String MSG = "Payment succeeded, amount : " + price;
            Toast.makeText(this.getContext(), MSG, Toast.LENGTH_LONG).show();
            // Logging token string.
            Log.d("Google Pay token: ", token);

            Navigation.findNavController(getView()).navigate(PaymentFragmentDirections.actionPaymentFragmentToCloseStatusfragment(offerId));


        } catch (JSONException e) {
            throw new RuntimeException("The selected garment cannot be parsed from the list of elements");
        }
    }

    public void requestPayment(View view) {

        // Disables the button to prevent multiple clicks.
        googlePayButton.setClickable(false);

        // The price provided to the API should include taxes and shipping.
        // This price is not displayed to the user.
        long priceInCents = price;
        long shippingCostCents = 0;
        long totalPriceCents = priceInCents + shippingCostCents;
        final Task<PaymentData> task = model.getLoadPaymentDataTask(totalPriceCents);

        task.addOnCompleteListener(completedTask -> {
            if (completedTask.isSuccessful()) {
                handlePaymentSuccess(completedTask.getResult());
            } else {
                Exception exception = completedTask.getException();
                if (exception instanceof ResolvableApiException) {
                    PendingIntent resolution = ((ResolvableApiException) exception).getResolution();
                    resolvePaymentForResult.launch(new IntentSenderRequest.Builder(resolution).build());

                } else if (exception instanceof ApiException) {
                    ApiException apiException = (ApiException) exception;
                    handleError(apiException.getStatusCode(), apiException.getMessage());

                } else {
                    handleError(CommonStatusCodes.INTERNAL_ERROR, "Unexpected non API" +
                            " exception when trying to deliver the task result to an activity!");
                }
            }
            // Re-enables the Google Pay payment button.
            googlePayButton.setClickable(true);
        });
    }

    public boolean checkValidDate()
    {
        if (!cardNumber.getText().toString().matches("^[1-9]{1}(?:[0-9]{15})?$")){
            cardNumber.setError("CardNumber is required and must be 16 numbers");
            return false;
        }
        else if (!expDate.getText().toString().matches("(?:0[1-9]|1[0-2])/[0-9]{2}")){
            expDate.setError("Expire date is required and must be MM/YY format");
            return false;
        }
        else if (!cvv.getText().toString().matches("^[0-9]{3,4}$")){
            cvv.setError("Cvv is required and must be 3 numbers");
            return false;
        }
        else if (id.getText().toString().length()!=9){
            id.setError("ID is required");
            return false;
        }
        else if (!name.getText().toString().matches("^[a-zA-Z ]{2,30}$")) {
            name.setError("Name is required");
            return false;
        }
        else if (!(bankAccount.getText().toString().length()>5 && bankAccount.getText().toString().length()<10)
                || !(bankAccount.getText().toString().matches("^[1-9]{1}(?:[0-9])*?$"))){
            bankAccount.setError("Bank account is required");
            return false;
        }
        else
            return true;
    }

    private void toLoginActivity() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

}
