package com.ftouchcustomer.DisplayImage;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ftouchcustomer.Global.ClsGlobal;
import com.ftouchcustomer.ImageEdit.ImageController;
import com.ftouchcustomer.Orders.ClsLayerItemMaster;
import com.ftouchcustomer.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DisplayMultipleImgActivity extends AppCompatActivity {


    public ArrayList<Uri> path = new ArrayList<>();
    public ArrayList<String> Databasepath = new ArrayList<>();
    ImageView img_main;
    RecyclerView rv_img_block;
    LinearLayoutManager linearLayoutManager;
    ImageAdapterNew imageAdapterNew;
    ImageController mainController;
    Toolbar toolbar;

    String ID = "";
    String itemName = "";
    String itemCode = "";
    String _imgMode = "";
    String menuImgUrl = "";
    String imgSave = "";

    TextView txt_item_name;
    TextView txt_no_data;
    RelativeLayout rl_recyclerview;

    ClsLayerItemMaster objClsLayerItemMaster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_img);

        objClsLayerItemMaster = (ClsLayerItemMaster) getIntent().getSerializableExtra("objClsLayerItemMaster");


        toolbar = findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ClsGlobal.imgPreviewMode = "Preview";

        ID = getIntent().getStringExtra("ID");
        itemCode = objClsLayerItemMaster.getITEM_CODE();
        itemName = objClsLayerItemMaster.getITEM_NAME();
        _imgMode = getIntent().getStringExtra("_imgMode");
        imgSave = getIntent().getStringExtra("imgSave");
        menuImgUrl = getIntent().getStringExtra("menuImgUrl");
        txt_no_data = findViewById(R.id.txt_no_data);
        rl_recyclerview = findViewById(R.id.rl_recyclerview);
        img_main = findViewById(R.id.img_main);
        rv_img_block = findViewById(R.id.rv_img_block);
        txt_item_name = findViewById(R.id.txt_item_name);
        txt_item_name.setText(itemName);

        linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);

        mainController = new ImageController(img_main);
        rv_img_block.setLayoutManager(linearLayoutManager);

        List<String> _imgList = objClsLayerItemMaster.getImages_name_list();
        List<String> _urlList = new ArrayList<>();

        if (_imgList != null && _imgList.size() != 0) {
            rl_recyclerview.setVisibility(View.VISIBLE);
            txt_no_data.setVisibility(View.GONE);
            for (String _fullPath : _imgList) {
                _urlList.add(menuImgUrl.concat("ItemImage/").concat(ID).concat("/").concat(_fullPath));
            }

            imageAdapterNew = new ImageAdapterNew(this, mainController, _urlList);
            rv_img_block.setAdapter(imageAdapterNew);
            mainController.setImgByURL(_urlList.get(0));
            imageAdapterNew.setOnClickImg((clsImgPath, position) -> mainController.setImgByURL(clsImgPath));

        }else {
            txt_no_data.setVisibility(View.VISIBLE);
            rl_recyclerview.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        path.clear();
        Databasepath.clear();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
