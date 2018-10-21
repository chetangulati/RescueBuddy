package essential;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.astudios.disastermanagement.R;
import com.onurkaganaldemir.ktoastlib.KToast;


public class Essential {


    public static final String BASE_URL = "http://172.22.0.37:3000/";
    public static final String REGISTER_URL = BASE_URL + "register";
    public static final String BAG_URL = BASE_URL + "gobag";
    public static final String LOGIN_URL = BASE_URL + "login";
    public static final String DISASTER_INFO_URL = BASE_URL + "disaster";

    public static final String Percent_Key = "percent";
    public static final String f1_Key = "f1";
    public static final String f2_Key = "f2";
    public static final String f3_Key = "f3";
    public static final String f4_Key = "f4";
    public static final String f5_Key = "f5";
    public final static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public final static String contactNumberPattern = "[0-9]{10}";

    public static final String PACKAGE_NAME = "com.astudios.disastermanagement";

    public static final String SOMETHING_OCCURED = "Something Occured!";

    // private static Stack<AppCompatActivity> activityStack = null;

    //toast constants
    public static final int ERROR = 0;
    public static final int SUCCESS = 1;
    public static final int INFO = 2;
    public static final int NORMAL = 3;
    public static final int WARNING = 4;

    //gender constants
    public static final int OTHER = -1;
    public static final int MALE = 0;
    public static final int FEMALE = 1;

    //keys
    public static final String NAME_KEY = "name";
    public static final String EMAIL_KEY = "email";
    public static final String AGE_KEY = "age";
    public static final String GENDER_KEY = "gender";
    public static final String MOBILE_KEY = "mbnum";
    public static final String HEIGHT_KEY = "height";
    public static final String WEIGHT_KEY = "kilogram";
    public static final String PASS_KEY = "pass";
    public static final String ID_KEY = "_id";
    //public static final String LOGIN_SAVED = "loginsaved";

    public static final String WIFI_NAME = "nasadm_rescue";
    public static final String NO_INTERNET = "No Internet!";
    public static final String REQUIRED = "This field is required";
    public static final String FILL_FORM = "Fill the Form";
    public static final String LOGIN_FAILED = "Login Failed";
    public static final String LOGIN_SUCCESS = "Login Success";
    public static final String LOGIN = "Logging into App";
    public static final String REGISTERED_SUCCESS = "Registration Success";
    public static final String REGISTERED_FAILED = "Registration FAILED!";
    public static final String APP_NAME = "Disaster Management";

    public static final String NOTITLE = "";

    private Dialog dialog;
    private io.supercharge.funnyloader.FunnyLoader loader;
    private Context context;
    public static boolean showToast = true;


    private Essential() {

    }

    public Essential(Context context) {
        this.context = context;
    }

    public void show(String msg, int status) {
        switch (status) {
            case ERROR:
                KToast.errorToast((AppCompatActivity) context, msg, Gravity.BOTTOM, KToast.LENGTH_SHORT);
                break;
            case SUCCESS:
                KToast.successToast((AppCompatActivity) context, msg, Gravity.BOTTOM, KToast.LENGTH_SHORT);
                break;
            case INFO:
                KToast.infoToast((AppCompatActivity) context, msg, Gravity.BOTTOM, KToast.LENGTH_SHORT);
                break;
            case NORMAL:
                KToast.normalToast((AppCompatActivity) context, msg, Gravity.BOTTOM, KToast.LENGTH_SHORT);
                break;
            case WARNING:
                KToast.warningToast((AppCompatActivity) context, msg, Gravity.BOTTOM, KToast.LENGTH_SHORT);
                break;

        }
    }

    public boolean checkInternet() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        return false;
    }

    public static void setAppBar(AppCompatActivity activity) {

        activity.getSupportActionBar().setTitle(Essential.NOTITLE);
        activity.getSupportActionBar().setElevation(0);
        activity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final Drawable upArrow = activity.getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        upArrow.setColorFilter(activity.getResources().getColor(R.color.orange), PorterDuff.Mode.SRC_ATOP);
        activity.getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    public static void hideKeyboard(AppCompatActivity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    /*
    public static Stack<AppCompatActivity> getStack() {
        if (activityStack == null)
            activityStack = new Stack<>();
        return activityStack;

    }

    public static void finishAllActivities() {
        if (activityStack != null) {
            while (!activityStack.isEmpty()) {
                activityStack.pop().finish();
            }
        }
    }
    */

    public void setFont(TextView textView) {
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/opensans.ttf"));
    }

    public void showDialog() {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_loader);
        loader = dialog.findViewById(R.id.loaderId);

        if (!loader.isRunning())
            loader.start();
        dialog.setCancelable(false);
        if (!dialog.isShowing())
            dialog.show();
    }

    public void cancelDialog() {
        loader.stop();
        if (dialog.isShowing())
            dialog.cancel();
    }
}
