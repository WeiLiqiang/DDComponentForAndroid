package com.mrzhang.component;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ljsw.router.facade.annotation.RouteNode;
import com.mrzhang.component.componentlib.router.Router;
import com.mrzhang.component.componentlib.router.ui.UIRouter;
import com.mrzhang.componentservice.readerbook.ReadBookService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@RouteNode(path = "/index",group = "home")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Fragment fragment;
    FragmentTransaction ft;

    Button installReadBookBtn;
    Button uninstallReadBtn;

    @BindView(R2.id.btn_navigate_bktest) Button btnNavToBktest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        installReadBookBtn = (Button) findViewById(R.id.install_share);
        uninstallReadBtn = (Button) findViewById(R.id.uninstall_share);
        installReadBookBtn.setOnClickListener(this);
        uninstallReadBtn.setOnClickListener(this);
        showFragment();

//        findViewById(R.id.btn_navigate_bktest).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                navigateToBktest();
//            }
//        });
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_navigate_bktest) void navigateToBktest() {
        UIRouter.getInstance()
                .openUri(MainActivity.this,
                        "https://bkComponent/demo",
                        null);
    }


    private void showFragment() {
        if (fragment != null) {
            ft = getSupportFragmentManager().beginTransaction();
            ft.remove(fragment).commit();
            fragment = null;
        }
        Router router = Router.getInstance();
        if (router.getService(ReadBookService.class.getSimpleName()) != null) {
            ReadBookService service = (ReadBookService) router.getService(ReadBookService.class.getSimpleName());
            fragment = service.getReadBookFragment();
            ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.tab_content, fragment).commitAllowingStateLoss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.install_share:
                Router.registerComponent("com.mrzhang.share.applike.ShareApplike");
                break;
            case R.id.uninstall_share:
                Router.unregisterComponent("com.mrzhang.share.applike.ShareApplike");
                break;
        }
    }


}
