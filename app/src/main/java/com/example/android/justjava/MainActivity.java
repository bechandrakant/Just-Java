package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameEditText = findViewById(R.id.name_edit_text);
        String name = nameEditText.getText().toString().trim();

        String priceMessage = createOrderSummary(name);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Coffee Order");
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        // displayMessage(priceMessage);
    }

    /**
     * Creates order summary
     *
     * @param name name entered in edit text
     * @return orderSummary
     */
    private String createOrderSummary(String name) {
        CheckBox whippedCream = findViewById(R.id.whipped_cream);
        boolean hasWhippedCream = whippedCream.isChecked();

        CheckBox chocolate = findViewById(R.id.chocolate);
        boolean hasChocolate = chocolate.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String priceMessage = getString(R.string.name_hint) + ": " + name;
        priceMessage += "\n" + getString(R.string.add_whipped_cream) + " " +
                ((hasWhippedCream)? getString(R.string.true_text): getString(R.string.false_text));
        priceMessage += "\n" + getString(R.string.add_chocolate) + " " +
                ((hasChocolate)? getString(R.string.true_text): getString(R.string.false_text));
        priceMessage += "\n" + getString(R.string.quantity) + " " + quantity;
        priceMessage += "\n" + getString(R.string.total_price) + " " + price;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * Calculate total price of order
     *
     * @param hasWhippedCream to add price based on if whipped cream is added or not
     * @param hasChocolate    to add price based on if chocolate is added or not
     * @return totalPrice of the order.
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int basePrice = 5;

        if (hasWhippedCream) {
            basePrice += 2;
        }

        if (hasChocolate) {
            basePrice += 1;
        }

        return basePrice * quantity;
    }

    /**
     * This method increments the quantity value when plus button is clicked
     */
    public void increment(View view) {
        if (quantity < 10) {
            quantity = quantity + 1;
        } else {
            Toast.makeText(MainActivity.this, "Can't order more than 10 cup of coffee!",
                    Toast.LENGTH_SHORT).show();
        }
        display(quantity);
    }

    /**
     * This method increments the quantity value when plus button is clicked
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
        } else {
            Toast.makeText(MainActivity.this, "Can't order less than 1 cup of coffee!",
                    Toast.LENGTH_SHORT).show();
        }
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }
}