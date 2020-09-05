package com.ftouchcustomer.Merchant;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.recyclerview.widget.RecyclerView;

import com.ftouchcustomer.Global.ClsGlobal;
import com.ftouchcustomer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.RectanglePromptBackground;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal;

import static android.content.Context.MODE_PRIVATE;

public class AdapterMerchantList extends RecyclerView.Adapter<AdapterMerchantList.ViewHolder> {

    List<ClsMerchantResponseList> lst = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;
    View itemView;
    String mode = "";

    private OnClickListenerCall mOnClickListenerCall;
    private OnWtsAppClickListenerCall mOnWtsAppClickListenerCall;
    private OnShopNowClickListenerCall mOnShopNowClickListenerCall;
    private OnTimeClickListenerCall mOnTimeClick;
    private OnCastLayoutClick onCastLayoutClick;
    private OnMainImageClick onMainImageClick;
    private OnGoogleMapClick onGoogleMapClick;
    private OnFavItems mOnFavItems;

    public AdapterMerchantList(Context mContext, String mode) {
        this.mContext = mContext;
        this.mode = mode;
        mInflater = LayoutInflater.from(mContext);
    }

    public List<ClsMerchantResponseList> getAdapterList() {
        return lst;
    }

    public void updateList(ClsMerchantResponseList clsMerchantResponseList) {
        lst.set(lst.indexOf(clsMerchantResponseList), clsMerchantResponseList);
        notifyDataSetChanged();
    }

    public void addList(List<ClsMerchantResponseList> list) {
//        this.lst.addAll(list);

        this.lst = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = mInflater.inflate(R.layout.row_merchants_list, parent, false);
        return new ViewHolder(itemView);
    }

    public void setOnMobileClick(OnClickListenerCall mOnClickListenerCall) {
        this.mOnClickListenerCall = mOnClickListenerCall;
    }

    public void setOnWtsAppClick(OnWtsAppClickListenerCall mOnWtsAppClickListenerCall) {
        this.mOnWtsAppClickListenerCall = mOnWtsAppClickListenerCall;
    }

    public void setOnShopNowClick(OnShopNowClickListenerCall mOnShopNowClickListenerCall) {
        this.mOnShopNowClickListenerCall = mOnShopNowClickListenerCall;
    }

    public void setOnTimeClick(OnTimeClickListenerCall mOnTimeClick) {
        this.mOnTimeClick = mOnTimeClick;
    }

    public void setOnMainImageClick(OnMainImageClick onMainImageClick) {
        this.onMainImageClick = onMainImageClick;
    }

    public void setOnCastLayoutClick(OnCastLayoutClick onCastLayoutClick) {
        this.onCastLayoutClick = onCastLayoutClick;
    }

    public void setOnMapClick(OnGoogleMapClick onGoogleMapClick) {
        this.onGoogleMapClick = onGoogleMapClick;
    }

    public void setOnFavItemClick(OnFavItems mOnFavItems) {
        this.mOnFavItems = mOnFavItems;
    }

    public interface OnClickListenerCall {
        void OnItemClick(ClsMerchantResponseList clsMerchantResponseList);
    }

    public interface OnWtsAppClickListenerCall {
        void OnItemClick(ClsMerchantResponseList clsMerchantResponseList);
    }

    public interface OnShopNowClickListenerCall {
        void OnItemClick(ClsMerchantResponseList clsMerchantResponseList);
    }

    public interface OnTimeClickListenerCall {
        void OnItemClick(List<ClsTiming> lst, ClsMerchantResponseList clsMerchantResponseList);
    }

    public interface OnMainImageClick {
        void OnItemClick(ClsMerchantResponseList clsMerchantResponseList);
    }

    public interface OnCastLayoutClick {
        void OnItemClick(ClsMerchantResponseList clsMerchantResponseList);
    }

    public interface OnGoogleMapClick {
        void OnMapItemClick(ClsMerchantResponseList clsMerchantResponseList);
    }

