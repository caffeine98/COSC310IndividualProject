package com.example.javabucksim;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chad.designtoast.DesignToast;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;

import java.math.BigDecimal;

public class PaymentActivity extends AppCompatActivity {
    Button addFundsButton;
    EditText fundAmount;;
    final int PAYPAL_REQ_CODE = 12;
    private static PayPalConfiguration paypalConfig = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(PaypalClientIDConfig.CLIENT_ID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        addFundsButton = (Button) findViewById(R.id.buttonAddFunds);
        fundAmount = (EditText) findViewById(R.id.editTextFundAmount);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);
        startService(intent);

    }

    public void onClickAddFundsButton(View view){
        if (fundAmount.getText().toString().isEmpty()) {
            DesignToast.makeText(getApplicationContext(), "Invalid amount entered", DesignToast.LENGTH_SHORT, DesignToast.TYPE_WARNING).show();
            return;
        }
        if (Integer.parseInt(fundAmount.getText().toString()) <= 0) {
            DesignToast.makeText(getApplicationContext(), "Invalid amount entered", DesignToast.LENGTH_SHORT, DesignToast.TYPE_WARNING).show();
            return;
        }
        addFundsPaypal();
    }

    private void addFundsPaypal() {
        PayPalPayment payment = new PayPalPayment(
                new BigDecimal(Integer.parseInt(fundAmount.getText().toString())), "CAD",
                "javabucksIM Payment", PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, com.paypal.android.sdk.payments.PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);
        intent.putExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_PAYMENT, payment);

        startActivityForResult(intent, PAYPAL_REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PAYPAL_REQ_CODE) {
            if (resultCode == Activity.RESULT_OK){
                DesignToast.makeText(getApplicationContext(), "Payment Successful", DesignToast.LENGTH_SHORT, DesignToast.TYPE_SUCCESS).show();
            }
            else {
                DesignToast.makeText(getApplicationContext(), "Payment Unsuccessful", DesignToast.LENGTH_SHORT, DesignToast.TYPE_ERROR).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    public void onClickAddFundsBackButton(View view) {
        finish();
    }
}