package com.example.arouterdemo.ui.image;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.example.arouterdemo.R;
import com.example.basecommonlibrary.RouterCommonUtil;

/**
 * @author: xiewenliang
 * @Filename:
 * @Description:
 * @Copyright: Copyright (c) 2017 Tuandai Inc. All rights reserved.
 * @date: 2017/4/19 10:48
 */

@Route(path = "/ui/image", group = "图片")
public class ImageActivity extends AppCompatActivity implements View.OnClickListener {

    @Autowired
    public String arg1;

    @Autowired(name = "arg1")
    public String argx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        findViewById(R.id.bt1).setOnClickListener(this);
        ARouter.getInstance().inject(this);
        String url = getIntent().getStringExtra("arg1");
        if (!TextUtils.isEmpty(url)) {
            Glide.with(this).load(url).into(((ImageView) findViewById(R.id.imageView)));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                RouterCommonUtil.startMainTextActivity(this, "测试文本");
                break;
            case R.id.bt2:
                RouterCommonUtil.startMainImageActivity(this, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492584424522&di=5dcde77431914e0b944b8af9ff5f9277&imgtype=jpg&src=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D1489971249%2C1618541505%26fm%3D214%26gp%3D0.jpg");
                break;
            case R.id.bt3:
                RouterCommonUtil.startLibraryOneActivity(this);
                break;
            case R.id.bt4:
                RouterCommonUtil.startLibraryTwoActivity(this);
                break;
        }
    }
}