    public interface OnFavItems {
        void OnFavItemsClick(ClsMerchantResponseList clsMerchantResponseList, ToggleButton tgb_fav);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClsMerchantResponseList currentObj = lst.get(position);

        List<ClsTiming> lst = new ArrayList<>();
        lst = currentObj.getTiming();

        if (mode.equalsIgnoreCase("FragmentMerchantList")) {
            holder.iv_img_shop_now.setImageResource(R.drawable.ic_clock);
            holder.txt_shop_now.setText("Book Appointment");
            holder.txt_shop_now.setTextSize(10);
        }

        String[] categoryList = currentObj.getCategories().split(",");
        holder.ll_category.setVisibility(View.VISIBLE);
        holder.cast_layout.removeAllViews();

        if (currentObj.getCategories() != null &&
                !currentObj.getCategories().equalsIgnoreCase("")) {

            for (String name : categoryList) {
                AddTimingEditText(holder.cast_layout, name);
            }

        } else {
            AddTimingEditText(holder.cast_layout, "NO PRODUCT FOUND!");
        }

        holder.txt_merchant_name.setText(currentObj.getBusinessName());

        if (currentObj.getDistance() != null &&
                !currentObj.getDistance().equalsIgnoreCase("")) {
            holder.txt_distance.setText(currentObj.getDistance() + " km");
            holder.txt_distance.setTextColor(Color.parseColor("#000000"));
            holder.iv_location.setColorFilter(mContext.getResources().getColor(R.color.green));
        } else {
            holder.txt_distance.setText(" km ?");
            holder.txt_distance.setTextColor(Color.parseColor("#b4b4b4"));
            holder.iv_location.setColorFilter(mContext.getResources().getColor(R.color.txtColor));
//
//            holder.iv_location.setBackgroundColor(Color.parseColor("#b4b4b4"));
        }


        if (currentObj.getTiming().size() != 0) {

            List<ClsTiming> list = new ArrayList<>();

            list = StreamSupport.stream(currentObj.getTiming())
                    .filter(s -> s.getWeekDay().equalsIgnoreCase(ClsGlobal.getCurrentDay()))
                    .collect(Collectors.toList());

            for (ClsTiming obj : list) {
//                String finalTime = obj.getWeekDay().concat(" ").concat(obj.getTime());

                String finalTime = "";
                if (obj.getTime().equalsIgnoreCase("Closed")) {
                    finalTime = "Closed";
                    holder.txt_time.setTextColor(Color.parseColor("#ffcc0000"));
                } else {
                    finalTime = "Today " .concat(obj.getTime());
                    holder.txt_time.setTextColor(Color.parseColor("#000000"));
                }
                holder.txt_time.setText(finalTime);
            }

        } else {
            holder.txt_time.setText("Store timing ?");
            holder.txt_time.setTextColor(Color.parseColor("#007bff"));
        }

        Uri uri = Uri.parse("android.resource://" + mContext.getPackageName() + "/drawable/ic_no_image.xml");

        if (currentObj.getLogoUrl().contains("Not found")) {
            Picasso.get().load(uri)
                    .placeholder(R.drawable.ic_no_image)
                    .resize(120, 60)
                    .into(holder.iv_display_logo);
        } else {
            Picasso.get().load(currentObj.getLogoUrl()).into(holder.iv_display_logo);
        }

        if (currentObj.getOnlineSellingStatus() != null &&
                currentObj.getOnlineSellingStatus().equalsIgnoreCase("Disable")) {
            holder.iv_hot_red.setVisibility(View.VISIBLE);
            holder.iv_green.setVisibility(View.GONE);
            holder.txt_status.setText("STORE IS OFFLINE.");
        } else {
            holder.iv_green.setVisibility(View.VISIBLE);
            holder.iv_hot_red.setVisibility(View.GONE);
            holder.txt_status.setText("STORE IS ONLINE.");
        }

//        holder.tgb_fav.setChecked(currentObj.isFavorite);

        if (currentObj.getFavourite() != null
                && currentObj.getFavourite().equalsIgnoreCase("YES")) {
            holder.tgb_fav.setChecked(true);
        } else {
            holder.tgb_fav.setChecked(false);
        }

        holder.Bind(currentObj, mOnClickListenerCall);
        holder.BindWtsApp(currentObj, mOnWtsAppClickListenerCall);
        holder.BindTimeApp(lst, currentObj, mOnTimeClick);
        holder.BindShopNow(currentObj, mOnShopNowClickListenerCall);
        holder.BindLogo(currentObj, onMainImageClick);
        holder.BindCastLayout(currentObj, onCastLayoutClick);
        holder.BindMapOpen(currentObj, onGoogleMapClick);
        holder.BindFavItem(currentObj, mOnFavItems);

        if (mode.equalsIgnoreCase("FragmentMerchantList")) {
            if (isFirstTimeAppointment()) {
                if (position == 0) {
                    new MaterialTapTargetPrompt.Builder((Activity) mContext)
                            .setTarget(holder.iv_display_logo)
                            .setPrimaryText("Store logo")
                            .setSecondaryText("it helps you to choose your favorites store or brand")
                            .setAnimationInterpolator(new FastOutSlowInInterpolator())
                            .setPromptBackground(new RectanglePromptBackground())
                            .setPromptFocal(new RectanglePromptFocal())
                            .setCaptureTouchEventOutsidePrompt(true)
                            .setBackgroundColour(Color.parseColor("#814a6d"))
                            .setPromptStateChangeListener((prompt, state) -> {
                                if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED ||
                                        state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED) {

                                    new MaterialTapTargetPrompt.Builder((Activity) mContext)
                                            .setTarget(holder.iv_phone)
                                            .setPrimaryText("Store's contact number")
                                            .setSecondaryText("you can contact to store directly from here for inquiry or any question.")
                                            .setAnimationInterpolator(new FastOutSlowInInterpolator())
                                            .setPromptBackground(new RectanglePromptBackground())
                                            .setPromptFocal(new RectanglePromptFocal())
                                            .setCaptureTouchEventOutsidePrompt(true)
                                            .setBackgroundColour(Color.parseColor("#814a6d"))
                                            .setPromptStateChangeListener((prompt1, state1) -> {
                                                if (state1 == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED ||
                                                        state1 == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED) {

                                                    new MaterialTapTargetPrompt.Builder((Activity) mContext)
                                                            .setTarget(holder.iv_wtsApp)
                                                            .setPrimaryText("Store WhatsApp Number")
                                                            .setSecondaryText("tap here and start inquiry.")
                                                            .setAnimationInterpolator(new FastOutSlowInInterpolator())
                                                            .setPromptBackground(new RectanglePromptBackground())
                                                            .setPromptFocal(new RectanglePromptFocal())
                                                            .setCaptureTouchEventOutsidePrompt(true)
                                                            .setBackgroundColour(Color.parseColor("#814a6d"))
                                                            .setPromptStateChangeListener((prompt2, state2) -> {
                                                                if (state2 == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED ||
                                                                        state2 == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED) {

                                                                    new MaterialTapTargetPrompt.Builder((Activity) mContext)
                                                                            .setTarget(holder.ll_status)
                                                                            .setPrimaryText("Store products & services")
                                                                            .setSecondaryText("you can see here seller's products & services so you can easily find best and relevant store")
                                                                            .setAnimationInterpolator(new FastOutSlowInInterpolator())
                                                                            .setPromptBackground(new RectanglePromptBackground())
                                                                            .setPromptFocal(new RectanglePromptFocal())
                                                                            .setCaptureTouchEventOutsidePrompt(true)
                                                                            .setBackgroundColour(Color.parseColor("#814a6d"))
                                                                            .setPromptStateChangeListener((prompt3, state3) -> {
                                                                                if (state3 == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED ||
                                                                                        state3 == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED) {

                                                                                    new MaterialTapTargetPrompt.Builder((Activity) mContext)
                                                                                            .setTarget(holder.ll_location)
                                                                                            .setPrimaryText("Store location & address")
                                                                                            .setSecondaryText("you can see here store's location & distance for easy to find and Map direction.")
                                                                                            .setAnimationInterpolator(new FastOutSlowInInterpolator())
                                                                                            .setPromptBackground(new RectanglePromptBackground())
                                                                                            .setPromptFocal(new RectanglePromptFocal())
                                                                                            .setCaptureTouchEventOutsidePrompt(true)
                                                                                            .setBackgroundColour(Color.parseColor("#814a6d"))
                                                                                            .setPromptStateChangeListener((prompt4, state4) -> {
                                                                                                if (state4 == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED ||
                                                                                                        state4 == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED) {

                                                                                                    new MaterialTapTargetPrompt.Builder((Activity) mContext)
                                                                                                            .setTarget(holder.txt_time)
                                                                                                            .setPrimaryText("Store Timing")
                                                                                                            .setSecondaryText("You can see here, Store of weekly timing")
                                                                                                            .setAnimationInterpolator(new FastOutSlowInInterpolator())
                                                                                                            .setPromptBackground(new RectanglePromptBackground())
                                                                                                            .setPromptFocal(new RectanglePromptFocal())
                                                                                                            .setCaptureTouchEventOutsidePrompt(true)
                                                                                                            .setBackgroundColour(Color.parseColor("#814a6d"))
                                                                                                            .setPromptStateChangeListener((prompt5, state5) -> {
                                                                                                                if (state5 == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED ||
                                                                                                                        state5 == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED) {

                                                                                                                    new MaterialTapTargetPrompt.Builder((Activity) mContext)
                                                                                                                            .setTarget(holder.cast_layout)
                                                                                                                            .setPrimaryText("Store Services")
                                                                                                                            .setSecondaryText("You can see here, Store Services")
                                                                                                                            .setAnimationInterpolator(new FastOutSlowInInterpolator())
                                                                                                                            .setPromptBackground(new RectanglePromptBackground())
                                                                                                                            .setPromptFocal(new RectanglePromptFocal())
                                                                                                                            .setCaptureTouchEventOutsidePrompt(true)
                                                                                                                            .setBackgroundColour(Color.parseColor("#814a6d"))
                                                                                                                            .setPromptStateChangeListener((prompt6, state6) -> {
                                                                                                                                if (state6 == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED ||
                                                                                                                                        state6 == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED) {

                                                                                                                                    new MaterialTapTargetPrompt.Builder((Activity) mContext)
                                                                                                                                            .setTarget(holder.txt_shop_now)
                                                                                                                                            .setPrimaryText("Book Appointment")
                                                                                                                                            .setSecondaryText("You can book appointment from here")
                                                                                                                                            .setAnimationInterpolator(new FastOutSlowInInterpolator())
                                                                                                                                            .setPromptBackground(new RectanglePromptBackground())
                                                                                                                                            .setPromptFocal(new RectanglePromptFocal())
                                                                                                                                            .setCaptureTouchEventOutsidePrompt(true)
                                                                                                                                            .setBackgroundColour(Color.parseColor("#814a6d"))
                                                                                                                                            .setAutoDismiss(true)
                                                                                                                                            .show();

                                                                                                                                }
                                                                                                                            })
                                                                                                                            .show();

                                                                                                                }
                                                                                                            })
                                                                                                            .show();

                                                                                                }
                                                                                            })
                                                                                            .show();

                                                                                }
                                                                            })
                                                                            .show();

                                                                }
                                                            })
                                                            .show();

                                                }
                                            })
                                            .show();
                                }
                            })
                            .show();
                }
            }
        } else if (mode.equalsIgnoreCase("FragmentMerchant")) {
            if (isFirstTimeOrder()) {
                if (position == 0) {
                    new MaterialTapTargetPrompt.Builder((Activity) mContext)
                            .setTarget(holder.iv_display_logo)
                            .setPrimaryText("Store Logo")
                            .setSecondaryText("it helps you to choose your favorites store or brand")
                            .setAnimationInterpolator(new FastOutSlowInInterpolator())
                            .setPromptBackground(new RectanglePromptBackground())
                            .setPromptFocal(new RectanglePromptFocal())
                            .setCaptureTouchEventOutsidePrompt(true)
                            .setBackgroundColour(Color.parseColor("#814a6d"))
                            .setPromptStateChangeListener((prompt, state) -> {
                                if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED ||
                                        state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED) {

                                    new MaterialTapTargetPrompt.Builder((Activity) mContext)
                                            .setTarget(holder.iv_phone)
                                            .setPrimaryText("Store's contact number")
                                            .setSecondaryText("you can contact to store directly from here for inquiry or any question.")
                                            .setAnimationInterpolator(new FastOutSlowInInterpolator())
                                            .setPromptBackground(new RectanglePromptBackground())
                                            .setPromptFocal(new RectanglePromptFocal())
                                            .setCaptureTouchEventOutsidePrompt(true)
                                            .setBackgroundColour(Color.parseColor("#814a6d"))
                                            .setPromptStateChangeListener((prompt1, state1) -> {
                                                if (state1 == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED ||
                                                        state1 == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED) {

                                                    new MaterialTapTargetPrompt.Builder((Activity) mContext)
                                                            .setTarget(holder.iv_wtsApp)
                                                            .setPrimaryText("Store WhatsApp Number")
                                                            .setSecondaryText("tap here and start inquiry.")
                                                            .setAnimationInterpolator(new FastOutSlowInInterpolator())
                                                            .setPromptBackground(new RectanglePromptBackground())
                                                            .setPromptFocal(new RectanglePromptFocal())
                                                            .setCaptureTouchEventOutsidePrompt(true)
                                                            .setBackgroundColour(Color.parseColor("#814a6d"))
                                                            .setPromptStateChangeListener((prompt2, state2) -> {
                                                                if (state2 == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED ||
                                                                        state2 == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED) {

                                                                    new MaterialTapTargetPrompt.Builder((Activity) mContext)
                                                                            .setTarget(holder.ll_status)
                                                                            .setPrimaryText("Store products & services")
                                                                            .setSecondaryText("you can see here seller's products & services so you can easily find best and relevant store")
                                                                            .setAnimationInterpolator(new FastOutSlowInInterpolator())
                                                                            .setPromptBackground(new RectanglePromptBackground())
                                                                            .setPromptFocal(new RectanglePromptFocal())
                                                                            .setCaptureTouchEventOutsidePrompt(true)
                                                                            .setBackgroundColour(Color.parseColor("#814a6d"))
                                                                            .setPromptStateChangeListener((prompt3, state3) -> {
                                                                                if (state3 == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED ||
                                                                                        state3 == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED) {

                                                                                    new MaterialTapTargetPrompt.Builder((Activity) mContext)
                                                                                            .setTarget(holder.ll_location)
                                                                                            .setPrimaryText("Store location & address")
                                                                                            .setSecondaryText("you can see here store's location & distance for easy to find and Map direction.")
                                                                                            .setAnimationInterpolator(new FastOutSlowInInterpolator())
                                                                                            .setPromptBackground(new RectanglePromptBackground())
                                                                                            .setPromptFocal(new RectanglePromptFocal())
                                                                                            .setCaptureTouchEventOutsidePrompt(true)
                                                                                            .setBackgroundColour(Color.parseColor("#814a6d"))
                                                                                            .setPromptStateChangeListener((prompt4, state4) -> {
                                                                                                if (state4 == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED ||
                                                                                                        state4 == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED) {

                                                                                                    new MaterialTapTargetPrompt.Builder((Activity) mContext)
                                                                                                            .setTarget(holder.txt_time)
                                                                                                            .setPrimaryText("Store Timing")
                                                                                                            .setSecondaryText("You can see here, Store of weekly timing")
                                                                                                            .setAnimationInterpolator(new FastOutSlowInInterpolator())
                                                                                                            .setPromptBackground(new RectanglePromptBackground())
                                                                                                            .setPromptFocal(new RectanglePromptFocal())
                                                                                                            .setCaptureTouchEventOutsidePrompt(true)
                                                                                                            .setBackgroundColour(Color.parseColor("#814a6d"))
                                                                                                            .setPromptStateChangeListener((prompt5, state5) -> {
                                                                                                                if (state5 == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED ||
                                                                                                                        state5 == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED) {

                                                                                                                    new MaterialTapTargetPrompt.Builder((Activity) mContext)
                                                                                                                            .setTarget(holder.cast_layout)
                                                                                                                            .setPrimaryText("Store Services")
                                                                                                                            .setSecondaryText("You can see here, Store Services")
                                                                                                                            .setAnimationInterpolator(new FastOutSlowInInterpolator())
                                                                                                                            .setPromptBackground(new RectanglePromptBackground())
                                                                                                                            .setPromptFocal(new RectanglePromptFocal())
                                                                                                                            .setCaptureTouchEventOutsidePrompt(true)
                                                                                                                            .setBackgroundColour(Color.parseColor("#814a6d"))
                                                                                                                            .setPromptStateChangeListener((prompt6, state6) -> {
                                                                                                                                if (state6 == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED ||
                                                                                                                                        state6 == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED) {

                                                                                                                                    new MaterialTapTargetPrompt.Builder((Activity) mContext)
                                                                                                                                            .setTarget(holder.txt_shop_now)
                                                                                                                                            .setPrimaryText("Shop Now")
                                                                                                                                            .setSecondaryText("You can Order something from here")
                                                                                                                                            .setAnimationInterpolator(new FastOutSlowInInterpolator())
                                                                                                                                            .setPromptBackground(new RectanglePromptBackground())
                                                                                                                                            .setPromptFocal(new RectanglePromptFocal())
                                                                                                                                            .setCaptureTouchEventOutsidePrompt(true)
                                                                                                                                            .setBackgroundColour(Color.parseColor("#814a6d"))
                                                                                                                                            .setAutoDismiss(true)
                                                                                                                                            .show();

                                                                                                                                }
                                                                                                                            })
                                                                                                                            .show();

                                                                                                                }
                                                                                                            })
                                                                                                            .show();

                                                                                                }
                                                                                            })
                                                                                            .show();

                                                                                }
                                                                            })
                                                                            .show();

                                                                }
                                                            })
                                                            .show();

                                                }
                                            })
                                            .show();
                                }
                            })
                            .show();
                }
            }

        }

    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_distance;
        TextView txt_merchant_name;
        TextView txt_time;
        TextView txt_status;
        TextView txt_shop_now;
        ImageView iv_display_logo, iv_location, iv_phone, iv_img_shop_now;
        ImageView iv_hot_red, iv_green, iv_wtsApp;
        LinearLayout ll_location, cast_layout, ll_category;
        LinearLayout ll_status, ll_shop_now;

        ToggleButton tgb_fav;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_green = itemView.findViewById(R.id.iv_green);
            iv_hot_red = itemView.findViewById(R.id.iv_hot_red);
            txt_distance = itemView.findViewById(R.id.txt_distance);
            iv_display_logo = itemView.findViewById(R.id.iv_display_logo);
            txt_merchant_name = itemView.findViewById(R.id.txt_merchant_name);
            iv_phone = itemView.findViewById(R.id.iv_phone);
            iv_img_shop_now = itemView.findViewById(R.id.iv_img_shop_now);
            cast_layout = itemView.findViewById(R.id.cast_layout);
            iv_wtsApp = itemView.findViewById(R.id.iv_wtsApp);
            ll_category = itemView.findViewById(R.id.ll_category);
            txt_shop_now = itemView.findViewById(R.id.txt_shop_now);
            txt_time = itemView.findViewById(R.id.txt_time);
            ll_location = itemView.findViewById(R.id.ll_location);
            txt_status = itemView.findViewById(R.id.txt_status);
            ll_status = itemView.findViewById(R.id.ll_status);
            iv_location = itemView.findViewById(R.id.iv_location);
            tgb_fav = itemView.findViewById(R.id.tgb_fav);
            ll_shop_now = itemView.findViewById(R.id.ll_shop_now);
        }

        void Bind(final ClsMerchantResponseList clsMerchantResponseList,
                  OnClickListenerCall onClickListenerCall) {
            iv_phone.setOnClickListener(v ->
                    // send current position via Onclick method.
                    onClickListenerCall.OnItemClick(clsMerchantResponseList));
        }

        void BindShopNow(final ClsMerchantResponseList clsMerchantResponseList,
                         OnShopNowClickListenerCall mOnShopNowClickListenerCall) {
            ll_shop_now.setOnClickListener(v ->
                    // send current position via Onclick method.
                    mOnShopNowClickListenerCall.OnItemClick(clsMerchantResponseList));
        }

        void BindWtsApp(final ClsMerchantResponseList clsMerchantResponseList,
                        OnWtsAppClickListenerCall onWtsAppClickListenerCall) {
            iv_wtsApp.setOnClickListener(v ->
                    // send current position via Onclick method.
                    onWtsAppClickListenerCall.OnItemClick(clsMerchantResponseList));
        }

        void BindTimeApp(List<ClsTiming> lst, ClsMerchantResponseList list,
                         OnTimeClickListenerCall onTimeClickListenerCall) {
            txt_time.setOnClickListener(v ->
                    // send current position via Onclick method.
                    onTimeClickListenerCall.OnItemClick(lst, list));
        }

        void BindLogo(final ClsMerchantResponseList clsMerchantResponseList,
                      OnMainImageClick onMainImageClick) {
            iv_display_logo.setOnClickListener(view -> {
                onMainImageClick.OnItemClick(clsMerchantResponseList);
            });
        }

        void BindCastLayout(final ClsMerchantResponseList clsMerchantResponseList,
                            OnCastLayoutClick onCastLayoutClick) {
            cast_layout.setOnClickListener(view -> {
                onCastLayoutClick.OnItemClick(clsMerchantResponseList);
            });
        }

        void BindMapOpen(ClsMerchantResponseList list,
                         OnGoogleMapClick onGoogleMapClick) {
            ll_location.setOnClickListener(v ->
                    // send current position via Onclick method.
                    onGoogleMapClick.OnMapItemClick(list));
        }

        void BindFavItem(ClsMerchantResponseList list,
                         OnFavItems mOnFavItems) {
            tgb_fav.setOnClickListener(v ->
                    // send current position via Onclick method.
                    mOnFavItems.OnFavItemsClick(list, tgb_fav));
        }
    }

    private void AddTimingEditText(LinearLayout linearLayout, String open) {
        LayoutInflater inflater = (LayoutInflater)
                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout2 = inflater.inflate(R.layout.row_category_name, null);
        TextView txt_categories = layout2.findViewById(R.id.txt_categories);
        txt_categories.setText(open);
        linearLayout.addView(layout2, linearLayout.getChildCount() - 1);
    }

    private boolean isFirstTimeAppointment() {
        SharedPreferences preferences = mContext.getSharedPreferences("IsFirstTime", MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("AdapterMerchantListAppointment", false);
        if (!ranBefore) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("AdapterMerchantListAppointment", true);
            editor.apply();
        }
        return !ranBefore;
    }

    private boolean isFirstTimeOrder() {
        SharedPreferences preferences = mContext.getSharedPreferences("IsFirstTime", MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("AdapterMerchantListOrder", false);
        if (!ranBefore) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("AdapterMerchantListOrder", true);
            editor.apply();
        }
        return !ranBefore;
    }
}
